package screens;

import superclass.Screen;
import util.DBConnector;
import util.Parser;
//import java.sql.*;
import model.Table;

public class Search extends Screen {

    public Search(String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }

    public void displayScreenOptions(){
        System.out.println("Search upcoming Shows - available commands:");
        System.out.println("Search:");
        System.out.println("s - search for a show by name or keyword. (not working)");
        System.out.println("Browse:");
        System.out.println("n - Browse shows by NAME. (in progress)");
        System.out.println("c - Browse shows by CATEGORY (e.g musical, opera). (not working)");
        System.out.println("d - Browse shows by performance DATE. (not working)");
    }

    /** 

    public void addOptions(){
        options.put("s", "Search (name or keyword). not working");
        options.put("n", "Name search (in progress)");
        options.put("c", "Category search (e.g musical, opera). (not working)");
        options.put("d", "Date search. (not working)");
    
    } 

    public void displayOptions(){

        for(String key : options.keySet()){
            System.out.println(key + " - " + options.get(key));
        }

        
    /** */

    public void getUserInput(){

        Boolean gettingInput = true;
        String userInput = null;
        while(gettingInput){

            userInput = parser.getInputForMenu();
            if(userInput.equals("s")){
                System.out.println("keyword searchh tbc");
                gettingInput = false;
            } else if (userInput.equals("n")){

                System.out.println("browse by name in progress");
                gettingInput = false;

                rs = db.runQuery(user.getSqlQueries().get("browse-shows") + "ORDER BY ShowName;");
                String tableName = "All Shows";
                Table allShows = new Table(rs, parser, tableName);
                user.setSearchResultsTable(allShows);
                user.newScreenRequest("shows-screen");

            } else if (userInput.equals("c")){
                System.out.println("broswe by category in progress");
                gettingInput = false;
            } else if (userInput.equals("d")){
                System.out.println("broswe by date in progress");
                gettingInput = false;
            } else if (userInput.equals("h")){
                //user.setAdminStatus(false);
                gettingInput = false;
                user.newScreenRequest("home-screen");
                
            } else {
                System.out.println("invalid command");    
                if(user.getIsAutomated()){
                    gettingInput = false;
                }            
            }

        }

    }
    
}
