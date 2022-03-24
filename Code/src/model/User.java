package model;

import java.util.HashMap;
import superclass.Screen;
import superclass.SearchDB;
import java.sql.*;

public class User {
    
    private Boolean isLoggedIn;
    private Boolean automated;
    private String currentScreen;
    private HashMap<String, Screen> screens;
    private ResultSet searchResultSet;
    private String previousSearch;
    private HashMap<String, String> sqlQueries;
    private HashMap<String, SearchDB> searchHistory;

    public User(HashMap<String, String> sqlQueries){
        this.sqlQueries = sqlQueries;
        screens = new HashMap<String, Screen>();
        searchHistory = new HashMap<String, SearchDB>();
        isLoggedIn = false;      
    }

    public void start(){

        goToScreen("home-screen");
 
    }

    public HashMap<String, String> getSqlQueries(){
        return sqlQueries;
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

    /** 

    public void setSearchResultsTable(Table searchResultsTable){
        this.searchResultsTable = searchResultsTable;
    }

    public Table getSearchResultsTable(){
        return searchResultsTable;
    }

    /** */

    public void setSearchResultSet(ResultSet rs){
        searchResultSet = rs;
    }

    public ResultSet getSearchResultSet(){
        return searchResultSet;
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
        setCurrentScreen(screenName);
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

        if (currentScreen.equals(screenName)){

            if (isLoggedIn){
                System.out.println("You're already on that screen");
                goToScreen(screenName);
            } else {
                permissionDenied();
            }

            
        } else {
            //if (isLoggedIn){
                goToScreen(screenName);
            //} else {
            //    permissionDenied();
            //}    
        }

    }

    public void permissionDenied(){
        System.out.println("permission denied");
    }
}
