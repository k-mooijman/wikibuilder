package info.mooijman.action;

public class HtmlActionImpl extends ActionBase implements Action{
    @Override
    public void execute(String dir) {

    }

    @Override
    public String filter() {

        saveToDisk();
        return null;
    }

    @Override
    public void setDir(){

    }

    protected boolean saveToDisk (){

        // ik verander iets aan de settings
        super.saveToDisk();
        return true;
    }

}
