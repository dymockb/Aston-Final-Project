package superclass;

import java.util.HashMap;
import util.Parser;
import util.MenuPrinter;

public class Menu {
 
    private HashMap<String, String> options;
    private Parser parser;
    private MenuPrinter printer;
  
    public Menu(){

        this.parser = new Parser();
        this.printer = new MenuPrinter();
        options = new HashMap<String, String>();
        addOptions();

    }

    public void displayPrompt(){
        System.out.println("Main menu - available commands:");
    }

    public void addOptions(){
        options.put("l", "login as admin user (not working yet)");
        options.put("b", "browse shows and book tickets (in progress)");
        options.put("q", "quit");
    
    }

    public void displayOptions(){
        System.out.println("l - login");
        System.out.println("b - browse");
        System.out.println("q - quit");
        System.out.print("> ");
    }

    public String getUserInput(){
        
        return parser.getInputForMenu();

    }

}
