package model;

import java.sql.*;
import util.ScreenPrinter;
import util.Parser;
import java.util.ArrayList;
import java.util.HashMap;
import util.StaticPrinter;

public class Table {

    private String eventName;
    private String tableName;
    private String orderedBy;
    private String ticketPriceRange;
    private Boolean isBookingTable;
    private HashMap<String, String> columnNames;

    private ResultSet rs;
    private ResultSetMetaData rsmd;

    private ScreenPrinter printer;
    private Parser parser;

    private int numberOfRows;
    private int numberOfCols;

    private int rowsToDisplay;
    private int startingRow;
    private int endingRow;

    private ArrayList<String> columnsToHide;
    private ArrayList<Integer> idxOfColumnsToHide;
 
    ArrayList<Boolean> switches = new ArrayList<Boolean>();

    public Table(ResultSet rs, Parser parser, String eventName, String tableName, String orderedBy, String ticketPriceRange, HashMap<String, String> columnNames, ArrayList<String> columnsToHide, Boolean isBookingTable){

        this.rs = rs;
        this.parser = parser;
        this.tableName = tableName;
        this.eventName = eventName;
        this.orderedBy = orderedBy;
        this.columnNames = columnNames;
        this.columnsToHide = columnsToHide;
        this.isBookingTable = isBookingTable;
        this.ticketPriceRange = ticketPriceRange;

        idxOfColumnsToHide = new ArrayList<Integer>();

        printer = new ScreenPrinter();
        switches.add(false);

        try {

            rsmd = rs.getMetaData();
            numberOfCols = rsmd.getColumnCount();

        } catch  (SQLException e) {
			e.printStackTrace();
		}


    }

    public String startBrowsing(Boolean hideTable, Boolean loggedIn) {

            String userInput = null;
            numberOfRows = getNumberOfRows();
            rowsToDisplay = 5;

            Boolean browsingTable = true;
            
            while(browsingTable){

                //PRINT THE TABLE

                if(!hideTable){
                    printTable();
                }

                hideTable = false;

                
                //System.out.println("Please select a row number to view more details.");
                System.out.println("Navigation options:");
                System.out.println("f - Forward");
                System.out.println("r - Return to top of results");
                if(isBookingTable){
                    System.out.println("n - new search");
                } else {
                    System.out.println("b - back to search screen");                    
                }
                System.out.println("");                
                StaticPrinter.printTableRowSelectionMsg(isBookingTable);
                
                try {

                    userInput = parser.getInputForMenu();

                } catch (Exception e){

                    System.out.println("Table ERROR - end of test file.");
                    browsingTable = false;
                    
                }

                if (userInput != null){

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
                        browsingTable = false;
                    }
                    
                } else {
                    browsingTable = false;
                }
                     
            }  
            
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

        int adjStartingRow = numberOfRows == 0 ? 0 : startingRow + 1;
        if (!isBookingTable){
            String titleText = eventName + ": " + tableName + " - " + (adjStartingRow) + " to " + endingRow + " out of " + numberOfRows;
            StaticPrinter.printTableHeading(titleText);
        } else {
            String titleText = eventName + ": " + tableName + " - " + (adjStartingRow) + " to " + endingRow + " out of " + numberOfRows + "\n" + ticketPriceRange;
            StaticPrinter.printTableHeading(titleText);            
        }


    }


    private void printColumnHeadings(){

        try {
            int cols = rsmd.getColumnCount();
            printer.printDivider();
            printer.printColDivider();
            printer.printIndexCell("#");		
            for (int i = 1; i <= cols; i ++){
                if(!columnsToHide.contains(rsmd.getColumnName(i))){
                    printer.printColDivider();
                    String heading = columnNames.get(rsmd.getColumnName(i)) != null ? columnNames.get(rsmd.getColumnName(i)) : rsmd.getColumnName(i);
                    printer.printCell(heading, 16);
                }
                if(columnsToHide.contains(rsmd.getColumnName(i))){
                    idxOfColumnsToHide.add(i);
                }
                

            }
            printer.printLastColDivider();
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
               
                if (!idxOfColumnsToHide.contains(i)){
                    printer.printColDivider();
                    printer.printCell(rs.getString(i), 16);
                }

            }
            printer.printLastColDivider();
            if (rs.getRow() == endingRow ){
                printer.printDivider();
            }


        }

        //printer.printDivider();

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
