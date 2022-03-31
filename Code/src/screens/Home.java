package screens;

import java.util.NoSuchElementException;
import superclass.Screen;
import util.DBConnector;
import util.Parser;
import util.StaticPrinter;

public class Home extends Screen {

    public Home(String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }

    public void displayScreen() throws NoSuchElementException {

        Boolean gettingInput = true;
        String userInput = null;

        String nextScreen = "home-screen";

        while(gettingInput){

            StaticPrinter.printHomeScreenHeading();

            System.out.println("Main menu - available commands:");
            System.out.println("s - Search for shows and buy tickets");
            System.out.println("g - go to basket");
            displayLoginLogout(); // l - login  / logout
            System.out.println("q - quit");
            displayBasketStatus();
            
            try {

                userInput = parser.getInputForMenu();

            } catch (NoSuchElementException e){
                
                System.out.println("End of test file.");
                gettingInput = false;
                noUserTextFileErrors = false;
            }

            if (userInput != null){

                if(userInput.equals("s")){

                    gettingInput = false;
                    nextScreen = "search-screen";
                   
    
                } else if (userInput.equals("q")){
    
                    nextScreen = "Quit";
                    gettingInput = false;
    
    
                } else if (userInput.equals("g")){
    
                    nextScreen = "basket-screen";
                    gettingInput = false;
    
    
                } else if (userInput.equals("l")){

                    System.out.println("Login facility is not active yet.");
    
                } else {
                    StaticPrinter.invalidCommand("Home");               
                }

            }

        }

        if (nextScreen.equals("Quit")){

            System.out.println("Quit");
        
        } else {

            if(noUserTextFileErrors){
                user.newScreenRequest(nextScreen);
            }

        }

    }
    
}
