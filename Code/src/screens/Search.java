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
            System.out.println("k - Keyword");
            System.out.println("d - Date");
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
                
            } catch (NoSuchElementException e){
                
                System.out.println("End of test file.");
                gettingInput = false;
                noUserTextFileErrors = false;
            }

            if(userInput != null){

                if (userInput.equals("k")){
                    
                    gettingInput = false;

                    
                    String keyword = parser.getText("Please enter a keyword:");
                 
                	//if (keyword.isEmpty() || keyword.isBlank()) {
                	//	System.out.println("Please enter a show name");
                	//}

                	 String searchString = user.getSqlQueries().get("select-by-keyword") +"'"+ "%" + keyword + "%"+"';";
                     //System.out.println(searchString);
                     SearchDB keywordSearch = new SearchDB(searchString, db) ;
                     rs = keywordSearch.runSearch();

                     user.setPreviousSearch(searchString);
                     user.setIsDateSearch(false);

                     user.setSearchResultSet(rs);
                     nextScreen = "shows-screen";
                     
                } else if (userInput.equals("d")){
                    
                    gettingInput = false;


               	    System.out.println("**Start Date for the search:**");
                    String StartDate = getDate();
                    System.out.println("**End Date for the search**");
                    String EndDate = getDate();

                    System.out.println("Fetching results from "+ StartDate + " to " + EndDate );
                                                       
                    String searchString = user.getSqlQueries().get("select-by-date") + StartDate + " And " + EndDate + " order by ShowDate, ShowTime;";              

                    SearchDB searchByDateRange = new SearchDB(searchString, db) ;
                    
                    user.setEventName("Theatre Royal");
                    user.setPreviousSearch(searchString);
                    user.setIsDateSearch(true);

                    rs = searchByDateRange.runSearch();               
                    
                    user.setSearchResultSet(rs);
                    nextScreen = "performances-screen";
                    

                } else if (userInput.equals("n")){
                    gettingInput = false;

                    
                    String searchString = user.getSqlQueries().get("browse-shows") + "ORDER BY ShowName;";
                    SearchDB allShowsByName = new SearchDB(searchString, db) ;
                    
                    user.setPreviousSearch(searchString);
                    user.setIsDateSearch(false);

                    rs = allShowsByName.runSearch();

                    user.setSearchResultSet(rs);
                    nextScreen = "shows-screen";
                     

                } else if (userInput.equals("c")){
                    
                    gettingInput = false;

                    String searchString = user.getSqlQueries().get("browse-shows") + "ORDER BY TypeName;";
                    SearchDB allShowsByName = new SearchDB(searchString, db) ;
                    //user.saveNewSearch("all-shows-by-typeName", allShowsByName);
                    user.setPreviousSearch(searchString);
                    user.setIsDateSearch(false);

                    rs = allShowsByName.runSearch();

                    user.setSearchResultSet(rs);
                    nextScreen = "shows-screen";
                    //user.newScreenRequest("shows-screen");

                } else if (userInput.equals("g")){
                    //user.setAdminStatus(false);
                    gettingInput = false;
                    nextScreen = "basket-screen";

                    //user.newScreenRequest("home-screen");
                 } else if (userInput.equals("h")){
                    //user.setAdminStatus(false);
                    gettingInput = false;

                    //user.newScreenRequest("home-screen");
                    
                } else {

                    StaticPrinter.invalidCommand("Search");         
                
                }

            }

        }

        if(noUserTextFileErrors){
            user.newScreenRequest(nextScreen);
        }
        
    }
    
    private String getDate() {
    	 String date = "";
    	 String day = parser.getText("Please enter a day number: ");
    	 String[] months = {
    			 			"Jan", "Feb", "Mar","Apr", 
    			            "May", "Jun", "Jul", "Aug",
    			            "Sep", "Oct", "Nov","Dec"
    			            };
    	
    	 System.out.println("***Months***");
    	 for(int i=0; i<months.length; i++) {
    		 String month = months[i];
    		 System.out.println((i+1) + " - "+ month);
    	 }
    	 String monthNum = parser.getText("Please enter a month number:");
    	 String year  = parser.getText("Please enter a year:");
    	 if(Integer.parseInt(year) < 2022 || Integer.parseInt(year) == 0) {
    		 System.out.println("Invalid date input");
    	 }else if(Integer.parseInt(monthNum) < 1 || Integer.parseInt(monthNum) > 12) {
    		 System.out.println("Invalid date input");
    	 }else if(Integer.parseInt(day) < 1 || Integer.parseInt(day) > 31) {
    		 System.out.println("Invalid date input");
    	 }
    	 else {
    		 date = "'"+year+"-"+monthNum+"-"+day+"'";
   
    	 }
    	 return date;
    }
    
}
