package screens;
import superclass.Screen;
import util.DBConnector;
import util.Parser;

import java.util.NoSuchElementException;

import util.IsInteger;
import java.sql.*;
import java.util.HashMap;
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
        
        Table showsTable = new Table(rs, parser, eventName, tableName, orderedBy, columnNames, false);

        Boolean browsing = true;
        Boolean hideTable = false;

        String nextScreen = "home-screen";

        while(browsing){

            try{

            String userInput = showsTable.startBrowsing(hideTable, user.getIsLoggedIn());

            if (IsInteger.checkString(userInput)){

                int selectedRowInt = Integer.parseInt(userInput);                       

                if (selectedRowInt <= showsTable.getNumberOfRows()){

                    browsing = false;
        
                    //System.out.println("Row " + selectedRowInt + " selected.");
        
                    int showID = Integer.parseInt(showsTable.getFirstCellofSelectedRowInResultSet(selectedRowInt));
                    
                    //System.out.println("ShowID selected: " + showID);
                    String showName = getEventName(showID);

                    rs = db.runQuery(user.getSqlQueries().get("get-show-by-ID") + showID + ";");  

                    user.setEventName(showName);
                    user.setSearchResultSet(rs); 
                    user.setIDValueForNextSearch(showID);
                    nextScreen = "view-show";
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
                hideTable = true;

            }


            } catch (NoSuchElementException e){

                System.out.println("ERROR - end of test file.");
                browsing= false;
                noUserTextFileErrors = false;
            }



        }
        if (noUserTextFileErrors){
            user.newScreenRequest(nextScreen);
        }
        


    }

    public String getEventName(int idx){

        //int idx = Integer.parseInt(IDindex);
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
