package screens;
import superclass.Screen;
import util.DBConnector;
import util.Parser;
import util.StaticPrinter;

import java.util.NoSuchElementException;

import util.IsInteger;
import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import model.Table;


public class Shows extends Screen {

    public Shows(String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }

    public void displayScreen() throws NoSuchElementException {

        rs = user.getSearchResultSet();
        String tableName = "All Shows";
        String eventName = "Theatre Royal";
        String orderedBy = "Name";

        HashMap<String, String> columnNames = new HashMap<String, String>();
        columnNames.put("ShowName", "Title");
        columnNames.put("ShowDescription", "Description");
        columnNames.put("TypeName", "Category");
        
        ArrayList<String> columnsToHide = new ArrayList<String>();
        columnsToHide.add("ID");

        Table showsTable = new Table(rs, parser, eventName, tableName, orderedBy, columnNames, columnsToHide, false);

        Boolean browsing = true;
        Boolean hideTable = false;

        String nextScreen = "home-screen";
        String userInputFromTable = null;

        while(browsing){

            userInputFromTable = showsTable.startBrowsing(hideTable, user.getIsLoggedIn());
            
            if(userInputFromTable != null){

                if (IsInteger.checkString(userInputFromTable)){

                    int selectedRowInt = Integer.parseInt(userInputFromTable);                       
    
                    if (selectedRowInt <= showsTable.getNumberOfRows()){
    
                        browsing = false;
            
                        int showID = Integer.parseInt(showsTable.getFirstCellofSelectedRowInResultSet(selectedRowInt));
                        
                        String showName = getEventName(showID);
    
                        String searchString = user.getSqlQueries().get("get-show-by-ID") + showID + ";";
                        rs = db.runQuery(searchString); 
    
                        //SearchDB allShowsByName = new SearchDB(searchString, db) ;
                        //searchString = user.getSqlQueries().get("browse-shows") + "ORDER BY ShowName;";
                        //user.saveNewSearch("all-shows-by-name", allShowsByName);
    
                        user.setPreviousSearch(user.getSqlQueries().get("browse-shows") + "ORDER BY ShowName;");
    
                        user.setEventName(showName);
                        user.setIDValueForNextSearch(showID);
                        user.setSearchResultSet(rs);      
                        nextScreen = "view-show";
    
                    } else {
            
                        printer.rowSelectionNotAvailableMessage();
            
                    }
    
                } else if (userInputFromTable.equals("n")) {
                    browsing = false;
                    nextScreen = "search-screen";
    
                } else {
    
                    StaticPrinter.invalidCommand("Table - Shows screen"); 
                    hideTable = true;
    
                }

            } else {
                browsing = false;
                noUserTextFileErrors = false;
            }

        }

        if (noUserTextFileErrors){
            user.newScreenRequest(nextScreen);
        }
        
    }

    public String getEventName(int idx){

        String output = "";
        try {
        
            rs.beforeFirst();
            while(rs.next()){
                if (Integer.parseInt(rs.getString(1)) == idx){
                    break;
                }

            }

            output = rs.getString(2);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return output;

    }
 
    
}
