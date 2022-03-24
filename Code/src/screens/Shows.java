package screens;
import superclass.Screen;
import util.DBConnector;
import util.Parser;

import util.IsInteger;
import java.sql.*;
import model.Table;


public class Shows extends Screen {

    public Shows(String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }

    public void displayScreen(){

        ResultSet rs = user.getSearchResultSet();
        String tableName = "All Shows";
        String orderedBy = "Name";
        Table showsTable = new Table(rs, parser, tableName, orderedBy);

        Boolean browsing = true;
        Boolean hideRows = false;
        while(browsing){

            String userInput = showsTable.startBrowsing( hideRows, 
                                                        standardOptions, 
                                                        user.getIsLoggedIn(), 
                                                        user.getCurrentScreenName().equals("home-screen"));

            if (IsInteger.checkString(userInput)){

                int selectedRowInt = Integer.parseInt(userInput);                       

                if (selectedRowInt <= showsTable.getNumberOfRows()){

                    browsing = false;
        
                    System.out.println("Row " + selectedRowInt + " selected.");
        
                    int showID = Integer.parseInt(showsTable.getFirstCellofSelectedRowInResultSet(selectedRowInt));
                    
                    System.out.println("ShowID selected: " + showID);

                    rs = db.runQuery(user.getSqlQueries().get("get-show-by-ID") + showID + ";");  

                    user.setSearchResultSet(rs);
                    user.newScreenRequest("single-show");

                } else {
        
                    printer.rowSelectionNotAvailableMessage();
        
                }

            } else if (userInput.equals("h")) {
                browsing = false;
                user.newScreenRequest("home-screen");

            } else {

                printer.invalidCommand(); 
                hideRows = true;

            }

        }


    }
 
    
}
