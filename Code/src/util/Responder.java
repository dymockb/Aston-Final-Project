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

    public void showCommands(String request) {

        if (request.equals("username")){

            System.out.println("Please enter your database username: ");
            System.out.print("> ");  

        } else if (request.equals("password")){

            System.out.println("Please enter your database password: ");
            System.out.print("> ");  

        } else if (request.equals("main-menu")) {

            System.out.println("Available Commands:");
            System.out.println("a - display all shows");
            System.out.println("b - browse shows");
            System.out.println("q - quit");
            System.out.print("> "); 

        } else if (request.equals("browse-table")){
            System.out.println("Available Commands:");
            System.out.println("<ShowID> - select a show using an ID number (eg. 1");            
            System.out.println("f - go forward");
            System.out.println("r - return to start");
            System.out.println("q - quit browsing");
            System.out.print("> ");           
                       
        } else {

            System.out.println("Invalid command.");

        }
    }

}
