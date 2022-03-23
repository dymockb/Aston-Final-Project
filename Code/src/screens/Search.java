package screens;

import superclass.Screen;
import util.DBConnector;
import util.Parser;
import java.sql.*;
import model.Table;

public class Search extends Screen {

    public Search(String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }

    public void displayPrompt(){
        System.out.println("Search menu - available commands:");
    }

    public void addOptions(){
        options.put("s", "search for a show by name or keyword. (not working)");
        options.put("n", "browse shows by name. (in progress)");
        options.put("c", "browse shows by category (e.g musical, opera). (not working)");
        options.put("d", "browse shows by performance date. (not working)");
    
    } 

    public void displayOptions(){

        for(String key : options.keySet()){
            System.out.println(key + " - " + options.get(key));
        }

    }

    public void getUserInput(){

        Boolean gettingInput = true;
        String userInput = null;
        while(gettingInput){

            userInput = parser.getInputForMenu();
            if(userInput.equals("s")){
                System.out.println("keyword searchh tbc");
                gettingInput = false;
            } else if (userInput.equals("n")){

                System.out.println("broswe by name in progress");
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
                user.newScreenRequest("home-screen");
                gettingInput = false;
            } else {
                System.out.println("invalid command");    
                if(user.getIsAutomated()){
                    gettingInput = false;
                }            
            }

        }

    }
    
}
