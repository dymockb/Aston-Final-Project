package screens;
import superclass.Screen;
import util.DBConnector;
import util.Parser;
import model.Table;

public class Shows extends Screen {

    private Table showsTable;

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

    public void addTable(Table showsTable){
        this.showsTable = showsTable;
    }

    public void displayPrompt(){
        System.out.println("Shows - available commands:");
    }


    public void getUserInput(){

        Boolean gettingInput = true;
        String userInput = null;
        while(gettingInput){

       

        }

    }
    
}
