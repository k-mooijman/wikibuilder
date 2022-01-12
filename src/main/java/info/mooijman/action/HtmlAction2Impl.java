package info.mooijman.action;


import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.commons.io.FileUtils;
import org.jdom2.JDOMException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static info.mooijman.Constants.NAVIGATION_TAG_NAME;


public class HtmlAction2Impl implements Action {

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


    protected boolean writeToTagInFile(String file, String tag, Element navigation) throws IOException, JDOMException {
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
            try {


                Element nav = new Element("div");
                nav.appendChild(createLink("../index.html", "oneDownAgain"));
                nav.appendElement("br");
                nav.appendChild(createLink("/index.html", "oneDownAll"));
                String tag = NAVIGATION_TAG_NAME;

                writeToTagInFile(index_file, tag, nav);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JDOMException e) {
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
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


//        if ()
        System.out.println("name " + index_file);

        return true;
    }

}
