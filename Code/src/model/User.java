package model;

import java.util.HashMap;

public class User {

    private HashMap<String, Menu> menus;

    public User(){

        menus = new HashMap<String, Menu>();
        createMenus();

    }

    public void createMenus(){

    }
    
    public String goToMenu(Menu menu){

        return "the user input from menu";
        
    } 

}
