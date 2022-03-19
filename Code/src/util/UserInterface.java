package util;

import java.sql.ResultSet;
import java.util.HashMap;

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
    private Parser parser;
    private ResultProcessor resultProcessor;

    /**
     * .    
     * */ 
    public UserInterface(DBConnector db, ScreenPrinter printer, HashMap<String, String> sqlQueries) {

        this.db = db;
        this.printer = printer;
        this.sqlQueries = sqlQueries;
        resultProcessor = new ResultProcessor();

    }

    public void setInputParser(Parser parser){
        this.parser = parser;
    }

    public Boolean mainMenu(){

        String command = parser.getInput("main-menu", "Main menu");
        
        Boolean returnType = true;
        
        if (command.equals("q")){
            returnType = false;
        } else if (command.equals("b")){
            browseShows();
        } else if (command.equals("l")){
            System.out.println("The admin login interface isn't built yet.");
        } else {
            printer.invalidCommand();
        }

        return returnType;

    }

    private void browseShows(){

        String userInput;
        Boolean browsing;

        browsing = true;
        while(browsing){

            userInput = parser.getInput("choose-browsing-mode", "");

            if (userInput.equals("s")){

                System.out.println("Search for show by name or keyword not built yet"); 

            } else if (userInput.equals("a")) {

                rs = db.runQuery(sqlQueries.get("browse-shows-in-alphabetical-order"));

                Boolean browsingTable = true;
        
                while(browsingTable){
        
                    resultProcessor.browseTable(rs);
                    
                    userInput = parser.getInput("browse-table", "show");
        
                    if(userInput.equals("q")){
                        browsingTable = false;
                    } else {
                        printer.invalidCommand();
                        browsingTable = false;
                    }
        
                }



            } else if (userInput.equals("c")) {
                System.out.println("Display shows by category not built yet");
            } else if (userInput.equals("d")) {
                System.out.println("Display shows by performance date not built yet");
            } else if (userInput.equals("q")) {
                browsing = false;
            } else {
                printer.invalidCommand();
            }

        }



    }


    /*
    private void selectAllShows(){
        rs = db.runQuery(sqlQueries.get("select-all-shows"));
        printer.printResults(rs);
    }
    */


}
