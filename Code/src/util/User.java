package util;

import model.UserInterface;

public class User {
    
    private UserInterface userInterface;
    private Boolean isAdmin;
    private String currentScreen;

    public User(UserInterface userInterface){

        this.userInterface = userInterface;
        isAdmin = true;

    }

    public void setAdminStatus(Boolean isAdmin){
        this.isAdmin = isAdmin;
    }

    public void setCurrentScreen(String screenName){
        currentScreen = screenName;
    }

    public void newScreenRequest(String screenName){

        //if (screenName.equals("home-screen")){
        if (currentScreen.equals(screenName)){

            if (isAdmin){
                System.out.println("You're already on that screen");
                userInterface.goToScreen(screenName);
            } else {
                permissionDenied();
            }

            
        } else {

            userInterface.goToScreen(screenName);
            
        }

    }

    public void permissionDenied(){
        System.out.println("permission denied");
    }
}
