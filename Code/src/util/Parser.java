package util;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * 
 * This parser reads user input 
 *  
 * @author  
 * @version 1.0
 */
public class Parser 
{
    private Scanner reader;
    private File file;
    private boolean testingOn;

    /**
     * Create a parser to read from the terminal window.
    */ 
    public Parser() 
    {
        reader = new Scanner(System.in);
        testingOn = false;
    }
    

    public void addFile(String filePath) throws FileNotFoundException {
        file = new File(filePath);
        reader = new Scanner(file);
        testingOn();
    };

    public void testingOn(){
        testingOn = true;
    }

    public void testingOff(){
        testingOn = false;
    }

    private void printInput(String input){
        if(testingOn){
            System.out.println(input);
        }
    }

    public String getInput(String request)
    {
        
        String inputLine;
        
        if (request.equals("username")){
            System.out.println("Please enter your database username: ");
            System.out.print("> ");                 
            inputLine = reader.nextLine();
        } else if (request.equals("password")) {
            System.out.println("Please enter your database password: ");
            System.out.print("> ");           
            inputLine = reader.nextLine();
        } else if (request.equals("commands-list")){
            System.out.println("Available Commands:");
            System.out.println("a - display all shows");
            System.out.println("b - browse shows");
            System.out.println("q - quit");
            System.out.print("> ");           
            inputLine = reader.nextLine();            
        } else if (request.equals("browse-table")){
            System.out.println("Available Commands:");
            System.out.println("f - go forward");
            System.out.println("r - return to start");
            System.out.println("q - quit browsing");
            System.out.print("> ");           
            inputLine = reader.nextLine();            
        } else {
            inputLine = "";
        }

        printInput(inputLine);
        return inputLine;

    }

    public String getSQL() {
        reader.useDelimiter(";");       
        String inputLine = reader.next();
        return inputLine;
    }

    public String createSqlDataFromCSV(String tableName){
    
        String sqlString = "INSERT INTO " + tableName + " (";

        String columnTitles = reader.nextLine();

        sqlString += columnTitles + ") VALUES ";

        String tableDataRow = "";

        while(reader.hasNextLine()){
            tableDataRow = reader.nextLine();
            sqlString += "(" + tableDataRow + "),";
        }

        sqlString = sqlString.substring(0,sqlString.length() - 1) + ";";

        return sqlString;
    
    };

    public void closeScanner(){

        reader.close();

    }
    

}
