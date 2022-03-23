package screens;
import superclass.Screen;
import util.DBConnector;
import util.Parser;

import util.IsInteger;


public class Shows extends Screen {

    public Shows(String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }


    public void displayScreenOptions(){
        //System.out.println("Shows - available commands:");
    }


    public void getUserInput(){

        Boolean browsing = true;
        Boolean hideRows = false;
        while(browsing){


            String userInput = user.getSearchResultsTable().startBrowsing( hideRows, 
                                                                           standardOptions, 
                                                                           user.getIsLoggedIn(), 
                                                                           user.getCurrentScreenName().equals("home-screen"));

            if (IsInteger.checkString(userInput)){

                int selectedRowInt = Integer.parseInt(userInput);                       

                if (selectedRowInt <= user.getSearchResultsTable().getNumberOfRows()){

                    browsing = false;
        
                    System.out.println("Row " + selectedRowInt + " selected.");
        
                    int showID = Integer.parseInt(user.getSearchResultsTable().getFirstCellofSelectedRowInResultSet(selectedRowInt));
                    
                    System.out.println("ShowID selected: " + showID);

                    rs = db.runQuery(user.getSqlQueries().get("get-show-by-ID") + showID + ";");  

                    //String tableName = "selected-show";
                    //Table selectedShow = new Table(rs, parser, tableName);
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
