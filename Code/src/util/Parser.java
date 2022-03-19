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
    private Responder responder;

    /**
     * Create a parser to read from the terminal window.
    */ 
    public Parser() 
    {
        reader = new Scanner(System.in);
        testingOn = false;
        responder = new Responder();
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

    public String getInput(String request, String keyword)
    {

        String inputLine = null;
        responder.displayCommands(request, keyword);
        inputLine = reader.nextLine();
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
