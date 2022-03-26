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

        System.out.println("b - Book tickets for this performance");
        displayBasketStatus();
        System.out.println("n - New search");
        System.out.println("h - return to home screen");
        

        Boolean viewingPerformance = true;

        String nextScreen = "home-screen";

        while(viewingPerformance){

            try {

                String userInput = parser.getInputForMenu();
                if (userInput.equals("b")){
                    viewingPerformance = false;

                    System.out.println("start booking process");
                    Booking booking = new Booking(user, performance, parser);
                    
                    String seatingAreaSelected = booking.startBooking();
                    if (seatingAreaSelected.equals("c") || seatingAreaSelected.equals("s")){

                        if(booking.getBasketUpdated()){
                        
                            booking.startCheckout();
                            booking.printSummary();
                                                  
                        } else {
                            
                            System.out.println("User did not added tickets to basket");
                            //return to user to homescreen 
                            nextScreen = "book-performance";           
                        }

                    } else if (seatingAreaSelected.equals("r")){
                        System.out.println("return to peformance tbc");
                    }
                    
                    



                } else if (userInput.equals("h")) {
                    viewingPerformance = false;
                    
                }else if (userInput.equals("n")) {
                    viewingPerformance = false;
    
                    //rs = user.getSearchHistory().get(user.getPreviousSearch()).runSearch();
                    //user.setSearchResultSet(rs);
                    //nextScreen = "shows-screen";
                    
                    nextScreen = "search-screen";
                    
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

