package superclass;
import model.User;
//import java.util.HashMap;
import java.util.ArrayList;

import util.DBConnector;
import util.Parser;
import util.ScreenPrinter;

import java.sql.*;

public abstract class Screen {

    //protected HashMap<String, String> options;
    protected ArrayList<String> standardOptions;
    protected User user;
    protected String screenName;
    protected Parser parser;
    protected ResultSet rs;
    protected ResultSetMetaData rsmd;
    protected DBConnector db;
    protected ScreenPrinter printer;

    public Screen(String screenName, Parser parser, DBConnector db){

        this.screenName = screenName;
        this.parser = parser;
        this.db = db;

        printer = new ScreenPrinter();
        
        standardOptions = new ArrayList<String>();
        standardOptions.add("h - return to Home Screen");
        standardOptions.add("l - login as Admin User");
        standardOptions.add("> ");

        //options = new HashMap<String, String>();
        //addOptions();
    
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

        for (String option : standardOptions){
            System.out.println(option);
        }

    }

    public void displayMenu(){

        displayScreenOptions();
        displayStandardOptions();

    }

    public abstract void getUserInput();

    public abstract void displayScreenOptions();

    //public abstract void addOptions();

    //public abstract void displayOptions();

    //public abstract void displayMenu();


    
}
