package screens;
import superclass.Screen;
import util.DBConnector;
import util.Parser;
import superclass.SearchDB;

import java.util.NoSuchElementException;

import model.Performance;
import model.Booking;
import util.StaticPrinter;

public class BookPerformance extends Screen {

    private Performance performance;

    public BookPerformance (String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }

    public void displayScreen() throws NoSuchElementException {

        Performance performance = new Performance(user.getSearchResultSet());

        System.out.println(performance.getPerformanceDetails());

        System.out.println("t - book tickets for this performance");
        System.out.println("b - back to search results");
        System.out.println("h - return to home screen");
        displayBasketStatus();

        Boolean viewingPerformance = true;

        String nextScreen = "home-screen";

        while(viewingPerformance){

            try {

                String userInput = parser.getInputForMenu();
                if (userInput.equals("t")){
                    viewingPerformance = false;

                    System.out.println("start booking process");
                    Booking booking = new Booking(user, performance, parser);
                    
                    Boolean bookingComplete = booking.startBooking();
                    if(bookingComplete){
                        
                        System.out.println("thanks for your booking.");
                        booking.printSummary();
                        //nextScreen = "home-screen";
                        
                    } else {
                        
                        System.out.println("Your booking was not completed.  Please start a new search.");
                        //nextScreen = "home-screen";
                        
                    }
                    



                } else if (userInput.equals("h")) {
                    viewingPerformance = false;
                    //user.newScreenRequest("home-screen");
                }else if (userInput.equals("b")) {
                    viewingPerformance = false;
    
                    rs = user.getSearchHistory().get(user.getPreviousSearch()).runSearch();
                    user.setSearchResultSet(rs);
                    nextScreen = "shows-screen";
                   // user.newScreenRequest("shows-screen");
                    
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

