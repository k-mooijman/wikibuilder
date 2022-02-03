package info.mooijman.action;


import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static info.mooijman.Constants.NAVIGATION_TAG_NAME;


public class HtmlAction2Impl implements Action {
    private static final Logger logger = LoggerFactory.getLogger(HtmlAction2Impl.class);

    @Override
    public void execute(String dir) {

    }

    @Override
    public String filter() {
        return null;
    }

    @Override
    public void setDir() {

    }

    public Element createLink(String link, String text) {
        return new Element("a").attr("href", link).text(text);
    }

    protected boolean writeToTagInFile(String file, String tag, Element navigation) throws IOException {
        File input = new File(file);
        Document doc = Jsoup.parse(input, "UTF-8");
        org.jsoup.nodes.Element navigationTag = doc.select(String.valueOf(tag)).first();
        if (navigationTag != null) {
            navigationTag.empty();
            navigationTag.appendChild(navigation);
        }
        FileUtils.writeStringToFile(input, doc.outerHtml(), StandardCharsets.UTF_8);

        return true;
    }


    @Override
    public boolean listChildrenToDisk(File dir, JsonObject children, JsonArray stem) {

        String index_file = dir + File.separator + "index.html";
        JsonObject dirInfo = stem.getJsonObject(stem.size() - 1);
        int i = stem.size() - 1;
        boolean hasTag = false;
        String firstParentWithTag = "";
        do {
            i--;
            if (i >= 0) {
                JsonObject parentInfo = stem.getJsonObject(i);
                hasTag = parentInfo.containsKey("indexContainsTag") && parentInfo.getBoolean("indexContainsTag");
                firstParentWithTag = parentInfo.getString("dir");
            } else {
                hasTag = true;
            }
        } while (!hasTag);


        boolean contains_index_with_tag = dirInfo.containsKey("indexContainsTag") && dirInfo.getBoolean("indexContainsTag");
        if (contains_index_with_tag) {

            logger.info("name {}", index_file);

            List<String> indexes = getNearestNavTags(".", children.getJsonArray("directories"));

            try {
                Element nav = new Element("div");
                nav.appendElement("hr");
                nav.appendChild(createLink("../index.html", "oneDownAgain"));
                nav.appendElement("br");
                nav.appendChild(createLink("/index.html", "oneDownAll"));

                indexes.forEach(index -> {
                    nav.appendElement("br");
                    logger.info("index - {}", index);
                    nav.appendChild(createLink(index,index));
                });

                nav.appendElement("hr");
                String tag = NAVIGATION_TAG_NAME;

                writeToTagInFile(index_file, tag, nav);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        String name = dir.getName() + ".json";
        String directory = dir.getAbsolutePath();
        try {
            File myJsonFile = new File(directory + File.separator + name);
            FileWriter myWriter = new FileWriter(myJsonFile);
            myWriter.write("dirInfo");
            myWriter.write(dirInfo.encodePrettily());
            myWriter.write(String.valueOf(contains_index_with_tag));
            myWriter.write("firstParentWithTag");
            myWriter.write(String.valueOf(firstParentWithTag));

            myWriter.write("\r\n");
            myWriter.write("Stem");
            myWriter.write(stem.encodePrettily());
            myWriter.write("\r\n");
            myWriter.write("Children");
            myWriter.write(children.encodePrettily());
            myWriter.close();
        } catch (IOException e) {
            logger.error("An error occurred.");
            e.printStackTrace();
        }



        return true;
    }



    public List<String> getNearestNavTags(String dir, JsonArray tree) {
        List<String> indexes = new ArrayList<>();

        tree.forEach(object -> {
            JsonObject directoryJson = (JsonObject) object;
            JsonArray files = directoryJson.getJsonObject("content").getJsonArray("files");
            AtomicBoolean hasTag = new AtomicBoolean(false);
            files.forEach(file -> {
                JsonObject fileJson = (JsonObject) file;
                if (fileJson.containsKey("indexContainsTag")) {
                    hasTag.set(hasTag.get() || fileJson.getBoolean("indexContainsTag"));
                }
            });

            String name = directoryJson.getString("name");
            String newDir = dir+"/" +name;
            JsonArray newToScanDirectories = directoryJson.getJsonObject("content").getJsonArray("directories");
            if (hasTag.get()) {
                indexes.add(newDir+ "/index.html");
            }else{
                List<String> localIndexes = getNearestNavTags(newDir,newToScanDirectories);
                indexes.addAll(localIndexes);
            }
        });

        return indexes;
    }


}
