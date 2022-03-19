package util;

import java.util.ArrayList;

/**
 * 
 * This responds to the user input 
 *  
 * @author  
 * @version 1.0
 */
public class Responder
{


    /**
     * Respond to user input .    
     * */ 
    public Responder() {

    }

    public void displayCommands(String request, String keyword, ArrayList<Boolean> switches) {

        if (request.equals("username")){

            System.out.println("Please enter your " + keyword + " username: ");
            System.out.print("> ");  

        } 
        
        if (request.equals("password")){

            System.out.println("Please enter your " + keyword + " username: ");
            System.out.print("> ");  

        } 
        
        if (request.equals("main-menu")) {

            System.out.println(keyword + ": Available Commands:");
            System.out.println("l - login as admin user (not working yet)");
            System.out.println("b - browse shows and book tickets (in progress)");
            System.out.println("q - quit");
            System.out.print("> "); 

        } 
        
        if (request.equals("browse-table")){
            System.out.println("Available Commands:");
            System.out.println("Please select a " + keyword + " using an ID number (not working yet)");
            
            if (switches.size() > 0 && !(switches.get(0) == true) ){
            
            System.out.println("f - go forward");
            
            }

            System.out.println("r - return to top of results");
            System.out.println("q - quit & return to previous menu");
            System.out.print("> ");           
                       
        }

        if (request.equals("choose-browsing-mode")){
            System.out.println("Available Commands:");
            System.out.println("s - search for a show by name or keyword. (not working)");
            System.out.println("a - display shows in alphabetical order. (in progress)");
            System.out.println("c - display shows by category (e.g musical, opera). (not working)");
            System.out.println("d - display shows by performance date. (not working)");
            System.out.println("q - quit browsing");
            System.out.print("> ");             
        }

        if (request.equals("unique-show")) {

            System.out.println("Available Commands:");
            System.out.println("b - book tickets for this show (not working)");
            System.out.println("q - quit and go back to previous menu");
            System.out.print("> "); 

        } 


    }

}
