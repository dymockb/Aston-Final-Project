package util;

public class StaticPrinter {

    public StaticPrinter(){

    }

    public static void invalidCommand(){
        System.out.println("** Invalid command **");
    }

    public static void printLoginLogout(Boolean userLoggedIn){
        if(userLoggedIn){
            System.out.println("l - logout");
        } else {
            System.out.println("l - login as Admin User");
        }
        
    }

    public static void printTableRowSelectionMsg(Boolean isBookingTable){
        if (isBookingTable){
            System.out.println("Please enter a row number to book tickets.");
        } else {
            System.out.println("Please enter a row number to view more details.");
        }
    }

    public static void printHomeScreenHeading(){

        System.out.println(String.format("%-80s", "------------------------------------------------------------------------------------------"));
        System.out.println(String.format("%-80s", "Welcome to the Theatre Royal"));
        System.out.println(String.format("%-80s", "------------------------------------------------------------------------------------------"));

    }

    public static void printSearchScreenHeader(){

        System.out.println(String.format("%-80s", "------------------------------------------------------------------------------------------"));
        System.out.println(String.format("%-80s", "Search for a show"));
        System.out.println(String.format("%-80s", "------------------------------------------------------------------------------------------"));

    }

    public static void printBasketHeading(int numberOfTicketsInBasket){

        System.out.println(String.format("%-80s", "------------------------------------------------------------------------------------------"));
        System.out.println(String.format("%-80s", "Your basket - " + numberOfTicketsInBasket + " ticket(s)."));
        System.out.println(String.format("%-80s", "------------------------------------------------------------------------------------------"));

    }

    public static void printBasketFooter(int numberOfTicketsInBasket, double basketTotal){

        System.out.println(String.format("%-80s", "------------------------------------------------------------------------------------------"));
        System.out.println(String.format("%-80s", "Basket: " + numberOfTicketsInBasket + " ticket(s). Total cost: £" + basketTotal +  "."));
        //System.out.println(String.format("%-80s", "g - go to basket"));        
        System.out.println(String.format("%-80s", "------------------------------------------------------------------------------------------"));

    }

    public static void printTableHeading(String headingText){

        System.out.println(String.format("%-80s", "------------------------------------------------------------------------------------------"));
        System.out.println(String.format("%-80s", headingText));
        //System.out.println(String.format("%-80s", "------------------------------------------------------------------------------------------"));

    }

    public static void printShowHeading(String headingText){

        System.out.println(String.format("%-80s", "------------------------------------------------------------------------------------------"));
        System.out.println(String.format("%-80s", headingText));
        System.out.println(String.format("%-80s", "------------------------------------------------------------------------------------------"));

    }

    public static void printShowDetails(String showName, String typeOfShow, String language, String liveMusic, String duration, String showDescription, String ticketPrice){

        System.out.println(String.format("%-80s", showName + " - " + typeOfShow + " (" + language + ")"));
        System.out.println(String.format("%-80s", "Live music: " + liveMusic));
        System.out.println(String.format("%-80s", "Show duration: " + duration));
        System.out.println(String.format("%-80s", "Ticket price: " + ticketPrice));
        System.out.println(String.format("%-80s", ""));
        System.out.println(String.format("%-80s", showDescription));
    }


    public static void printPerformanceHeading(String headingText){

        System.out.println(String.format("%-80s", "------------------------------------------------------------------------------------------"));
        System.out.println(String.format("%-80s", headingText));
        System.out.println(String.format("%-80s", "------------------------------------------------------------------------------------------"));

    }


    public static void printPerformanceDetails(String description, String liveMusic, String language, int numberOfAvailableSeats){

        System.out.println(String.format("%-80s", description));
        System.out.println(String.format("%-80s", "(" + language +  ")"));
        System.out.println(String.format("%-80s", "Live music: " + liveMusic));
        System.out.println(String.format("%-80s", "Number of tickets available: " + numberOfAvailableSeats));
    }

    public static void printChooseSeatsHeading(int numberOfAvailableSeats){

        System.out.println(String.format("%-80s", "------------------------------------------------------------------------------------------"));
        System.out.println(String.format("%-80s", "This performance has " + numberOfAvailableSeats +  " seat(s) available."));
        System.out.println(String.format("%-80s", "------------------------------------------------------------------------------------------"));

    }
   
}
