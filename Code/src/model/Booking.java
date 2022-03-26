package model;
import util.Parser;
import util.StaticPrinter;

import java.util.NoSuchElementException;

import util.DBConnector;


public class Booking {

    private Performance performance;
    private Parser parser;
    private User user;
    private String errorMessage;
    private Boolean bookingComplete;
    private Boolean basketUpdated;

    public Booking(User user, Performance performance, Parser parser){

        this.performance = performance;
        this.parser = parser;
        this.user = user;

        errorMessage = "No errors";
        bookingComplete = false;  
        basketUpdated = false;      

    }

    public Boolean getBasketUpdated(){
        return basketUpdated;
    }

    public String startBooking() throws NoSuchElementException {

        Boolean bookingInProgress = true;
        Boolean returnValue = false;

        //String nextScreen = "home-screen";
        System.out.println("run sql to get available seats");

        String seatingAreaChosen = null;

        while (bookingInProgress){

            int numberOfAvailableSeats = 10;
            StaticPrinter.printChooseSeatsHeading(numberOfAvailableSeats);

            System.out.println("Circle: x tickets available - \u00A350");
            System.out.println("Stalls: x tickets avaiable - \u00A340");
            System.out.println("");
            System.out.println("Available commands:");
            System.out.println("c - Circle - book seats");
            System.out.println("s - Stalls - book seats");
            System.out.println("r - return to performance details");

            try{

            seatingAreaChosen = parser.getInputForMenu();

            if(seatingAreaChosen.equals("c")){

                Boolean selectingSeats = true;
                while(selectingSeats){
                    
                    System.out.println("Circle seats available:");
                    System.out.println("14, 15, 16, 17, 22, 87, 88");
                    System.out.println("");
                    System.out.println("Available commands:");
                    System.out.println("r - return to previous screen");
                    System.out.println("Please type the seat numbers for your booking (eg: 14,15,16,18):");  
                    String userInput = parser.getInputForMenu(); 

                    if (userInput.equals("r")){
                        selectingSeats = false;
                        //bookingInProgress = false;
                        
                    } else {

                    //String seatsArray = convertInputToSeatsArray(userInput);
                    String seatsArray = "placeholder";

                        Boolean seatsAvailable = checkSeatAvailability(seatsArray);
                        if (seatsAvailable){
                            
                            System.out.println("Your selected seats are available.");
                            System.out.println("Total price: \u00A380");
                            System.out.println("Add these tickets to your basket? y / n");
                            userInput = parser.getInputForMenu();

                            if (userInput.equals("y")){
                                
                                System.out.println("Add tickets to basket");
                                basketUpdated = user.getBasket().addTickets(seatsArray);
                                selectingSeats = false;
                                bookingInProgress = false;

                            } else if (userInput.equals("n")){
                                selectingSeats = false;
                                bookingInProgress = false;

                            } else {
                                StaticPrinter.invalidCommand();
                            }

        
                        } else {
                            System.out.println("Sorry one or more of the seat(s) you selected are not available.");                     
                        }

                    }


                }



            } else if (seatingAreaChosen.equals("s")){

                System.out.println("Stalls seats available:");
                System.out.println("14, 15, 16, 17, 22, 87, 88");
                System.out.println("Please type the seat numbers for your booking (eg: 14,15,16,18):");  
                String userInput = parser.getInputForMenu(); 

            } else if (seatingAreaChosen.equals("r")){
                bookingInProgress = false;
            }

        } catch (NoSuchElementException e){
            bookingInProgress = false;
        }



        }

        return seatingAreaChosen;

    }

    public void startCheckout(){

        Boolean success = user.getBasket().startCheckout();
        if (success){
            
            //returnValue = true;
        } else {
            System.out.println("Sorry there was an error - your booking was not completed.");
            
        }


    }

    public void printSummary(){
        System.out.println("here is a summary of your booking - END OF PURCHASE CYCLE");
    }

    public Boolean checkSeatAvailability(String userInput){

        System.out.println("checking availability");
        return true;
    }
    
}
