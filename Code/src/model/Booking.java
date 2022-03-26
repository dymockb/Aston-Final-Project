package model;
import util.Parser;
import util.StaticPrinter;

import java.util.NoSuchElementException;

import util.DBConnector;


public class Booking {

    private Performance performance;
    private Parser parser;
    private User user;

    public Booking(User user, Performance performance, Parser parser){

        this.performance = performance;
        this.parser = parser;
        this.user = user;

    }

    public Boolean startBooking() throws NoSuchElementException {

        Boolean bookingInProgress = true;
        Boolean returnValue = false;

        //String nextScreen = "home-screen";

        while (bookingInProgress){
            System.out.println("starting booking...");

            System.out.println("Circle: x tickets available - \u00A350");
            System.out.println("Stalls: x tickets avaiable - \u00A340");

            System.out.println("Available commands:");
            System.out.println("c - book seats in circle");
            System.out.println("s - book seats in stalls");

            try{

            String userInput = parser.getInputForMenu();

            if(userInput.equals("c")){

                Boolean selectingSeats = true;
                while(selectingSeats){
                    
                    System.out.println("Circle seats available:");
                    System.out.println("14, 15, 16, 17, 22, 87, 88");
                    System.out.println("Please type the seat numbers for your booking (eg: 14,15,16,18):");  
                    userInput = parser.getInputForMenu(); 

                    //String seatsArray = convertInputToSeatsArray(userInput);
                    String seatsArray = "placeholder";

                    Boolean seatsAvailable = checkSeatAvailability(seatsArray);
                    if (seatsAvailable){
                        
                        System.out.println("Your selected seats are available - the total cost is \u00A380.");
                        System.out.println("Add these tickets to your basket? y / n");
                        userInput = parser.getInputForMenu();

                        if (userInput.equals("y")){
                            
                            System.out.println("Add tickets to basket");
                            Boolean startCheckout = user.getBasket().addTickets(seatsArray);
                            
                            if(startCheckout){
                                Boolean success = user.getBasket().startCheckout();
                                if (success){
                                    selectingSeats = false;
                                    bookingInProgress = false;
                                    returnValue = true;
                                } else {
                                    System.out.println("Sorry there was an error - your booking was not completed.");
                                    System.out.println("n - new search.");
                                    System.out.println("b - view basket.");
                                    System.out.println("h - return to home screen.");
                                    userInput = parser.getInputForMenu();
                                    if (userInput.equals("h")){
                                        selectingSeats = false;
                                        bookingInProgress = false;
                                    }
                                }
                            } else {
                                System.out.println("your basket has been updated.");
                                System.out.println("n - new search.");
                                System.out.println("b - view basket.");
                                System.out.println("h - return to home screen.");
                                userInput = parser.getInputForMenu();
                                if (userInput.equals("h")){
                                    selectingSeats = false;
                                    bookingInProgress = false;
                                }
                            }

                        } else if (userInput.equals("n")){
                            System.out.println("your basket has not been updated.");
                            System.out.println("n - new search.");
                            System.out.println("b - view basket.");
                            System.out.println("h - return to home screen.");
                            userInput = parser.getInputForMenu();
                            if (userInput.equals("h")){
                                selectingSeats = false;
                                bookingInProgress = false;
                            }

                        } else {
                            StaticPrinter.invalidCommand();
                        }

    
                    } else {
                        System.out.println("Sorry one or more of the seat(s) you selected are not available.");                     
                    }

                }

            } else if (userInput.equals("s")){

                System.out.println("Stalls seats available:");
                System.out.println("14, 15, 16, 17, 22, 87, 88");
                System.out.println("Please type the seat numbers for your booking (eg: 14,15,16,18):");  
                userInput = parser.getInputForMenu(); 

            }

        } catch (NoSuchElementException e){
            bookingInProgress = false;
        }



        }

        return returnValue;

    }

    public void printSummary(){
        System.out.println("here is a summary of your booking - END OF PURCHASE CYCLE");
    }

    public Boolean checkSeatAvailability(String userInput){

        System.out.println("checking availability");
        return true;
    }
    
}
