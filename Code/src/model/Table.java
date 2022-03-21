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
 
    ArrayList<Boolean> switches = new ArrayList<Boolean>();

    public Table(ResultSet rs, Parser parser){

        this.rs = rs;
        this.parser = parser;
        printer = new ScreenPrinter();
        switches.add(false);


    }

    //public abstract void startBrowsing(int startingRow, int rowsToDisplay);

    public void startBrowsing() {

            String userInput;
            numberOfRows = getNumberOfRows();
            rowsToDisplay = 2;
            System.out.println("rows to display: " + rowsToDisplay);

            Boolean browsingTable = true;

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
    
                        //} else if (Integer.valueOf(userInput) instanceof Integer){
                        } else if (isInteger(userInput)){
                                
                            int selectedRow = Integer.parseInt(userInput);                       
    
                            if (selectedRow <= numberOfRows){
    
                                System.out.println("Row " + selectedRow + " selected. SQL needed to fetch the show.");
    
                                int showID = Integer.parseInt(getShowByRowNumber(rs, selectedRow));
    
                                viewUniqueShow(showID);
    
                            } else {
    
                                printer.rowSelectionNotAvailableMessage();
    
                            }
    
                        } else {
    
                            printer.invalidCommand();
                            //browsingTable = false;
    
                        }
    
            }            
            

    }

    //protected int getNumberOfRows(ResultSet rs){
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

    /** 
    private void printRows(){

        try {

			rsmd = rs.getMetaData();
			
			//int noOfRows = getNumberOfRows();
			numberOfCols = rsmd.getColumnCount();

			endingRow = (startingRow + rowsToDisplay) < numberOfRows ? startingRow + rowsToDisplay : numberOfRows; 	

            printTitle();
            printColumnHeadings();
            printRows();

			//printer.printTableHeading(startingRow, endingRow, numberOfRows);
			//printer.printColumnTitles(rsmd);
			//printer.printTableData(rs, startingRow, endingRow, noOfCols);

   
                rs.beforeFirst();
                
                if ( !(startingRow == rs.getRow()) ){
    
                    while (rs.next()) {
                        if(startingRow == rs.getRow()){			
                            break;
                        }			   
                    }
                } 
    
                while (rs.next() && startingRow < numberOfRows) {
    
                    System.out.print("| ");
                    System.out.print(createSubString("" + rs.getRow()));
    
                    for (int i = 1; i <= numberOfCols; i ++){
                        System.out.print("| ");
                        System.out.print(createSubString(rs.getString(i)));
                    }
                    System.out.println(" |");	
                    startingRow ++;
                }	
    
                System.out.println("+-----------------");
    
            
            
        } catch (SQLException e) {
			e.printStackTrace();
		}


    }

    */

    public boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }

    private void printTitle(){

        printer.printTableTitle(startingRow, endingRow, numberOfRows);

    }

    private void printColumnHeadings(){

        //printer.printColumnHeadings(rsmd);

        try {
            int cols = rsmd.getColumnCount();
            printer.printDivider();
            //System.out.println("+-----------------");
            //System.out.print("| ");
            printer.printColDivider();
            printer.printCell(" # ");
            //System.out.print(createSubString(" # "));			
            for (int i = 1; i <= cols; i ++){
                //System.out.print("| ");
                printer.printColDivider();
                printer.printCell(rsmd.getColumnName(i));
                //System.out.print(createSubString(rsmd.getColumnName(i)));
            }
            printer.printColDivider();
            //System.out.print(" |");
            printer.printDivider();
            //System.out.println("\n+-----------------");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        

    }

    private void printRows(){

        try {

        rsmd = rs.getMetaData();
        
        //int noOfRows = getNumberOfRows();
        numberOfCols = rsmd.getColumnCount();

        endingRow = (startingRow + rowsToDisplay) < numberOfRows ? (startingRow + rowsToDisplay) : numberOfRows; 	

        printTitle();
        printColumnHeadings();
        //printRows();

        
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
            //System.out.print("| ");
            printer.printCell("" + rs.getRow());
            //System.out.print(createSubString("" + rs.getRow()));

            for (int i = 1; i <= numberOfCols; i ++){
                printer.printColDivider();
                printer.printCell(rs.getString(i));
            }
            printer.printColDivider();
            printer.printDivider();
            //startingRow ++;

        }

        
            //printer.printDivider();

            /** 

            System.out.print("| ");
            System.out.print(createSubString("" + rs.getRow()));

            for (int i = 1; i <= numberOfCols; i ++){
                System.out.print("| ");
                System.out.print(createSubString(rs.getString(i)));
            }
            System.out.println(" |");	
            startingRow ++;
        }	

        System.out.println("+-----------------");

         */
        } catch (SQLException e) {
			e.printStackTrace();
		}

    }

    public String getShowByRowNumber(ResultSet rs, int selectedRow){

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

    public void viewUniqueShow(int showID){

        Boolean viewingShow = true;

        System.out.println("the show ID is: " + showID);

        while (viewingShow) {
            
            String userInput = parser.getInput("unique-show", "TBC", switches);
            
            if (userInput.equals("b")){

                System.out.println("Browse available dates (not working)");

            } else if (userInput.equals("q")) {

                viewingShow = false;

            } else {

                printer.invalidCommand();

            }

        }

    }
    
    public abstract void selectRow();


    

}
