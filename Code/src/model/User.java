package model;

import java.util.HashMap;
import model.History;
import superclass.Screen;
import superclass.SearchDB;
import java.sql.*;
import util.DBConnector;
import util.Parser;

public class User {
    
    private DBConnector db;
    private Basket basket;

    private Boolean isLoggedIn;
    private Boolean automated;
    private String currentScreen;
    private HashMap<String, Screen> screens;
    private ResultSet searchResultSet;
    
    private String previousSearch;
    private int IDValueForNextSearch;
    private String eventName;

    private HashMap<String, String> sqlQueries;

    private History navHistory;

    private HashMap<String, SearchDB> searchHistory;
    private HashMap<String, Screen> screenHistory;

    public User(HashMap<String, String> sqlQueries, DBConnector db){
        this.sqlQueries = sqlQueries;
        this.db = db;
        //basket = new Basket(parser);
        screens = new HashMap<String, Screen>();
        searchHistory = new HashMap<String, SearchDB>();
        navHistory = new History();
        isLoggedIn = false;      
    }

    public void initialiseBasket(Parser inputParser){
        basket = new Basket(inputParser, sqlQueries);
    }

    public void start(){

        goToScreen("home-screen");
 
    }

    public DBConnector getDBConnector(){
        return db;
    }

    public Basket getBasket(){
        return basket;
    }

    public HashMap<String, String> getSqlQueries(){
        return sqlQueries;
    }

    public void setEventName(String eventName){
        this.eventName = eventName;
    }

    public String getEventName(){
        return eventName;
    }

    public void saveNewSearch(String searchName, SearchDB search){
        searchHistory.put(searchName, search);
    }

    public HashMap<String, SearchDB> getSearchHistory(){
        return searchHistory;
    }

    public void setPreviousSearch(String searchName){
        previousSearch = searchName;
    }

    public String getPreviousSearch(){
        return previousSearch;
    }

    public History getNavHistory(){
        return navHistory;
    }

    public void updateNavHistory(int index, SearchDB search, Screen screen){
        navHistory.addToHistory(index, search, screen);
    }

    public void setSearchResultSet(ResultSet rs){
        searchResultSet = rs;
    }

    public ResultSet getSearchResultSet(){
        return searchResultSet;
    }

    public void setIDValueForNextSearch(int IDValueForNextSearch){
        this.IDValueForNextSearch = IDValueForNextSearch;
    }

    public String getIDValueForNextSearch(){
        return String.valueOf(IDValueForNextSearch);
    }

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
        //SearchDB search = new SearchDB("test-string", db);
        //setCurrentScreen(screenName);
        //updateNavHistory(navHistory.getSizeOfScreenHistoryHM(), search, screen);
        screen.displayScreen();

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


        /** 
        if (currentScreen.equals(screenName)){

            if (isLoggedIn){
                System.out.println("You're already on that screen");
                goToScreen(screenName);
            } else {
                permissionDenied();
            }
    
            
        } else { 
            /** */

            //if (isLoggedIn){
                goToScreen(screenName);
            //} else {
            //    permissionDenied();
            //}    
        //}

    }

    public void permissionDenied(){
        System.out.println("permission denied");
    }
}
