package model;

import java.sql.*;
import util.ScreenPrinter;
import util.Parser;
import java.util.ArrayList;

public abstract class Table {

    protected ResultSet rs;
    protected ScreenPrinter printer;
    protected Parser parser;
    protected int numberOfRows;
    protected int startingRow;
    protected int rowsToDisplay;
    ArrayList<Boolean> switches = new ArrayList<Boolean>();

    public Table(ResultSet rs, Parser parser){

        this.rs = rs;
        this.parser = parser;
        printer = new ScreenPrinter();
        switches.add(false);


    }

    //public abstract void startBrowsing(int startingRow, int rowsToDisplay);

    public void startBrowsing(int startingRow, int rowsToDisplay) {

            String userInput;
            numberOfRows = getNumberOfRows();
    
            Boolean browsingTable = true;
            System.out.println("up to here");
            while(browsingTable){

                printRows(startingRow, rowsToDisplay);
    
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

    private void printRows(int startingRow, int rowsToDisplay){

        try {

			ResultSetMetaData rsmd = rs.getMetaData();
			
			//int noOfRows = getNumberOfRows();
			int noOfCols = rsmd.getColumnCount();

			int endingRow = (startingRow + rowsToDisplay) < numberOfRows ? startingRow + rowsToDisplay : numberOfRows; 	

			printer.printTableHeading(startingRow, endingRow, numberOfRows);
			printer.printColumnTitles(rsmd);
			printer.printTableData(rs, startingRow, endingRow, noOfCols);
            
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
