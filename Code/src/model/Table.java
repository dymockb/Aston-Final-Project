package model;

import java.sql.*;
import util.ScreenPrinter;
import util.Parser;
import java.util.ArrayList;

public abstract class Table {

    protected ResultSet rs;
    protected ResultSetMetaData rsmd;

    protected ScreenPrinter printer;
    protected Parser parser;

    protected int numberOfRows;
    protected int numberOfCols;

    protected int rowsToDisplay;
    protected int startingRow;
    protected int endingRow;

    protected int selectedRow;

    protected int columnToHide;
 
    ArrayList<Boolean> switches = new ArrayList<Boolean>();

    public Table(ResultSet rs, Parser parser){

        this.rs = rs;
        this.parser = parser;
        printer = new ScreenPrinter();
        switches.add(false);


    }

    public int startBrowsing() {

            String userInput;
            numberOfRows = getNumberOfRows();
            rowsToDisplay = 5;
            System.out.println("rows to display: " + rowsToDisplay);

            Boolean browsingTable = true;

            int selectedRowNumber = -1;

            while(browsingTable){

                printRows();
    
                userInput = parser.getInput("browse-table", "show", switches);
    
                        if (userInput.equals("b"))  {
    
                            browsingTable = false;
    
                        } else if (userInput.equals("r")){
    
                            startingRow = 0;
                            switches.set(0, false);
    
                        } else if (userInput.equals("f")){
    
                            if (startingRow + rowsToDisplay < numberOfRows){
                                startingRow += rowsToDisplay;           
                            }
    
                            if (startingRow + rowsToDisplay >= numberOfRows){
                                switches.set(0, true);
                            }
    
                        } else if (isInteger(userInput)){
                                
                            selectedRow = Integer.parseInt(userInput);                       
    
                            if (selectedRow <= numberOfRows){
    
                                System.out.println("Row " + selectedRow + " selected.");
    
                                selectedRowNumber = Integer.parseInt(getFirstCellofSelectedRowInResultSet());
                                browsingTable = false;
    
                            } else {
    
                                printer.rowSelectionNotAvailableMessage();
    
                            }
    
                        } else {
    
                            printer.invalidCommand();
    
                        }
    
            }  
            
            return selectedRowNumber;
            

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

        printer.printTableTitle(startingRow, endingRow, numberOfRows);

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

        rsmd = rs.getMetaData();

        numberOfCols = rsmd.getColumnCount();

        endingRow = (startingRow + rowsToDisplay) < numberOfRows ? (startingRow + rowsToDisplay) : numberOfRows; 	

        printTitle();
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

    public boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
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
