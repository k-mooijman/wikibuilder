package info.mooijman.action;

public class GitActionImpl extends ActionBase implements Action  {
    @Override
    public void execute(String dir) {

    }

    @Override
    public String filter() {
        return "een";
    }

    @Override
    public void setDir(){

    }

    public void getInfo(){

        saveToDisk();

    }



}
