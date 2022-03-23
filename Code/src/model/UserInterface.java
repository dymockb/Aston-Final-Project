package model;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.lang.Integer;

//import model.Browse;
import model.Table;
import model.Show;
//import util.IsInteger;
import util.DBConnector;
import util.Parser;
import util.Router;
import util.ScreenPrinter;
import superclass.Screen;

/**
 * 
 * This responds to the user input 
 *  
 * @author  
 * @version 1.0
 */
public class UserInterface
{

    private ResultSet rs;
    private DBConnector db;
    private ScreenPrinter printer;
    private HashMap<String,String> sqlQueries;
	private HashMap<String, Screen> screens;
    private Parser parser;
    //private ResultProcessor resultProcessor;
    private ArrayList<Boolean> switches;
    //private Browse browse;
    /**
     * .    
     * */ 
    public UserInterface(DBConnector db, ScreenPrinter printer, HashMap<String, String> sqlQueries) {

        this.db = db;
        this.printer = printer;
        this.sqlQueries = sqlQueries;
        screens = new HashMap<String, Screen>();
        //resultProcessor = new ResultProcessor();

        switches = new ArrayList<Boolean>();
        switches.add(false);

    }

    public void setInputParser(Parser parser){
        this.parser = parser;
    }
    
    public void addScreen(String screenName, Screen screen){
        screens.put(screenName, screen);
    }

    public void start(){

        //Router router = new Router();
        //router.goToScreen("home-screen");
        goToScreen("home-screen");

         
    }

    public void goToScreen(String screenName){

        Screen screen = screens.get(screenName);

        screen.displayContent();
        screen.displayMenu();
        screen.getUserInput();

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

            } else if (userInput.equals("n")) {

                //browse = new Browse(db, sqlQueries.get("browse-shows"), "ORDER BY ShowName;");

                //browse.fetchData();
                //ResultSet results = browse.returnResults();
                rs = db.runQuery(sqlQueries.get("browse-shows") + "ORDER BY ShowName;");
                String tableName = "All Shows";
                Table allShows = new Table(rs, parser, tableName);
                int selectedShowID = allShows.startBrowsing();

                if (selectedShowID != -1){
                    System.out.println("The selected show is: " + selectedShowID);
                    rs = db.runQuery(sqlQueries.get("get-show-by-ID") + selectedShowID + ";");  

                    //tableName = "Selected Show";
                    //Table selectedShow = new Table(rs, parser, tableName);
                    //selectedShow.startBrowsing();
                    Show aShowClass = new Show(rs);
                    aShowClass.getPerformances();

                }
                



                //browse = new Browse(db, sqlQueries.get("get-show-by-ID") + theSelectedShowIs, ";");
                //browse.fetchData();
                //results = browse.returnResults();


            } else if (userInput.equals("c")) {

                rs = db.runQuery(sqlQueries.get("browse-shows") + "ORDER BY TypeName;");
                String tableName = "All Shows";
                Table allShows = new Table(rs, parser, tableName);
                int selectedShowID = allShows.startBrowsing();

                if (selectedShowID != -1){
                    System.out.println("The selected show is: " + selectedShowID);
                    rs = db.runQuery(sqlQueries.get("get-show-by-ID") + selectedShowID + ";");                

                    tableName = "Selected Show";
                    Table selectedShow = new Table(rs, parser, tableName);
                    selectedShow.startBrowsing();
                }

            } else if (userInput.equals("d")) {

                System.out.println("Display shows by performance date not built yet");

            } else if (userInput.equals("x")) {

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

    /** 

    public boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }
    /** */
}
