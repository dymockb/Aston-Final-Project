package screens;
import superclass.Screen;
import util.DBConnector;
import util.Parser;
import java.util.HashMap;

import java.util.NoSuchElementException;

import model.Performance;
import model.Booking;
import util.StaticPrinter;
import superclass.SearchDB;

public class ViewPerformance extends Screen {

    private Performance performance;

    public ViewPerformance (String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }

    public void displayScreen() throws NoSuchElementException {

        performance = new Performance(user.getSearchResultSet(), user);

        HashMap<String, String> performanceDetails = performance.getPerformanceDetails();
        String showName = performanceDetails.get("ShowName");
        String performanceTime = performanceDetails.get("ShowDateTime");
        String performanceTitle = showName + " " + performanceTime;
        String description = performanceDetails.get("ShowDescription");
        String language = performanceDetails.get("LangugeName");
        String liveMusic = performanceDetails.get("Performaner");
        int numberOfAvailableSeats = performance.getTotalNumberOfAvailableSeats();
        
        StaticPrinter.printPerformanceHeading(performanceTitle);
        StaticPrinter.printPerformanceDetails(description, liveMusic, language, numberOfAvailableSeats);
        //System.out.println(performance.getPerformanceDetails());

        System.out.println("");
        System.out.println("Available commands:");
        System.out.println("b - Book tickets for this performance");
        System.out.println("r - Return to list of performances for this show");
        System.out.println("h - Home Screen");

        displayBasketStatus();
        

        Boolean viewingPerformance = true;

        String nextScreen = "home-screen";

        while(viewingPerformance){

            try {

                String userInput = parser.getInputForMenu();
                if (userInput.equals("b")){
                    viewingPerformance = false;

                    Booking booking = new Booking(user, performance, parser);
                    
                    String seatingAreaSelected = booking.startBooking();
                    
                    if (seatingAreaSelected.equals("c") || seatingAreaSelected.equals("s")){

                        if(booking.getBasketUpdated()){
                        
                            booking.startCheckout();
                            booking.printSummary();
                                                  
                        } else {
                            
                            System.out.println("User added tickets but did not checkout");
                            //return to user to homescreen 
                            //nextScreen = "book-performance";           
                        }

                    } else if (seatingAreaSelected.equals("r")){
                        
                        nextScreen = "view-performance"; 

                    }
                    
                    



                } else if (userInput.equals("h")) {
                    viewingPerformance = false;
                    
                }else if (userInput.equals("r")) {

                    viewingPerformance = false;
    
                    SearchDB savedSearch = new SearchDB(user.getPreviousSearch(), db);
                        
                    //String stringTemplate = user.getSqlQueries().get("all-performances-for-single-show");
                    //String searchString = stringTemplate.replace("show-id-from-java", user.getIDValueForNextSearch());
                    //String searchString = stringTemplate.replace("show-id-from-java", show.getShowDetails().get("ID"));
                    //SearchDB showPerformancesByDate = new SearchDB(searchString, db);
                    //user.setPreviousSearch(searchString);

                    //user.saveNewSearch("shows-performances-by-date", showPerformancesByDate);
                    

                    rs = savedSearch.runSearch();

                    user.setSearchResultSet(rs);
                    nextScreen = "performances-screen";
                    
                } else {
                    StaticPrinter.invalidCommand();
                }

            } catch (NoSuchElementException e){
                System.out.println("ERROR - end of test file.");
                viewingPerformance = false;
                noUserTextFileErrors = false;
            }
            

        }

        if(noUserTextFileErrors){
            user.newScreenRequest(nextScreen);
        }

    }

}

