package util;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * 
 * This parser reads user input 
 *  
 * @author  Dymock Brett
 * @version 1.0
 */
public class Parser 
{
    private Scanner reader;         // source of command input
    //private boolean testingOn;

    /**
     * Create a parser to read from the terminal window.
    */ 
    public Parser() 
    {
        reader = new Scanner(System.in);
        //testingOn = false;
    }
    

    /** */
    public Parser(String testFile) throws FileNotFoundException
    {
        File file= new File(testFile);
        reader = new Scanner(file).useDelimiter(";");
        //testingOn = true;
    }

    //private void printInput(String input){
    //    if(testingOn){
    //        System.out.println(input);
    //    }
    //}

    /**
     * @return 
     * 
     *

    public Command getInventoryCommand() 
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();
        printInput(inputLine);

        // Find up to two words on the line.
        try (Scanner tokenizer = new Scanner(inputLine)){
        
            if(tokenizer.hasNext()) {
                word1 = tokenizer.next();      // get first word
                if(tokenizer.hasNext()) {
                    word2 = tokenizer.next();      // get second word
                    // note: we just ignore the rest of the input line.
                }
            }
        }

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        
        if(commandWords.isCommand(word1)) {
            return new Command(word1, word2);
        }
        else {
            return new Command(null, word2); 
        }
    }
    */
    
    public String getInput(String request)
    {
        
        String inputLine;   // will hold the full input line
        
        if (request.equals("username")){
            System.out.println("Please enter your database username: ");
            System.out.print("> ");     // print prompt            
            inputLine = reader.nextLine();
        } else if (request.equals("password")) {
            System.out.println("Please enter your database password: ");
            System.out.print("> ");     // print prompt            
            inputLine = reader.nextLine();
        } else {
            inputLine = "";
        }

        //printInput(inputLine);

        return inputLine;

    }

    public String getSQL() {
        String inputLine;   // will hold the full input line

        System.out.print("> ");     // print prompt

        inputLine = reader.next();
        //printInput(inputLine);
        return inputLine;
    }
    

}
