package info.mooijman.action;

import io.vertx.core.json.JsonObject;

import java.io.File;

public class GitActionImpl extends ActionBase implements Action  {
    @Override
    public void execute(String dir) {

    }

    @Override
    public String filter() {
        return "een";
    }

    @Override
    public boolean listChildrenToDisk(File dir, JsonObject children) {
        return false;
    }


    @Override
    public void setDir(){

    }

    public void getInfo(){

        saveToDisk();

    }



}
