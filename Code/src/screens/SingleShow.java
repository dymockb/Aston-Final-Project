package screens;
import superclass.Screen;
import util.DBConnector;
import util.Parser;
import superclass.SearchDB;

import java.util.NoSuchElementException;

import model.Show;
import util.StaticPrinter;

public class SingleShow extends Screen {

    private Show show;

    public SingleShow (String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }

    public void displayScreen() throws NoSuchElementException {

        show = new Show(user.getSearchResultSet());

        System.out.println(show.getShowDetails());

        System.out.println("v - view performances");
        System.out.println("b - back to search results");
        System.out.println("h - return to home screen");
        
        Boolean viewingShow = true;
        while(viewingShow){

            try {

                String userInput = parser.getInputForMenu();
                if (userInput.equals("v")){
                    viewingShow = false;

                    String searchString = user.getSqlQueries().get("all-performances-for-single-show") + "ORDER BY ShowDateTime;";
                    SearchDB showPerformancesByDate = new SearchDB(searchString, db) ;
                    user.saveNewSearch("shows-performances-by-date", showPerformancesByDate);
                    user.setPreviousSearch("shows-performances-by-date");

                    rs = showPerformancesByDate.runSearch();

                    user.setSearchResultSet(rs);
                    user.newScreenRequest("performances-screen");


                } else if (userInput.equals("h")) {
                    viewingShow = false;
                    user.newScreenRequest("home-screen");
                }else if (userInput.equals("b")) {
                    viewingShow = false;
    
                    rs = user.getSearchHistory().get(user.getPreviousSearch()).runSearch();
                    user.setSearchResultSet(rs);
                    user.newScreenRequest("shows-screen");
                    
                } else {
                    StaticPrinter.invalidCommand();
                }

            } catch (NoSuchElementException e){
                System.out.println("ERROR - end of test file.");
                viewingShow = false;
            }
            

        }

    }

    
}
