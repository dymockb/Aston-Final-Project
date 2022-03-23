package screens;
import superclass.Screen;
import util.DBConnector;
import util.Parser;
import model.Table;

public class Shows extends Screen {

    public Shows(String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }

    public void addOptions(){
        options.put("b", "browse shows and book tickets (in progress)");
        options.put("q", "quit");
    
    }

    public void displayOptions(){

        for(String key : options.keySet()){
            System.out.println(key + " - " + options.get(key));
        }

    }

    public void displayPrompt(){
        System.out.println("Shows - available commands:");
    }

    public void browseTable(){
        String selectedShow = user.getSearchResultsTable().startBrowsing();
        System.out.println("the selected show is: ");
        System.out.println(selectedShow);
    }


    public void getUserInput(){

        Boolean gettingInput = true;
        String userInput = null;
        while(gettingInput){

       

        }

    }
    
}
