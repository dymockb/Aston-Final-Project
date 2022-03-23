package model;

import java.util.HashMap;
import superclass.Screen;
import java.sql.*;

public class User {
    
    private Boolean isLoggedIn;
    private Boolean automated;
    private String currentScreen;
    private HashMap<String, Screen> screens;
    private Table searchResultsTable;
    private ResultSet searchResultSet;
    //private Parser parser;
    private HashMap<String, String> sqlQueries;
    //private ScreenPrinter printer;
    //private DBConnector db;

    //public User(DBConnector db, ScreenPrinter printer, HashMap<String, String> sqlQueries){
    public User(HashMap<String, String> sqlQueries){
        this.sqlQueries = sqlQueries;
        //this.printer = printer;
        //this.db = db;

        screens = new HashMap<String, Screen>();
        isLoggedIn = true;      
    }

    public void start(){

        //Router router = new Router();
        //router.goToScreen("home-screen");
        goToScreen("home-screen");

         
    }

    public HashMap<String, String> getSqlQueries(){
        return sqlQueries;
    }

    public void setSearchResultsTable(Table searchResultsTable){
        this.searchResultsTable = searchResultsTable;
    }

    public Table getSearchResultsTable(){
        return searchResultsTable;
    }

    public void setSearchResultSet(ResultSet rs){
        searchResultSet = rs;
    }

    public ResultSet getSearchResultSet(){
        return searchResultSet;
    }
    //public void setInputParser(Parser parser){
    //     this.parser = parser;
    //}

    public void setAutomated(Boolean automated){
        this.automated = automated;
    }

    public boolean getIsAutomated(){
        return automated;
    }

    public void addScreen(Screen screen){
        screens.put(screen.getScreenName(), screen);
    }

    public void goToScreen(String screenName){

        Screen screen = screens.get(screenName);

        screen.setCurrentScreen();
        screen.displayMenu();
        screen.getUserInput();

    }

    public void setLoggedInStatus(Boolean isLoggedIn){
        this.isLoggedIn = isLoggedIn;
    }

    public Boolean getIsLoggedIn(){
        return isLoggedIn;
    }

    public void setCurrentScreen(String screenName){
        currentScreen = screenName;
    }

    public String getCurrentScreenName(){
        return currentScreen;
    }

    public void newScreenRequest(String screenName){

        if (currentScreen.equals(screenName)){

            if (isLoggedIn){
                System.out.println("You're already on that screen");
                goToScreen(screenName);
            } else {
                permissionDenied();
            }

            
        } else {

            goToScreen(screenName);
            
        }

    }

    public void permissionDenied(){
        System.out.println("permission denied");
    }
}
