package screens;
import superclass.Screen;
import util.Parser;
import util.DBConnector;
import model.Basket;
import java.util.NoSuchElementException;

public class BasketScreen extends Screen {

    private Basket basket;

    public BasketScreen(String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
        
    }

    public void displayScreen() throws NoSuchElementException {

        String nextScreen = "home-screen";

        try{

        basket = user.getBasket();
        int numberOfTicketsInBasket = basket.displayBasket();
        if(numberOfTicketsInBasket > 0 ){
            System.out.println("c - Checkout");
            System.out.println("s - Search for shows and buy tickets");
            String userInput = parser.getInputForMenu();
            if(userInput.equals("c")){
                basket.startCheckout();
            } else if (userInput.equals("s")){
                user.newScreenRequest("search-screen");
            }
        } else {
            System.out.println("s - Search for shows and buy tickets");
            System.out.println("h - Home screen");
            String userInput = parser.getInputForMenu();
            
            if(userInput.equals("s")){
                nextScreen = "search-screen";
            } else if (userInput.equals("h")){
                nextScreen = "home-screen";
            }
        }

        } catch (NoSuchElementException e){

            System.out.println("error - end of text file");
            noUserTextFileErrors = false;
        }

        if(noUserTextFileErrors){
            user.newScreenRequest(nextScreen);
        }


    }
    
}
