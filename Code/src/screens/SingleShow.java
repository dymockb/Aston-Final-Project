package screens;
import superclass.Screen;
import util.DBConnector;
import util.Parser;
import model.Show;
import util.StaticPrinter;

public class SingleShow extends Screen {

    private Show show;

    public SingleShow (String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }

    public void displayScreenOptions(){
    
    }

    public void getUserInput(){

        show = new Show(user.getSearchResultSet());
        
        Boolean viewingShow = true;
        while(viewingShow){
            
            String userInput = parser.getInputForMenu();
            if (userInput.equals("q")){
                viewingShow = false;
            } else if (userInput.equals("h")) {
                viewingShow = false;
                user.newScreenRequest("home-screen");
            } else {
                StaticPrinter.invalidCommand();
            }
        }

    }

    
}
