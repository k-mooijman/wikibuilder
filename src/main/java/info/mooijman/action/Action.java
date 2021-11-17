package info.mooijman.action;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.io.File;

public interface Action {

    void execute(String dir);

    void setDir();

    String filter();

    boolean listChildrenToDisk(File dir, JsonObject children, JsonArray stam);


}
