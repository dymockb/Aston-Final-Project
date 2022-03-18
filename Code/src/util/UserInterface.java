package util;

import java.sql.ResultSet;
import java.util.HashMap;

//import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

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

    /**
     * .    
     * */ 
    public UserInterface(DBConnector db, ScreenPrinter printer, HashMap<String, String> sqlQueries) {

        this.db = db;
        this.printer = printer;
        this.sqlQueries = sqlQueries;

    }

    public void setInputParser(Parser parser){
        this.parser = parser;
    }

    public Boolean mainMenu(){

        String command = parser.getInput("main-menu");
        
        Boolean returnType = true;
        
        if (command.equals("q")){
            returnType = false;
        } else if (command.equals("b")){
            browseShows();
        } else if (command.equals("a")) {
            selectAllShows();
        } else {
            System.out.println("Invalid Command.");
        }

        return returnType;

    }

    private void browseShows(){
        rs = db.runQuery(sqlQueries.get("browse-shows"));
        Boolean browsing = true;
        while(browsing){
            browsing = queryResults.browseTable(rs, 5);
        }

    }

    private void selectAllShows(){
        rs = db.runQuery(sqlQueries.get("select-all-shows"));
        printer.printResults(rs);
    }


}
