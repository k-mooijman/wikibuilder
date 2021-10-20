package info.mooijman;

import info.mooijman.action.Action;
import info.mooijman.action.GitActionImpl;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.File;
import java.util.Arrays;

public class DirectoryWalker {
    private final Action action;

    public DirectoryWalker(Action action) {
        this.action = action;
    }


    public void doStuff(){
        action.setDir();
        action.execute("dir");
    }
    public JsonObject start(File directory ){
        JsonObject tree = new JsonObject();
        JsonArray files = new JsonArray();
        JsonArray directories = new JsonArray();

        File[] listOfFiles = directory.listFiles();
        Arrays.sort(listOfFiles);
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                JsonObject file = new JsonObject();
                file.put("name",listOfFiles[i].getName());
                files.add(file);
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
                JsonObject subtree = new JsonObject();
                DirectoryWalker walker = new DirectoryWalker(this.action);
                JsonObject dirTree = walker.start(listOfFiles[i]);
                subtree.put("name",listOfFiles[i].getName());
                subtree.put("content",dirTree);
                directories.add(subtree);
            }

        }
//       save tree to disk
        tree.put("files",files);
        tree.put("directories",directories);
        action.listChildrenToDisk(directory,tree);
        return tree;
    }
}
