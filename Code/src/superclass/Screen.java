package superclass;
import model.User;
//import model.Basket;
//import java.util.HashMap;
//import java.util.ArrayList;

import util.DBConnector;
import util.Parser;
import util.ScreenPrinter;
import util.StaticPrinter;

import java.sql.*;

public abstract class Screen {

    //protected HashMap<String, String> options;
    //protected ArrayList<String> standardOptions;
    protected Boolean noUserTextFileErrors;
    protected User user;
    protected String screenName;
    protected Parser parser;
    protected ResultSet rs;
    protected ResultSetMetaData rsmd;
    protected DBConnector db;
    protected ScreenPrinter printer;

    public Screen(String screenName, Parser parser, DBConnector db){

        noUserTextFileErrors = true;

        this.screenName = screenName;
        this.parser = parser;
        this.db = db;

        printer = new ScreenPrinter();
        
        //standardOptions = new ArrayList<String>();
        //standardOptions.add("h - return to Home Screen");
        //standardOptions.add("l - login as Admin User");
    
    }

    public void displayLoginLogout(){
        
       StaticPrinter.printLoginLogout(user.getIsLoggedIn());
        
    }

    public void displayBasketStatus(){
        System.out.println("You currently have " + user.getBasket().getNumberOfTicketsInBasket() + " ticket(s) in your basket.");
    }

    public void registerUser(User user){

        this.user = user;

    }

    public String getScreenName(){
        return screenName;
    }

    public void updateNavHistory(){

    }

    public abstract void displayScreen();


    
}
