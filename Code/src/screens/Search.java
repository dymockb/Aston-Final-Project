package screens;

import superclass.Screen;
import superclass.SearchDB;
import util.DBConnector;
import util.Parser;
//import java.sql.*;
//import model.Table;

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



    public void displayScreen(){

        Boolean gettingInput = true;
        String userInput = null;
        while(gettingInput){

            System.out.println("Search:");
            System.out.println("s - Search for a show by name or keyword. (not working)");
            System.out.println("");
            System.out.println("Browse:");
            System.out.println("n - Browse shows by ShowName");
            System.out.println("c - Browse shows by TypeName");
            System.out.println("d - Browse shows by DATE.");
            System.out.println("");
            displayLoginLogout();
            System.out.println("h - return to Home Screen.");

            userInput = parser.getInputForMenu();


            if(userInput.equals("s")){
                System.out.println("keyword searchh tbc");
                gettingInput = false;
            } else if (userInput.equals("n")){
                gettingInput = false;

                String searchString = user.getSqlQueries().get("browse-shows") + "ORDER BY ShowName;";
                SearchDB allShowsByName = new SearchDB(searchString, db) ;
                user.saveNewSearch("all-shows-by-name", allShowsByName);
                user.setPreviousSearch("all-shows-by-name");

                rs = allShowsByName.runSearch();

                user.setSearchResultSet(rs);
                user.newScreenRequest("shows-screen");

            } else if (userInput.equals("c")){
                
                gettingInput = false;

                String searchString = user.getSqlQueries().get("browse-shows") + "ORDER BY TypeName;";
                SearchDB allShowsByName = new SearchDB(searchString, db) ;
                user.saveNewSearch("all-shows-by-typeName", allShowsByName);
                user.setPreviousSearch("all-shows-by-TypeName");

                rs = allShowsByName.runSearch();

                user.setSearchResultSet(rs);
                user.newScreenRequest("shows-screen");

            } else if (userInput.equals("d")){
                
                System.out.println("Please enter a start date for the search range (dd-mm-yy):");
                String startDate = parser.getInputForMenu();

                System.out.println("Please enter a start date for the search range (dd-mm-yy):");
                String endDate = parser.getInputForMenu();
                
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
