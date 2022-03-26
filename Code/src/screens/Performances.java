package screens;
import util.Parser;
import util.DBConnector;
import superclass.Screen;
import superclass.SearchDB;

import java.util.NoSuchElementException;
import java.sql.*;
import java.util.HashMap;
import model.Table;
import util.IsInteger;

public class Performances extends Screen {

    public Performances (String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }

    public void displayScreen() throws NoSuchElementException {

        rs = user.getSearchResultSet();

        String tableName = "All Performances";
        String eventName = user.getEventName();
        String orderedBy = "Date";

        HashMap<String, String> columnNames = new HashMap<String, String>();

        Table performancesTable = new Table(rs, parser, eventName, tableName, orderedBy, columnNames, true);

        Boolean browsing = true;
        Boolean hideRows = false;

        String nextScreen = "home-screen";

        while(browsing){

            try{

            String userInput = performancesTable.startBrowsing(hideRows, user.getIsLoggedIn());

            if (IsInteger.checkString(userInput)){

                int selectedRowInt = Integer.parseInt(userInput);                       

                if (selectedRowInt <= performancesTable.getNumberOfRows()){

                    browsing = false;
        
                    //System.out.println("Row " + selectedRowInt + " selected.");
        
                    int performanceID = Integer.parseInt(performancesTable.getFirstCellofSelectedRowInResultSet(selectedRowInt));
                    
                    //System.out.println("PerformanceID selected: " + performanceID);

                    String stringTemplate = user.getSqlQueries().get("get-performance-by-ID");
                    String searchString = stringTemplate.replace("show-id-from-java", user.getIDValueForNextSearch());
                    searchString += ";";
                    SearchDB getSelectedPerformance = new SearchDB(searchString, db);

                    user.saveNewSearch("selected-performance-search", getSelectedPerformance);
                    user.setPreviousSearch("selected-performance-search");

                    rs = getSelectedPerformance.runSearch();
                    //rs = db.runQuery(user.getSqlQueries().get("get-performance-by-ID") + performanceID + ";");  

                    user.setSearchResultSet(rs);
                    nextScreen = "view-performance";
                    //user.newScreenRequest("single-show");

                } else {
        
                    printer.rowSelectionNotAvailableMessage();
        
                }

            } else if (userInput.equals("n")) {
                browsing = false;
                nextScreen = "search-screen";
                //user.newScreenRequest("home-screen");

            } else {

                printer.invalidCommand(); 
                hideRows = true;

            }


            } catch (NoSuchElementException e){

                System.out.println("ERROR - end of test file.");
                browsing= false;
                noUserTextFileErrors = false;
            }



        }
        
        if(noUserTextFileErrors){
            user.newScreenRequest(nextScreen);
        }
        


    }

    
}
