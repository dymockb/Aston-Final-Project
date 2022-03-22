package util;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.lang.Integer;

import model.Browse;
import model.PerformanceTable;

/**
 * 
 * This responds to the user input 
 *  
 * @author  
 * @version 1.0
 */
public class UserInterface
{

    //private ResultSet rs;
    private DBConnector db;
    private ScreenPrinter printer;
    private HashMap<String,String> sqlQueries;
    private Parser parser;
    //private ResultProcessor resultProcessor;
    private ArrayList<Boolean> switches;
    private Browse browse;
    /**
     * .    
     * */ 
    public UserInterface(DBConnector db, ScreenPrinter printer, HashMap<String, String> sqlQueries) {

        this.db = db;
        this.printer = printer;
        this.sqlQueries = sqlQueries;
        //resultProcessor = new ResultProcessor();

        switches = new ArrayList<Boolean>();
        switches.add(false);

    }

    public void setInputParser(Parser parser){
        this.parser = parser;
    }

    public Boolean mainMenu(){

        String command = parser.getInput("main-menu", "Main menu", switches);
        
        Boolean returnType = true;
        
        if (command.equals("q")){
            returnType = false;
        } else if (command.equals("b")){
 
            browseShows();
   
        } else if (command.equals("l")){
            System.out.println("The admin login interface isn't built yet.");
        } else if (command.equals("t")) {

            System.out.println("Code for buying a ticket in progress");

        } else {
            printer.invalidCommand();
        }

        return returnType;

    }

    private void browseShows(){

        Boolean browsing = true;

        while(browsing){

            String userInput = parser.getInput("choose-browsing-mode", "", switches);

            if (userInput.equals("s")){

                System.out.println("Search for show by name or keyword not built yet"); 

            } else if (userInput.equals("a")) {

                browse = new Browse(db, sqlQueries.get("browse-shows"), "ORDER BY ShowName;");

                browse.fetchData();
                ResultSet results = browse.returnResults();
                PerformanceTable allPerformances = new PerformanceTable(results, parser);

                int theSelectedShowIs = allPerformances.startBrowsing();
                System.out.println("The selected show is: " + theSelectedShowIs);

                browse = new Browse(db, sqlQueries.get("get-show-by-ID") + theSelectedShowIs, ";");
                browse.fetchData();
                results = browse.returnResults();

                PerformanceTable selectedShow = new PerformanceTable(results, parser);
                selectedShow.startBrowsing();


            } else if (userInput.equals("c")) {

                System.out.println("Display shows by category not built yet");

            } else if (userInput.equals("d")) {

                System.out.println("Display shows by performance date not built yet");

            } else if (userInput.equals("b")) {

                browsing = false;

            } else {

                printer.invalidCommand();

            }

        }

    }

    public void viewUniqueShow(int showID){

        Boolean viewingShow = true;

        System.out.println("the show ID is: " + showID);

        while (viewingShow) {
            
            String userInput = parser.getInput("unique-show", "TBC", switches);
            
            if (userInput.equals("b")){

                System.out.println("Browse available dates (not working)");

            } else if (userInput.equals("q")) {

                viewingShow = false;

            } else {

                printer.invalidCommand();

            }

        }

    }

    public boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }

}
