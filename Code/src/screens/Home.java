package screens;

import superclass.Screen;
import util.DBConnector;
import util.Parser;
import model.User;

public class Home extends Screen {

    public Home(String screenName, Parser parser, DBConnector db){
        super(screenName, parser, db);
    
    }



    public void displayPrompt(){
        System.out.println("Main menu - available commands:");
        System.out.println("b - browse shows and book tickets");
        System.out.println("q - quit");
    }

    public void addOptions(){
        options.put("b", "browse shows and book tickets (in progress)");
        options.put("q", "quit");
    
    }

    public void displayOptions(){

        for(String key : options.keySet()){
            System.out.println(key + " - " + options.get(key));
        }

    }

    public void getUserInput(){

        Boolean gettingInput = true;
        String userInput = null;
        while(gettingInput){

            userInput = parser.getInputForMenu();
            if(userInput.equals("b")){
                gettingInput = false;
                user.newScreenRequest("search-screen");

            } else if (userInput.equals("q")){
                System.out.println("Quit");
                gettingInput = false;
            } else if (userInput.equals("l")){
                System.out.println("login screen tbc");
                gettingInput = false;
            } else if (userInput.equals("h")){
                //user.setAdminStatus(false);
                gettingInput = false;
                user.newScreenRequest(screenName);

            } else {
                System.out.println("invalid command");                
            }

        }

    }
    
}
