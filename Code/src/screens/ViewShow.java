package screens;
import superclass.Screen;
import util.DBConnector;
import util.Parser;
import superclass.SearchDB;
import java.util.HashMap;
import java.sql.*;

import java.util.NoSuchElementException;

import model.Show;
import util.StaticPrinter;

public class ViewShow extends Screen {

    private Show show;

    public ViewShow (String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }

    private double getMaxPrice(ResultSet rs, double startingPrice){

        double outputPrice = startingPrice;
        try {
            rs.beforeFirst();
            rs.next();
            for (int i = 1; i <= 4; i ++){
                if(rs.getInt(i) > outputPrice){
                    outputPrice = rs.getInt(i);
                }
            }

        } catch (SQLException e) {
			e.printStackTrace();
		}

        return outputPrice;

    };

    private double getMinPrice(ResultSet rs, double startingPrice){

        double outputPrice = startingPrice;
        try {
            rs.beforeFirst();
            rs.next();
            for (int i = 1; i <= 4; i ++){
                if(rs.getInt(i) < outputPrice){
                    outputPrice = rs.getInt(i);
                }
            }

        } catch (SQLException e) {
			e.printStackTrace();
		}

        return outputPrice;

    };


    public void displayScreen() throws NoSuchElementException {

        show = new Show(user.getSearchResultSet());
        HashMap<String, String> showDetails = show.getShowDetails();

        //String showID = showDetails.get("ID");
        String showName = showDetails.get("ShowName");
        String typeOfShow = showDetails.get("TypeName");
        String language = showDetails.get("LangugeName");
        String liveMusic = showDetails.get("Performaner");
        String duration = showDetails.get("Duration");
        String priceBandID = showDetails.get("PriceID");
        String showDescription = showDetails.get("ShowDescription");

        String searchString = user.getSqlQueries().get("get-all-prices-for-show");  
        searchString = searchString.replace("price-band-id-from-java", priceBandID + ";");
        
        System.out.println(searchString);
        SearchDB getPrices = new SearchDB(searchString, db);
        rs = getPrices.runSearch();

        double maxPrice = getMaxPrice(rs, 0);
        double minPrice = getMinPrice(rs, maxPrice);

        String ticketPrices = "GBP " + minPrice + " - GBP " + maxPrice ;
 
        StaticPrinter.printShowHeading(showName);
        StaticPrinter.printShowDetails(showName, typeOfShow, language, liveMusic, duration, showDescription, ticketPrices);
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
                    //String searchString = stringTemplate.replace("show-id-from-java", user.getIDValueForNextSearch());
                    searchString = stringTemplate.replace("show-id-from-java", show.getShowDetails().get("ID"));
                    searchString += "ORDER BY ShowDate, ShowTime;";
                    SearchDB showPerformancesByDate = new SearchDB(searchString, db);
                    user.setPreviousSearch(searchString);
                    user.setShowTicketPriceRange(ticketPrices);
                    //user.saveNewSearch("shows-performances-by-date", showPerformancesByDate);
                    

                    rs = showPerformancesByDate.runSearch();

                    user.setSearchResultSet(rs);
                    nextScreen = "performances-screen";
                    


                } else if (userInput.equals("h")) {
                    viewingShow = false;
                    
                }else if (userInput.equals("b")) {
                    viewingShow = false;
    
                    SearchDB search = new SearchDB(user.getPreviousSearch(), user.getDBConnector());
                    rs = search.runSearch();
                    user.setSearchResultSet(rs);
                    nextScreen = "shows-screen";
                    
                } else {
                    StaticPrinter.invalidCommand("ViewShow");
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
