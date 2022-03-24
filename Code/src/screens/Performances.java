package screens;
import util.Parser;
import util.DBConnector;
import superclass.Screen;

public class Performances extends Screen {

    public Performances (String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }

    public void displayScreen(){

        System.out.println("the performance shows here");

    }
    
}
