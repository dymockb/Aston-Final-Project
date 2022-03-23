package model;

import java.util.HashMap;
import superclass.Screen;
import util.Parser;
import util.ScreenPrinter;
import util.DBConnector;
import screens.Shows;

public class User {
    
    private Boolean isAdmin;
    private Boolean automated;
    private String currentScreen;
    private HashMap<String, Screen> screens;
    private Table searchResultsTable;
    private Parser parser;
    private HashMap<String, String> sqlQueries;
    private ScreenPrinter printer;
    private DBConnector db;

    public User(DBConnector db, ScreenPrinter printer, HashMap<String, String> sqlQueries){
        this.sqlQueries = sqlQueries;
        this.printer = printer;
        this.db = db;

        screens = new HashMap<String, Screen>();
        isAdmin = true;      
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

    public void setInputParser(Parser parser){
        this.parser = parser;
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

        //screen.displayContent();
        screen.setCurrentScreen();
        screen.displayMenu();
        if(!screenName.equals("shows-screen")){
            screen.getUserInput();
        }

        if(screenName.equals("shows-screen")){
            Shows showsScreen = (Shows)screen;
            showsScreen.getUserInput();
        }

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
