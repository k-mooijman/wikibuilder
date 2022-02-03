package info.mooijman.action;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HtmlActionImpl implements Action {
    private static final Logger logger = LoggerFactory.getLogger(HtmlActionImpl.class);

    @Override
    public void execute(String dir) {

    }

    @Override
    public String filter() {

        saveToDisk();
        return null;
    }

    @Override
    public void setDir() {

    }

    protected boolean saveToDisk() {

        // ik verander iets aan de settings
//        super.saveToDisk();
        return true;
    }

    @Override
    public boolean listChildrenToDisk(File dir, JsonObject children,JsonArray stem) {

        String name = "index.html";
        String directory = dir.getAbsolutePath();
        try {
            File myJsonFile = new File(directory+File.separator+name);
            FileWriter myWriter = new FileWriter(myJsonFile);

            myWriter.write("<a href=\"../index.html\">parent</a> \n\r");
            myWriter.write("<br> \n\r");

            JsonArray temp = children.getJsonArray("directories");
            for (Object o : temp ) {
                if ( o instanceof JsonObject ) {
                    String dirName = ((JsonObject) o).getString("name");

                    myWriter.write(String.format("<a href=\"%s/index.html\">%s</a> %n",dirName,dirName));
                    myWriter.write("<br> \n\r");

                }
            }

            myWriter.write("<hr> \n\r");
            myWriter.write("<pre> \n\r");
            myWriter.write(children.encodePrettily());
            myWriter.write("</pre> \n\r");

            myWriter.close();
        } catch (IOException e) {
            logger.error("An error occurred.");
            e.printStackTrace();
        }

        logger.debug("name {}" , name);
        return true;
    }

}
