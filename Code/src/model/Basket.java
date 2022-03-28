package model;

import util.Parser;
import util.StaticPrinter;

import java.util.ArrayList;
import java.util.HashMap;
import util.DBConnector;

public class Basket {

    //private int numberOfTicketsInBasket = 0;
    private double basketTotal = 0;
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
        return basketTotal;
    }

    public int displayBasket(){
        StaticPrinter.printBasketHeading(tickets.size());
        return tickets.size();
    }

    public String addTickets(ArrayList<Ticket> tickets){

        //numberOfTicketsInBasket += tickets.size();
        for (Ticket ticket : tickets){
            basketTotal += ticket.getPrice();
            this.tickets.add(ticket);
        }

        StaticPrinter.printBasketHeading(tickets.size());
        System.out.println("There are " + tickets.size() + " ticket(s) in your basket.");
        System.out.println("The baske total is " + basketTotal); 
        System.out.println("Would you like to checkout now?");
        System.out.println("y - checkout.");
        System.out.println("n - save basket and search again.");

        String userInput = parser.getInputForMenu();
        if(userInput.equals("y")){
            System.out.println("1. ticket array size: " + tickets.size());
            startCheckout();
            return "complete";
        } else if (userInput.equals("n")){
            return "basket-saved";
        } else {
            System.out.println("basket.addTickets() method error -  invalid command");
            return "invalid command";
        }

    }

    public Boolean startCheckout(){
        System.out.println("1.5. ticket array size: " + tickets.size());
        Boolean returnValue = false;

        System.out.println("Please enter your card details." );
        String userInput = parser.getInputForMenu();
        if(validateCardDetails(userInput)){
            System.out.println("Your card details are confirmed.  Proceed with purchase?");
            System.out.println("y - purchase tickets");
            System.out.println("n - cancel purchase");
            System.out.println("2. ticket array size: " + tickets.size());
            userInput = parser.getInputForMenu();
            if (userInput.equals("y")){

                System.out.println("3. ticket array size: " + tickets.size());
                for (Ticket ticket : tickets){

                    System.out.println("make a ticket string");
                    String addTicketString = createTicketString(
                        ticket.getCustomerID(),
                        ticket.getPerformanceID(),
                        ticket.getSeatID(),
                        ticket.getPrice()
                        );

                    System.out.println(addTicketString);

                    try {

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    //db.runQuery(addTicketString);

                }

                // LOOP through tickets array and update Reservations table.
                /** 
                 INSERT INTO Reservation (CustomerID, PerformanceID, SeatID, PaymentTypeID, DeliveryTypeID, Price, ReservationDateTime, CancellationDateTime) 
VALUES (1, 2, 121, 1, 1, 40, "2022-03-27 10:50:00", 0)

                /** */





                System.out.println("Payment confirmed. Clear basket" );
                tickets = new ArrayList<Ticket>();
                basketTotal = 0;
                //System.out.println("Basket summary. there are " + tickets.size() + " tickets in the basket");
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

    private String createTicketString(int customerID, int performanceID, int seatID, double price){

        /**
         *  INSERT INTO Reservation (CustomerID, PerformanceID, SeatID, PaymentTypeID, DeliveryTypeID, Price, ReservationDateTime, CancellationDateTime) 
VALUES (1, 2, 121, 1, 1, 40, "2022-03-27 10:50:00", 0)
         */

        String ticketString = "INSERT INTO Reservation (CustomerID, PerformanceID, SeatID, PaymentTypeID, DeliveryTypeID, Price, ReservationDateTime, CancellationDateTime) VALUES (";

        ticketString += customerID + ", ";
        ticketString += performanceID + ", ";
        ticketString += seatID + ", ";
        ticketString += "1, ";
        ticketString += "1, ";
        ticketString += price + ", ";
        ticketString += "2022-03-27 10:50:00" + ", ";
        ticketString += "0);";

        return ticketString;
    
    }


    
}
