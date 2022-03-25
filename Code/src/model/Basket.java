package model;

import util.Parser;

public class Basket {

    private int numberOfTicketsInBasket = 0;
    private int numberOfShowsInBasket = 0;
    private Parser parser;
    private Ticket[] tickets;

    public Basket(Parser parser){
        this.parser = parser;
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

        System.out.println("Basket summary. there are " + numberOfTicketsInBasket + " in the basket");
        System.out.println("Total price: \u00A380" );
        System.out.println("Please enter your card details. validating payment..." );
        System.out.println("** add tickets to Database " );
        System.out.println("Payment confirmed. Clear basket" );
        numberOfTicketsInBasket = 0;
        System.out.println("Basket summary. there are " + numberOfTicketsInBasket + " in the basket");
        return true;

    }
    
}
