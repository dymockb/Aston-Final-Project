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
            System.out.println("Please select a row number to book tickets.");
        } else {
            System.out.println("Please select a row number to view more details.");
        }
    }



    
}
