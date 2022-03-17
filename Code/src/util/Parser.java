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
    private Scanner reader;         // source of command input
    private String fileName;
    private File file;
    //private boolean testingOn;

    /**
     * Create a parser to read from the terminal window.
    */ 
    public Parser() 
    {
        reader = new Scanner(System.in);
        //testingOn = false;
    }
    

    /** 
    public Parser(String filePath, String fileName) throws FileNotFoundException
    {
        File file = new File(filePath);
        reader = new Scanner(file);
        this.fileName = fileName;
        //.useDelimiter(";");
        //testingOn = true;
    }
    */
    
    public void addFile(String filePath, String fileName) throws FileNotFoundException {
        file = new File(filePath);
        reader = new Scanner(file);
        this.fileName = fileName;
    };

    //private void printInput(String input){
    //    if(testingOn){
    //        System.out.println(input);
    //    }
    //}

    /**
     * @return 
     * 
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
        } else if (request.equals("commands-list")){
            System.out.println("Available Commands:");
            System.out.println("a - display all shows");
            System.out.println("q - quit");
            System.out.print("> ");     // print prompt            
            inputLine = reader.nextLine();            
        } else {
            inputLine = "";
        }

        //printInput(inputLine);

        return inputLine;

    }

    public String getSQL() {
        
        reader.useDelimiter(";");
        
        String inputLine;   // will hold the full input line

        //System.out.print("> ");     // print prompt

        inputLine = reader.next();
        //printInput(inputLine);
        return inputLine;
    }

    /** The file name used in the Parser constructor must
     *  match the database tablename */
    public String createSqlDataFromCSV(){
    
        String sqlString = "INSERT INTO " + fileName + " (";

        String columnTitles = reader.nextLine();

        sqlString += columnTitles + ") VALUES ";

        String tableDataRow = "";

        while(reader.hasNextLine()){
            tableDataRow = reader.nextLine();
            sqlString += "(" + tableDataRow + "),";
            //System.out.println(tableDataRow);
        }

        sqlString = sqlString.substring(0,sqlString.length() - 1) + ";";

        System.out.println(sqlString);
        
        return sqlString;
    
    };

    public void closeScanner(){

        reader.close();

    }
    

}
