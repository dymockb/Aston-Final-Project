package util;

import model.UserInterface;

public class Router {
    
    UserInterface userInterface;

    public Router(UserInterface userInterface){

        this.userInterface = userInterface;

    }

    public Router(){

    }

    public void goToScreen(String screenName){

    }

    public void newScreenRequest(String userInput, String screenName){

        if (screenName.equals("home-screen")){

            System.out.println("You're already on that screen");
            userInterface.goToScreen(screenName);
            
        } else {

            System.out.println("That screen not made yet");
        }




    }
}
