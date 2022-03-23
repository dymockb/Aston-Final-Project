package screens;
import superclass.Screen;
import util.DBConnector;
import util.Parser;
import model.Table;
import util.IsInteger;
//import util.ScreenPrinter;

public class Shows extends Screen {

    public Shows(String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }

    public void addOptions(){
        options.put("b", "browse shows and book tickets (in progress)");
        options.put("q", "quit");
    
    }

    public void displayOptions(){

        for(String key : options.keySet()){
            System.out.println(key + " - " + options.get(key));
        }

    }

    public void displayPrompt(){
        System.out.println("Shows - available commands:");
    }

    public void browseTable(){

        Boolean browsing = true;
        Boolean hideRows = false;
        while(browsing){


            String userInput = user.getSearchResultsTable().startBrowsing(hideRows);

            if (IsInteger.checkString(userInput)){

                int selectedRowInt = Integer.parseInt(userInput);                       

                if (selectedRowInt <= user.getSearchResultsTable().getNumberOfRows()){

                    browsing = false;
        
                    System.out.println("Row " + selectedRowInt + " selected.");
        
                    int ShowID = Integer.parseInt(user.getSearchResultsTable().getFirstCellofSelectedRowInResultSet(selectedRowInt));
                    //browsingTable = false;
                    System.out.println("ShowID selected: " + ShowID);
        
                } else {
        
                    printer.rowSelectionNotAvailableMessage();
        
                }

            } else {

                printer.invalidCommand();
                hideRows = true;

            }


        }


    }


    public void getUserInput(){

        Boolean browsing= true;
        Boolean hideRows = false;
        while(browsing){

            String userInput = user.getSearchResultsTable().startBrowsing(hideRows);

            if (IsInteger.checkString(userInput)){

                int selectedRowInt = Integer.parseInt(userInput);                       

                if (selectedRowInt <= user.getSearchResultsTable().getNumberOfRows()){

                    browsing = false;
        
                    System.out.println("Row " + selectedRowInt + " selected.");
        
                    int ShowID = Integer.parseInt(user.getSearchResultsTable().getFirstCellofSelectedRowInResultSet(selectedRowInt));
                    //browsingTable = false;
                    System.out.println("ShowID selected: " + ShowID);
        
                } else {
        
                    printer.rowSelectionNotAvailableMessage();
        
                }

            } else {

                printer.invalidCommand();
                hideRows = true;

            }


        }

    }
    
}
