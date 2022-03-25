package screens;
import util.Parser;
import util.DBConnector;
import superclass.Screen;
import java.util.NoSuchElementException;
import java.sql.*;
import model.Table;
import util.IsInteger;

public class Performances extends Screen {

    public Performances (String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }

    public void displayScreen() throws NoSuchElementException {

        ResultSet rs = user.getSearchResultSet();
        String tableName = "All Performances";
        String orderedBy = "Date";
        Table showsTable = new Table(rs, parser, tableName, orderedBy);

        Boolean browsing = true;
        Boolean hideRows = false;

        String nextScreen = "home-screen";

        while(browsing){

            try{

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
                    
                    System.out.println("PerformanceID selected: " + showID);

                    //rs = db.runQuery(user.getSqlQueries().get("get-show-by-ID") + showID + ";");  

                    //user.setSearchResultSet(rs);
                    //nextScreen = "single-show";
                    //user.newScreenRequest("single-show");

                } else {
        
                    printer.rowSelectionNotAvailableMessage();
        
                }

            } else if (userInput.equals("h")) {
                browsing = false;
                //user.newScreenRequest("home-screen");

            } else {

                printer.invalidCommand(); 
                hideRows = true;

            }


            } catch (NoSuchElementException e){

                System.out.println("ERROR - end of test file.");
                browsing= false;
            }



        }

        user.newScreenRequest(nextScreen);


    }


    
}
