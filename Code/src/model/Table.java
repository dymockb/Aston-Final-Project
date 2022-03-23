package model;

import java.sql.*;
import util.ScreenPrinter;
import util.Parser;
import util.IsInteger;
import java.util.ArrayList;

public class Table {

    private String tableName;
    private ResultSet rs;
    private ResultSetMetaData rsmd;

    private ScreenPrinter printer;
    private Parser parser;

    private int numberOfRows;
    private int numberOfCols;

    private int rowsToDisplay;
    private int startingRow;
    private int endingRow;

    private int selectedRow;

    private int columnToHide;
 
    ArrayList<Boolean> switches = new ArrayList<Boolean>();

    public Table(ResultSet rs, Parser parser, String tableName){

        this.rs = rs;
        this.parser = parser;
        this.tableName = tableName;
        printer = new ScreenPrinter();
        switches.add(false);

        try {

            rsmd = rs.getMetaData();
            numberOfCols = rsmd.getColumnCount();

        } catch  (SQLException e) {
			e.printStackTrace();
		}


    }

    public String startBrowsing() {

            String userInput;
            numberOfRows = getNumberOfRows();
            rowsToDisplay = 5;



            //int selectedRowNumber = -1;
            userInput = "tbc";

            Boolean browsingTable = true;
            Boolean hideRows = false;
            while(browsingTable){

                if(!hideRows){
                    printRows();
                }

                hideRows = false;

                userInput = parser.getInput("browse-table", "show", switches);
    
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
    
                        } else if (IsInteger.checkString(userInput)){

                            return userInput;
                            /** 
                                
                            selectedRow = Integer.parseInt(userInput);                       
    
                            if (selectedRow <= numberOfRows){
    
                                System.out.println("Row " + selectedRow + " selected.");
    
                                selectedRowNumber = Integer.parseInt(getFirstCellofSelectedRowInResultSet());
                                browsingTable = false;
                            /** 
                            } else {
    
                                printer.rowSelectionNotAvailableMessage();
    
                            }
                            */
    
                        } else {
    
                            printer.invalidCommand();
                            hideRows = true;
    
                        }
    
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

        printer.printTableTitle(tableName, startingRow, endingRow, numberOfRows);

    }

    private void printRowSelectionMsg(){
        printer.printRowSelectionMsg();;
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

    private void printRows(){

        try {

        endingRow = (startingRow + rowsToDisplay) < numberOfRows ? (startingRow + rowsToDisplay) : numberOfRows; 	

        printTitle();
        printRowSelectionMsg();
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
    
    public String getFirstCellofSelectedRowInResultSet(){

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
