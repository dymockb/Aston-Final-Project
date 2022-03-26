package screens;

import java.util.NoSuchElementException;

import superclass.Screen;
import superclass.SearchDB;
import util.DBConnector;
import util.Parser;
//import java.sql.*;
//import model.Table;
import util.StaticPrinter;

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



    public void displayScreen() throws NoSuchElementException {

        Boolean gettingInput = true;
        String userInput = null;

        String nextScreen = "home-screen";

        while(gettingInput){

            StaticPrinter.printSearchScreenHeader();

            System.out.println("Search for Shows By:");
            System.out.println("k - Keyword or Name. (not working)");
            System.out.println("d - Date (not working)");
            System.out.println("");
            System.out.println("Browse Shows By:");
            System.out.println("n - Name");
            System.out.println("c - Category");
            System.out.println("");
            System.out.println("g - Go to basket.");
            System.out.println("h - Home Screen.");
            displayBasketStatus();

            

            try {
                userInput = parser.getInputForMenu();


                if(userInput.equals("k")){
                    System.out.println("keyword search tbc");
                    gettingInput = false;
                } else if (userInput.equals("n")){
                    gettingInput = false;

                    
                    String searchString = user.getSqlQueries().get("browse-shows") + "ORDER BY ShowName;";
                    SearchDB allShowsByName = new SearchDB(searchString, db) ;
                    user.saveNewSearch("all-shows-by-name", allShowsByName);
                    user.setPreviousSearch("all-shows-by-name");

                    rs = allShowsByName.runSearch();

                    user.setSearchResultSet(rs);
                    nextScreen = "shows-screen";
                    //user.newScreenRequest("shows-screen");
                     

                } else if (userInput.equals("c")){
                    
                    gettingInput = false;

                    String searchString = user.getSqlQueries().get("browse-shows") + "ORDER BY TypeName;";
                    SearchDB allShowsByName = new SearchDB(searchString, db) ;
                    user.saveNewSearch("all-shows-by-typeName", allShowsByName);
                    user.setPreviousSearch("all-shows-by-TypeName");

                    rs = allShowsByName.runSearch();

                    user.setSearchResultSet(rs);
                    nextScreen = "shows-screen";
                    //user.newScreenRequest("shows-screen");

                } else if (userInput.equals("d")){
                    
                    System.out.println("Please enter a start date for the search range (dd-mm-yy):");
                    String startDate = parser.getInputForMenu();

                    System.out.println("Please enter an end date for the search range (dd-mm-yy):");
                    String endDate = parser.getInputForMenu();

                    System.out.println("Now run a search for PERFORMANCES between " + startDate + " and " + endDate);
                    

                } else if (userInput.equals("h")){
                    //user.setAdminStatus(false);
                    gettingInput = false;

                    //user.newScreenRequest("home-screen");
                    
                } else {
                    System.out.println("invalid command");            
                
                }

            } catch (NoSuchElementException e){
                
                System.out.println("ERROR - end of test file.");
                gettingInput = false;
                noUserTextFileErrors = false;
            }



        }

        if(noUserTextFileErrors){
            user.newScreenRequest(nextScreen);
        }
        

    }
    
}
