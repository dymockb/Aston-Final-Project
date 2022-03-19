package util;

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

    public void displayCommands(String request) {

        if (request.equals("username")){

            System.out.println("Please enter your database username: ");
            System.out.print("> ");  

        } 
        
        if (request.equals("password")){

            System.out.println("Please enter your database password: ");
            System.out.print("> ");  

        } 
        
        if (request.equals("main-menu")) {

            System.out.println("Available Commands:");
            System.out.println("l - login as admin user (not working yet)");
            System.out.println("b - browse shows and book tickets (in progress)");
            System.out.println("q - quit");
            System.out.print("> "); 

        } 
        
        if (request.equals("browse-table")){
            System.out.println("Available Commands:");
            System.out.println("Select a show using an ID number (not working yet)");
            System.out.println("q - quit browsing");
            System.out.print("> ");           
                       
        }

    }

}
