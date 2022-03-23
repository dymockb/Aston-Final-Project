package util;

public class StaticPrinter {

    public StaticPrinter(){

    }

    public static void printStandardOptions(Boolean loggedIn, Boolean homescreen){

        if(loggedIn){
                //System.out.println("                    login as Admin user - l");
            if (!homescreen){
                System.out.println("return to Home screen - h");
            }

        } else {
            System.out.println("l - logout");
        }
    }

    public static void invalidCommand(){
        System.out.println("** Invalid command **");
    }


    
}
