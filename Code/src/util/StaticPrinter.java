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


    
}
