package info.mooijman;

import info.mooijman.action.Action;
import info.mooijman.action.HtmlAction2Impl;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static info.mooijman.Constants.*;


public class DirectoryWalker {
    private static final Logger logger = LoggerFactory.getLogger(DirectoryWalker.class);

    private final Action action;
    public DirectoryWalker(Action action) {
        this.action = action;
    }

    public void doStuff(){
        action.setDir();
        action.execute("dir");
    }

    public JsonObject start(File directory, JsonArray stem) throws IOException {
        JsonArray newStem = stem.copy();
        JsonObject treeStem = new JsonObject();
        treeStem.put("dir",directory.getCanonicalPath());
        File f = new File(directory.getCanonicalPath()+File.separator+"index.html");
        logger.debug("File {}" , f.isFile());
        treeStem.put("containsIndex",(f.exists() && !f.isDirectory()));
        try {
            Scanner scanner = new Scanner(f);
            treeStem.put("indexContainsTag",false);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("<"+NAVIGATION_TAG_NAME+">")) treeStem.put("indexContainsTag",true);
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        newStem.add(treeStem);

        JsonObject tree = new JsonObject();
        JsonArray files = new JsonArray();
        JsonArray directories = new JsonArray();

        File[] listOfFiles = directory.listFiles();
        Arrays.sort(listOfFiles);
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                JsonObject file = new JsonObject();
                file.put("name",listOfFiles[i].getName());
                if (listOfFiles[i].getName().equals("index.html")) {
                    file.put("indexContainsTag",treeStem.getValue("indexContainsTag"));
                }
                files.add(file);
                logger.debug("File {}" , listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                logger.debug("Directory {} " , listOfFiles[i].getName());
                JsonObject subtree = new JsonObject();
                DirectoryWalker walker = new DirectoryWalker(this.action);
                JsonObject dirTree = walker.start(listOfFiles[i],newStem);
                subtree.put("name",listOfFiles[i].getName());
                subtree.put("content",dirTree);
                directories.add(subtree);
            }
        }
//       save tree to disk
        tree.put("files",files);
        tree.put("directories",directories);
        action.listChildrenToDisk(directory,tree,newStem);
        return tree;
    }



}
