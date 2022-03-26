package model;

import util.Parser;
import java.util.ArrayList;

public class Basket {

    private int numberOfTicketsInBasket = 0;
    private int numberOfShowsInBasket = 0;
    private Parser parser;
    private ArrayList<Ticket> tickets;

    public Basket(Parser parser){
        this.parser = parser;
        tickets = new ArrayList<Ticket>();
    }

    public int getNumberOfTicketsInBasket(){
        return numberOfTicketsInBasket;
    }

    public int getNumberOfShowsInBasket(){
        return numberOfShowsInBasket;
    }

    public Boolean addTickets(String placeholderShouldbeArray){

        numberOfTicketsInBasket++;

        System.out.println("msg from basket. there are " + numberOfTicketsInBasket + " in the basket Would you like to checkout now?");
        System.out.println("y - checkout.");
        System.out.println("n - save basket and search again.");

        String userInput = parser.getInputForMenu();
        if(userInput.equals("y")){
            return true;
        } else if (userInput.equals("n")){
            return false;
        } else {
            System.out.println("addtickets method error in basket invalid command");
            return false;
        }

    }

    public Boolean startCheckout(){

        Boolean returnValue = false;

        System.out.println("Basket summary. there are " + numberOfTicketsInBasket + " in the basket");
        System.out.println("Total price: \u00A380" );
        System.out.println("Please enter your card details." );
        String userInput = parser.getInputForMenu();
        if(validateCardDetails(userInput)){
            System.out.println("Your card details are confirmed.  Proceed with purchase?");
            System.out.println("y - purchase tickets");
            System.out.println("n - cancel purchase");
            userInput = parser.getInputForMenu();
            if (userInput.equals("y")){
                storeTicketPurchaseDetails(tickets);
                System.out.println("Payment confirmed. Clear basket" );
                numberOfTicketsInBasket = 0;
                System.out.println("Basket summary. there are " + numberOfTicketsInBasket + " in the basket");
                returnValue = true;
            } else if (userInput.equals("n")){
                System.out.println("Your purchase has been cancelled");
                returnValue = false;
            }

        } else {
            System.out.println("Card payment error.");
            returnValue = false;
        }

        return returnValue;

    }


    private Boolean validateCardDetails(String userInput){

        System.out.println("Validating payment...");
        return true;

    }

    private void storeTicketPurchaseDetails(ArrayList<Ticket> tickets){
        System.out.println("Adding tickets to database");
    }
    
}
