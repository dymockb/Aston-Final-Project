package util;

public class IsInteger {

    public IsInteger(){

    }
    
    public static Boolean checkString(String input){

        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }

    }
}
