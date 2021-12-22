package info.mooijman.action;


import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.commons.io.FileUtils;
import org.jdom2.JDOMException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

    protected boolean writeNavigation(String file, String navigation ) throws IOException, JDOMException {

        File input = new File(file);
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
        org.jsoup.nodes.Element navigationTag = doc.select("navigation").first();
        if (navigationTag!=null) {
            navigationTag.text(navigation);
        }
        FileUtils.writeStringToFile(input, doc.outerHtml(), StandardCharsets.UTF_8);

        return true;
    }

    @Override
    public boolean listChildrenToDisk(File dir, JsonObject children , JsonArray stem) {



        String name = dir.getName() + ".json";
        String directory = dir.getAbsolutePath();
        try {
            File myJsonFile = new File(directory+File.separator+name);
            FileWriter myWriter = new FileWriter(myJsonFile);
            myWriter.write(stem.encodePrettily());
            myWriter.write("\r\n");
            myWriter.write(children.encodePrettily());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("name " + name);
        return true;
    }

}
