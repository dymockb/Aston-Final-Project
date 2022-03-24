package screens;

import java.util.NoSuchElementException;
import superclass.Screen;
import util.DBConnector;
import util.Parser;

public class Home extends Screen {

    public Home(String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }

    public void displayScreen() throws NoSuchElementException {

        Boolean gettingInput = true;
        String userInput = null;
        while(gettingInput){

            System.out.println("Main menu - available commands:");
            System.out.println("b - browse shows and book tickets");
            displayLoginLogout(); // l - login  / logout
            System.out.println("q - quit");

            try {
                userInput = parser.getInputForMenu();
            } catch (NoSuchElementException e){
                e.printStackTrace();
                gettingInput = false;
            }

            //userInput = parser.getInputForMenu();

            if(userInput.equals("b")){

                gettingInput = false;
                user.newScreenRequest("search-screen");

            } else if (userInput.equals("q")){

                System.out.println("Quit");
                gettingInput = false;

            } else if (userInput.equals("l")){
                
                //user.setLoggedInStatus(true);

                System.out.println("login screen tbc");

            } else {
                System.out.println("invalid command");                
            }

        }

    }
    
}
