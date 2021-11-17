package info.mooijman.action;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HtmlAction2Impl implements Action {
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
    public boolean listChildrenToDisk(File dir, JsonObject children , JsonArray stem) {
        String name = dir.getName() + ".json";
        String directory = dir.getAbsolutePath();
        try {
            File myJsonFile = new File(directory+File.separator+name);
            FileWriter myWriter = new FileWriter(myJsonFile);
            myWriter.write(children.encodePrettily());
            myWriter.write("\r\n");
            myWriter.write(stem.encodePrettily());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("name " + name);
        return true;
    }

}
