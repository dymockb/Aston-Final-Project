package model;
import util.Parser;


public class Booking {

    private Performance performance;
    private Parser parser;

    public Booking(Performance performance, Parser parser){

        this.performance = performance;
        this.parser = parser;

    }

    public Boolean startBooking(){

        Boolean bookingInProgress = true;
        Boolean returnValue = false;

        while (bookingInProgress){
            System.out.println("starting booking...");
            String userInput = parser.getInputForMenu();
            
            if (userInput.equals("complete")){
                bookingInProgress = false;
                returnValue = true;
            } else {
                bookingInProgress = false;
                returnValue = false;
            }
        }

        return returnValue;

    }

    public void printSummary(){
        System.out.println("here is a summary of your booking");
    }
    
}
