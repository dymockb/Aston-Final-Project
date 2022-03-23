package screens;

import superclass.Screen;
import util.Parser;

public class Home extends Screen {

    public Home(String screenName, Parser parser){
        super(screenName, parser);
    
    }



    public void displayPrompt(){
        System.out.println("Main menu - available commands:");
    }

    public void addOptions(){
        options.put("l", "login as admin user (not working yet)");
        options.put("b", "browse shows and book tickets (in progress)");
        options.put("h", "return to home screen");
        options.put("q", "quit");
    
    }

    public void displayOptions(){

        for(String key : options.keySet()){
            System.out.println(key + " - " + options.get(key));
        }
        System.out.print("> ");

    }

    public void displayMenu(){

        displayPrompt();
        displayOptions();

    }


    public void getUserInput(){

        Boolean gettingInput = true;
        String userInput = null;
        while(gettingInput){

            userInput = parser.getInputForMenu();
            if(userInput.equals("b")){
                user.newScreenRequest("search-screen");
                gettingInput = false;
            } else if (userInput.equals("l")){
                System.out.println("login screen tbc");
                gettingInput = false;
            } else if (userInput.equals("h")){
                //user.setAdminStatus(false);
                user.newScreenRequest(screenName);
                gettingInput = false;
            } else {
                System.out.println("invalid command");                
            }

        }

    }
    
}
