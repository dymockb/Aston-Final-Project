package screens;
import util.Parser;
import util.StaticPrinter;
import util.DBConnector;
import superclass.Screen;
import superclass.SearchDB;

import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.ArrayList;
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
        ArrayList<String> columnsToHide = new ArrayList<String>();
        columnsToHide.add("ID");
        columnsToHide.add("ShowName");

        Table performancesTable = new Table(rs, parser, eventName, tableName, orderedBy, columnNames, columnsToHide, true);

        Boolean browsing = true;
        Boolean hideRows = false;

        String nextScreen = "home-screen";

        while(browsing){

            String userInputFromTable = null;

            userInputFromTable = performancesTable.startBrowsing(hideRows, user.getIsLoggedIn());
            
            if(userInputFromTable == null){
                browsing = false;
                noUserTextFileErrors = false;
            }
            
            if (IsInteger.checkString(userInputFromTable)){

                int selectedRowInt = Integer.parseInt(userInputFromTable);                       

                if (selectedRowInt <= performancesTable.getNumberOfRows()){

                    browsing = false;
        
                    //System.out.println("Row " + selectedRowInt + " selected.");
                    String performanceID = performancesTable.getFirstCellofSelectedRowInResultSet(selectedRowInt);
                    //System.out.println("PerformanceID selected: " + performanceID);

                    String stringTemplate = user.getSqlQueries().get("get-performance-by-ID");
                    String searchString = stringTemplate.replace("performance-id-from-java", performanceID);
                    searchString += ";";
                    //System.out.println(searchString); 
                    SearchDB getSelectedPerformance = new SearchDB(searchString, db);

                    rs = getSelectedPerformance.runSearch();

                    user.setSearchResultSet(rs);
                    nextScreen = "view-performance";

                } else {
        
                    printer.rowSelectionNotAvailableMessage();
        
                }

            } else if (userInputFromTable != null) {

                if (userInputFromTable.equals("n")){
                    browsing = false;
                    nextScreen = "search-screen";
                } else {
                    System.out.println("Perfomances - 1");
                    StaticPrinter.invalidCommand("Performances"); 
                    hideRows = true;
                }

            }

        }
        
        if(noUserTextFileErrors){
            user.newScreenRequest(nextScreen);
        }
        


    }

    
}
