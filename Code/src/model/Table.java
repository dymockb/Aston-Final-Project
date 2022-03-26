package model;

import java.sql.*;
import util.ScreenPrinter;
import util.Parser;
import java.util.ArrayList;
import util.StaticPrinter;

public class Table {

    private String eventName;
    private String tableName;
    private String orderedBy;
    private Boolean isBookingTable;

    private ResultSet rs;
    private ResultSetMetaData rsmd;

    private ScreenPrinter printer;
    private Parser parser;

    private int numberOfRows;
    private int numberOfCols;

    private int rowsToDisplay;
    private int startingRow;
    private int endingRow;

    private int columnToHide;
 
    ArrayList<Boolean> switches = new ArrayList<Boolean>();

    public Table(ResultSet rs, Parser parser, String eventName, String tableName, String orderedBy, Boolean isBookingTable){

        this.rs = rs;
        this.parser = parser;
        this.tableName = tableName;
        this.eventName = eventName;
        this.orderedBy = orderedBy;
        this.isBookingTable = isBookingTable;


        printer = new ScreenPrinter();
        switches.add(false);

        try {

            rsmd = rs.getMetaData();
            numberOfCols = rsmd.getColumnCount();

        } catch  (SQLException e) {
			e.printStackTrace();
		}


    }

    public String startBrowsing(Boolean hideRows, Boolean loggedIn, Boolean homescreen) {

            String userInput;
            numberOfRows = getNumberOfRows();
            rowsToDisplay = 5;

            userInput = "";

            Boolean browsingTable = true;
            
            while(browsingTable){

                //PRINT THE TABLE

                if(!hideRows){
                    printTable();
                }

                hideRows = false;

                StaticPrinter.printTableRowSelectionMsg(isBookingTable);
                //System.out.println("Please select a row number to view more details.");
                System.out.println("Navigation options:");
                System.out.println("f - go forward");
                System.out.println("r - return to top of results");
                System.out.println("h - return to home");

                userInput = parser.getInputForMenu();
    
                        if (userInput.equals("r")){
    
                            startingRow = 0;
                            switches.set(0, false);
    
                        } else if (userInput.equals("f")){
    
                            if (startingRow + rowsToDisplay < numberOfRows){
                                startingRow += rowsToDisplay;           
                            }
    
                            if (startingRow + rowsToDisplay >= numberOfRows){
                                switches.set(0, true);
                            }
    
                        } else {
                            return userInput;
                        } 
                        
                        /**
                        else if (IsInteger.checkString(userInput)){

                            return userInput;
                             
                                
                            selectedRow = Integer.parseInt(userInput);                       
    
                            if (selectedRow <= numberOfRows){
    
                                System.out.println("Row " + selectedRow + " selected.");
    
                                selectedRowNumber = Integer.parseInt(getFirstCellofSelectedRowInResultSet());
                                browsingTable = false;
                            /** 
                            } else {
    
                                printer.rowSelectionNotAvailableMessage();
    
                            }
                            
    
                        } else {
    
                            printer.invalidCommand();
                            hideRows = true;
    
                        }
                        */
    
            }  
            
            //return selectedRowNumber;
            return userInput;
            

    }

    public int getNumberOfRows(){	

		int output = 0;
		try {
			rs.last();
			output = rs.getRow();
			rs.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return output;

	}

    private void printTitle(){

        //printer.printTableTitle(tableName, startingRow, endingRow, numberOfRows);
        int adjStartingRow = numberOfRows == 0 ? 0 : startingRow + 1;
        System.out.print(eventName + ": " + tableName + " - " + (adjStartingRow) + " to " + endingRow + " out of " + numberOfRows + ", orderd by " + orderedBy);

    }


    private void printColumnHeadings(){

        try {
            int cols = rsmd.getColumnCount();
            printer.printDivider();
            printer.printColDivider();
            printer.printIndexCell(" # ");		
            for (int i = 1; i <= cols; i ++){
                printer.printColDivider();
                if(!rsmd.getColumnName(i).equals("ID")){
                    printer.printCell(rsmd.getColumnName(i));
                }
                if(rsmd.getColumnName(i).equals("ID")){
                    columnToHide = i;
                }
                

            }
            printer.printColDivider();
            printer.printDivider();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        

    }

    private void printTable(){

        try {

        endingRow = (startingRow + rowsToDisplay) < numberOfRows ? (startingRow + rowsToDisplay) : numberOfRows; 	

        printTitle();
        //printRowSelectionMsg();
        printColumnHeadings();
                
        rs.beforeFirst();
        
        if ( !(startingRow == rs.getRow()) ){

            while (rs.next()) {
                if(startingRow == rs.getRow()){			
                    break;
                }			   
            }
        
        }           
         
        while (rs.next() && rs.getRow() <= endingRow) {

            printer.printColDivider();
            printer.printIndexCell(String.valueOf(rs.getRow()));
            
            for (int i = 1; i <= numberOfCols; i ++){
                printer.printColDivider();
                if (i != columnToHide){
                    printer.printCell(rs.getString(i));
                }

            }
            printer.printColDivider();
            printer.printDivider();

        }

        } catch (SQLException e) {
			e.printStackTrace();
		}

    }
    
    public String getFirstCellofSelectedRowInResultSet(int selectedRow){

        String output = "";
        try {
        
            rs.beforeFirst();
            while(rs.next()){
                if (rs.getRow() == selectedRow){
                    break;
                }

            }

            output = rs.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return output;

    }


    

}
