package info.mooijman;

import info.mooijman.action.Action;

public class DirectoryWalker {
    private final Action action;

    public DirectoryWalker(Action action) {
        this.action = action;
    }


    public void doStuff(){
        action.setDir();
        action.execute("dir");
    }

}
