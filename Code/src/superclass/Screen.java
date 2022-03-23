package superclass;
import model.User;
import java.util.HashMap;

import util.DBConnector;
import util.Parser;
import java.sql.*;

public abstract class Screen {

    protected HashMap<String, String> options;
    protected User user;
    protected String screenName;
    protected Parser parser;
    protected ResultSet rs;
    protected ResultSetMetaData rsmd;
    protected DBConnector db;
    
    public Screen(String screenName, Parser parser, DBConnector db){

        this.screenName = screenName;
        this.parser = parser;
        this.db = db;
        
        options = new HashMap<String, String>();
        addOptions();
    
    }

    public void registerUser(User user){

        this.user = user;

    }

    public void setCurrentScreen(){
        user.setCurrentScreen(screenName);
    }

    public String getScreenName(){
        return screenName;
    }

    public void displayStandardOptions(){

        System.out.println("h - return to Home Screen");
        System.out.println("l - login as Admin User");
        System.out.print("> ");

    }

    public void displayMenu(){

        displayPrompt();
        displayOptions();
        displayStandardOptions();

    }

    public abstract void getUserInput();

    public abstract void displayPrompt();

    public abstract void addOptions();

    public abstract void displayOptions();

    //public abstract void displayMenu();


    
}
