package model;

import util.Parser;
import util.StaticPrinter;

import java.util.ArrayList;
import java.util.HashMap;
import util.DBConnector;
import java.util.NoSuchElementException;

public class Basket {

    //private int numberOfTicketsInBasket = 0;
    //private double basketTotal = 0;
    private Parser parser;
    private DBConnector db;
    private ArrayList<Ticket> tickets;
    private HashMap<String, String> sqlQueries;

    public Basket(Parser parser, HashMap<String, String> sqlQueries, DBConnector db){
        this.parser = parser;
        this.sqlQueries = sqlQueries;
        this.db = db;
        tickets = new ArrayList<Ticket>();
    }

    public int getNumberOfTicketsInBasket(){
        return tickets.size();
    }

    public double getBasketTotal(){
        double total = 0;
        for (Ticket ticket : tickets){
            total += ticket.getPrice();
        }
        return total;
    }

    public ArrayList<Ticket> getTickets(){
        return tickets;
    }

    public int displayBasket(){
        StaticPrinter.printBasketHeading(tickets.size());
        return tickets.size();
    }

    public String addTickets(ArrayList<Ticket> tickets){

        //numberOfTicketsInBasket += tickets.size();
        for (Ticket ticket : tickets){
            //basketTotal += ticket.getPrice();
            this.tickets.add(ticket);
        }

        StaticPrinter.printBasketHeading(this.tickets.size());
        System.out.println("There are " + this.tickets.size() + " ticket(s) in your basket.");
        System.out.println("The basket total is " + getBasketTotal()); 
        System.out.println("Would you like to checkout now?");
        System.out.println("y - checkout.");
        System.out.println("n - save basket and search again.");

        String userInput = null;

        try {
            userInput = parser.getInputForMenu();
        } catch (NoSuchElementException e){
            System.out.println("End of test file.");
            
        }

        if(userInput != null){
            if(userInput.equals("y")){
                String checkoutResult = startCheckout();
                if (checkoutResult.equals("purchase-complete")){
                    return "purchase-complete";
                } else if (checkoutResult.equals("file-input-error")){
                    return null;
                } else if (checkoutResult.equals("purchase-cancelled-by-user")){
                    return "purchase-cancelled-by-user";
                } 

                else {
                    return null;
                }
            } else if (userInput.equals("n")){
                return "basket-saved";
            } else {
                System.out.println("basket.addTickets() method error -  invalid command");
                return "invalid-command";
            }
        } else {
            return null;
        }



    }

    public String startCheckout(){

        String returnValue = null;

        Boolean gettingCardDetails = true;

        String userInput = null;
        while(gettingCardDetails){
            System.out.println("Please enter your 16-digit card number:" );
            System.out.println("c - cancel and exit booking." );
    
            try {
                userInput = parser.getInputForMenu();
            } catch (NoSuchElementException e){
                System.out.println("ERROR - end of test file.");
                
            }

            if (userInput != null){
                if(userInput.equals("c")){
                    gettingCardDetails = false;
                    return "purchase-cancelled-by-user";
                } else if(validateCardDetails(userInput)){
                    gettingCardDetails = false;
                } else {
                    System.out.println("Card payment error. Please try again.");
                }
            } else {
                gettingCardDetails = false;
                System.out.println("No input");
            }
        }

        System.out.println("Your card details are confirmed.  Proceed with purchase?");
        System.out.println("y - purchase tickets");
        System.out.println("n - cancel purchase");

        userInput = null;

        try {
            userInput = parser.getInputForMenu();
        } catch (NoSuchElementException e){
            System.out.println("ERROR - end of test file.");
            
        }

        if (userInput != null){

            if (userInput.equals("y")){

                for (Ticket ticket : tickets){

                    String addTicketString = createTicketString(
                        ticket.getCustomerID(),
                        ticket.getPerformanceID(),
                        ticket.getSeatID(),
                        ticket.getPrice()
                        );

                    

                    try {
                        
                        db.runQuery(addTicketString);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    

                }

                System.out.println("Payment confirmed." );
                System.out.println("Here is a summary of your purchase:" );

                double totalPurchase = 0;
                int ticketNumber = 1;
                for(Ticket ticket: tickets){
                    System.out.println("Ticket number " + ticketNumber + ": " + ticket.getShowName());
                    System.out.println("   Date: " + ticket.getPerformanceDate());
                    System.out.println("   Time: " + ticket.getPerformanceTime());
                    String seatingArea = ticket.getSeatID() <= 120 ? "Stalls" : "Circle";
                    System.out.println("   Seating Area: " + seatingArea);
                    System.out.println("   Seat Number:" + ticket.getSeatID());
                    System.out.println("   Price: " + ticket.getPrice());
                    totalPurchase += ticket.getPrice();
                    ticketNumber++;
                }

                System.out.println("Total purchase: " + totalPurchase);


            
                tickets = new ArrayList<Ticket>();
                returnValue = "purchase-complete";

            } else if (userInput.equals("n")){
                returnValue = "purchase-cancelled-by-user";
            }

        } else {
            returnValue = "file-input-error";
        }

        return returnValue;

    }


    private Boolean validateCardDetails(String userInput){

        System.out.println("Validating payment...");
        if(userInput.length() == 16){
            return true;
        } else {
            return false;
        }




    }

    private String createTicketString(int customerID, String performanceID, int seatID, double price){

        String ticketString = "INSERT INTO Reservation (CustomerID, PerformanceID, SeatID, PaymentTypeID, DeliveryTypeID, Price, ReservationDateTime, CancellationDateTime) VALUES (";

        ticketString += customerID + ", ";
        ticketString += performanceID + ", ";
        ticketString += seatID + ", ";
        ticketString += "1, ";
        ticketString += "1, ";
        ticketString += price + ", ";
        //the actual time of the booking is not captured
        ticketString += "'2022-03-27 10:50:00'" + ", ";
        ticketString += "0);";

        return ticketString;
    
    }


    
}
