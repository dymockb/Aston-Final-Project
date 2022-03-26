package screens;
import superclass.Screen;
import util.DBConnector;
import util.Parser;
import superclass.SearchDB;
import java.util.HashMap;

import java.util.NoSuchElementException;

import model.Show;
import util.StaticPrinter;

public class ViewShow extends Screen {

    private Show show;

    public ViewShow (String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }

    public void displayScreen() throws NoSuchElementException {

        show = new Show(user.getSearchResultSet());
        HashMap<String, String> showDetails = show.getShowDetails();


        String showName = showDetails.get("ShowName");
        String typeOfShow = showDetails.get("TypeName");
        String language = showDetails.get("LangugeName");
        String liveMusic = showDetails.get("Performaner");
        String duration = showDetails.get("Duration");
        String ticketPrice = showDetails.get("PriceID");
        String showDescription = showDetails.get("ShowDescription");
        

        StaticPrinter.printShowHeading(showName);
        StaticPrinter.printShowDetails(showName, typeOfShow, language, liveMusic, duration, showDescription, ticketPrice);
        //System.out.println(show.getShowDetails());
        System.out.println("");
        displayBasketStatus();
        System.out.println("Available commands:");
        System.out.println("v - view performance dates");
        System.out.println("b - back to search results");
        System.out.println("h - return to home screen");
        
        
        Boolean viewingShow = true;

        String nextScreen = "home-screen";

        while(viewingShow){

            try {

                String userInput = parser.getInputForMenu();
                if (userInput.equals("v")){
                    viewingShow = false;

                    String stringTemplate = user.getSqlQueries().get("all-performances-for-single-show");
                    String searchString = stringTemplate.replace("show-id-from-java", user.getIDValueForNextSearch());
                    searchString += "ORDER BY ShowDateTime;";
                    SearchDB showPerformancesByDate = new SearchDB(searchString, db);

                    user.saveNewSearch("shows-performances-by-date", showPerformancesByDate);
                    user.setPreviousSearch("shows-performances-by-date");

                    rs = showPerformancesByDate.runSearch();

                    user.setSearchResultSet(rs);
                    nextScreen = "performances-screen";
                    //user.newScreenRequest("performances-screen");


                } else if (userInput.equals("h")) {
                    viewingShow = false;
                    //user.newScreenRequest("home-screen");
                }else if (userInput.equals("b")) {
                    viewingShow = false;
    
                    rs = user.getSearchHistory().get(user.getPreviousSearch()).runSearch();
                    user.setSearchResultSet(rs);
                    nextScreen = "shows-screen";
                   // user.newScreenRequest("shows-screen");
                    
                } else {
                    StaticPrinter.invalidCommand();
                }

            } catch (NoSuchElementException e){
                System.out.println("ERROR - end of test file.");
                viewingShow = false;
                noUserTextFileErrors = false;
            }
            

        }
        if(noUserTextFileErrors){
            user.newScreenRequest(nextScreen);
        }

    }

    
}
