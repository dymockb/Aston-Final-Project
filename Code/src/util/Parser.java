package util;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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

    public void inputFromString(String inputString){
        reader = new Scanner(inputString);
    }

    public void testingOn(){
        testingOn = true;
    }

    public void testingOff(){
        testingOn = false;
    }

    private void printInput(String input, Boolean descriptionOfInput){
        if(testingOn){
            if(descriptionOfInput){
                System.out.println(input);
            } else {
                System.out.print(" - " + input + "\n");
            }

        }
    }

    public String getInput(String request, String keyword, ArrayList<Boolean> switches)
    {

        String inputLine = null;
        responder.displayCommands(request, keyword, switches);
        inputLine = reader.nextLine();
        Boolean descriptionOfInput = false;
        printInput(inputLine, descriptionOfInput);
        return inputLine;
 
    }

    public String getInputForMenu(){
        Boolean descriptionOfInput = true;
        System.out.print("> ");
        String inputLine = null;
        inputLine = reader.nextLine();
        printInput(inputLine, descriptionOfInput);
        while(inputLine.startsWith("*")){
            descriptionOfInput = false;
            inputLine = reader.nextLine();
            printInput(inputLine, descriptionOfInput);
        }
        //System.out.println("input returned: " + inputLine);
        return inputLine;
    }
    
    public String getText(String prompt){
        System.out.println(prompt);
        return reader.nextLine();
    }

    public String getSQL() {
        reader.useDelimiter(";");       
        String inputLine = reader.next();
        return inputLine;
    }

    public String getUserSeat(){
        reader.useDelimiter(",");
        String seat = reader.next();
        return seat;
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
    
    }

    public void closeScanner(){

        reader.close();

    }
    

}
