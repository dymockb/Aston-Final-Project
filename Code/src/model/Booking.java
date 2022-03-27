package model;
import util.Parser;
import util.StaticPrinter;

import java.util.NoSuchElementException;
import java.util.ArrayList;

import util.DBConnector;
import superclass.SearchDB;
import java.sql.*;

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

        ArrayList<Integer> availableStallsSeats = performance.getAvailableStallsSeats();
        ArrayList<Integer> availableCircleSeats = performance.getAvailableCircleSeats();
        int totalSeatsAvailable = availableStallsSeats.size() + availableCircleSeats.size();

        String seatingAreaChosen = null;

        while (bookingInProgress){

            StaticPrinter.printChooseSeatsHeading(totalSeatsAvailable);

            System.out.println("Circle: " + availableCircleSeats.size() + " tickets available.");
            System.out.println("Stalls: " + availableStallsSeats.size() + " tickets avaiable.");
            System.out.println("");
            System.out.println("Available commands:");
            System.out.println("c - Circle - book seats");
            System.out.println("s - Stalls - book seats");
            System.out.println("r - return to performance details");

            try{

            seatingAreaChosen = parser.getInputForMenu();

            if(seatingAreaChosen.equals("c")){

                if (availableCircleSeats.size() > 0){         

                    Boolean selectingSeats = true;
                    while(selectingSeats){
                        
                        System.out.println("Circle seats available:");
                        for(int seat : availableCircleSeats){
                            System.out.print(seat + " ");
                        }
                        System.out.println("");
                        System.out.println("Available commands:");
                        System.out.println("r - return to previous screen");
                        System.out.println("Please type the seat numbers for your booking (eg: 14,15,16,18):");  
                        String userInput = parser.getInputForMenu(); 

                        if (userInput.equals("r")){
                            selectingSeats = false;
                            
                        } else {

                            ArrayList<String> seatsArray = convertInputToSeatsArray(userInput);

                            Boolean allSeatsAvailable = checkSeatAvailability(seatsArray);
                            if (allSeatsAvailable){
                                
                                System.out.println("Your selected seats are available. Selected seats:");
                               
                                int totalPrice = 0;

                                ArrayList<Ticket> tickets = new ArrayList<Ticket>();
                                for(String seat : seatsArray){
                                    
                                    int price = getSeatPrice(seat);
                                    System.out.println("Seat " + seat + ", price: " + price);
                                    totalPrice += price;
                                    Ticket ticket = new Ticket(price, Integer.valueOf(seat));
                                    tickets.add(ticket);
                                }
                                System.out.println("Total price: " + totalPrice);

                                System.out.println("Add these tickets to your basket? y / n");
                                userInput = parser.getInputForMenu();

                                if (userInput.equals("y")){
                                    
                                    System.out.println("Add tickets to basket");
                                    basketUpdated = user.getBasket().addTickets(tickets);
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


            } else {
                System.out.println("There are no circle seats available.");
            }


            } else if (seatingAreaChosen.equals("s")){

                System.out.println("Stalls seats available:");
                System.out.println("14, 15, 16, 17, 22, 87, 88");
                System.out.println("Please type the seat numbers for your booking (eg: 14,15,16,18):");  
                //String userInput = parser.getInputForMenu(); 

            } else if (seatingAreaChosen.equals("r")){
                seatingAreaChosen = "r";
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

    public ArrayList<String> convertInputToSeatsArray(String userInput){

        //process input to get a list of individual seat numbers.
        // then add each seat number to array list:

            ArrayList<String> selectedSeats = new ArrayList<String>();
            selectedSeats.add(userInput);
            if (selectedSeats.size() > 0){
                return selectedSeats;
            } else {
                return null;
            }

    }

    public Boolean checkSeatAvailability(ArrayList<String> selectedSeats){

        String performanceID = performance.getPerformanceDetails().get("ID");

        Boolean allSeatsAreAvailable = true;

        for (String seat : selectedSeats){

            String stringTemplate = user.getSqlQueries().get("check-seat-available");
            stringTemplate = stringTemplate.replace("performance-id-from-java", performanceID);
            stringTemplate = stringTemplate.replace("seat-id-from-java", seat);
            stringTemplate += ";";
                
            SearchDB bookingsForThisSeatThisPeformance = new SearchDB(stringTemplate, user.getDBConnector());
    
            ResultSet rs = bookingsForThisSeatThisPeformance.runSearch();
    
            try {
                rs.beforeFirst();
                rs.next();
                int countBookedSeats = rs.getInt(1);
                if (!(countBookedSeats == 0)){
                    allSeatsAreAvailable = false;
                }    
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return allSeatsAreAvailable;
        
    }

    public void createTickets(ArrayList<String> seatsArray){

        for(String seat : seatsArray){
            getSeatPrice(seat);
        }

    }

    public int getSeatPrice(String seat){

        int returnValue = 0;

        String performanceID = performance.getPerformanceDetails().get("ID");
        String performanceTime = performance.getPerformanceDetails().get("ShowDateTime");
        System.out.println("Performance time: Matinee (but db says this:) " + performanceTime);
        performanceTime = "Matinee";
        String seatArea = Integer.valueOf(seat) <= 120 ? "Stalls" : "Circle";

        String stringTemplate = user.getSqlQueries().get("get-seat-price");
        stringTemplate = stringTemplate.replace("performance-time-from-java", performanceTime);
        stringTemplate = stringTemplate.replace("seat-area-from-java", seatArea);
        stringTemplate = stringTemplate.replace("performance-id-from-java", performanceID);
        stringTemplate += ";";

        SearchDB priceOfThisSeat = new SearchDB(stringTemplate, user.getDBConnector());

        ResultSet rs = priceOfThisSeat.runSearch();

        try {
            rs.beforeFirst();
            rs.next();
            int seatPrice = rs.getInt(1);
            if (seatPrice != 0){
                returnValue = seatPrice;
            }    
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnValue;

    }
    
}
