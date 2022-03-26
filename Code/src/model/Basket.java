package model;

import util.Parser;
import util.StaticPrinter;

import java.util.ArrayList;
import java.util.HashMap;

public class Basket {

    private int numberOfTicketsInBasket = 0;
    private int numberOfShowsInBasket = 0;
    private Parser parser;
    private ArrayList<Ticket> tickets;
    private HashMap<String, String> sqlQueries;

    public Basket(Parser parser, HashMap<String, String> sqlQueries){
        this.parser = parser;
        this.sqlQueries = sqlQueries;
        tickets = new ArrayList<Ticket>();
    }

    public int getNumberOfTicketsInBasket(){
        return numberOfTicketsInBasket;
    }

    public int getNumberOfShowsInBasket(){
        return numberOfShowsInBasket;
    }

    public int displayBasket(){
        StaticPrinter.printBasketHeading(numberOfTicketsInBasket);
        System.out.println("There are currently " + numberOfTicketsInBasket + " tickets in your basket");
        return numberOfTicketsInBasket;
    }

    public Boolean addTickets(String placeholderShouldbeArray){

        numberOfTicketsInBasket++;

        StaticPrinter.printBasketHeading(numberOfTicketsInBasket);
        System.out.println("There are " + numberOfTicketsInBasket + " ticket(s) in your basket.");
        System.out.println("The total cost of your basket is £80"); 
        System.out.println("Would you like to checkout now?");
        System.out.println("y - checkout.");
        System.out.println("n - save basket and search again.");

        String userInput = parser.getInputForMenu();
        if(userInput.equals("y")){
            return true;
        } else if (userInput.equals("n")){
            return false;
        } else {
            System.out.println("basket.addTickets() method error -  invalid command");
            return false;
        }

    }

    public Boolean startCheckout(){

        Boolean returnValue = false;

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
