package util;

import java.sql.*;
import java.util.ArrayList;

public class ScreenPrinter {

	public ScreenPrinter() {
		
	}

	public void printTableTitle(String tableName, int startingRow, int endingRow, int noOfRows){

		System.out.print(tableName + " - ");
		System.out.println("Showing results " + (startingRow + 1) + " to " + endingRow + " out of " + noOfRows + ".");
	
	}	

	public void printRowSelectionMsg(){
		System.out.print("Please select a row number to view more details.");
	}

	public void printColumnHeadings(ResultSetMetaData rsmd){

		try {
			int cols = rsmd.getColumnCount();
			System.out.println("+-----------------");
			System.out.print("| ");
			System.out.print(createSubString(5, " # "));			
			for (int i = 1; i <= cols; i ++){
				System.out.print("| ");
				System.out.print(createSubString(12, rsmd.getColumnName(i)));
			}
			System.out.print(" |");
			System.out.println("\n+-----------------");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void printColDivider(){
		System.out.print("| ");
	}

	public void printEmptyRow(){
		//System.out.println("\n+-----------------");
		System.out.println("\n");
        //System.out.println(String.format("%-80s", "\n------------------------------------------------------------------------------------------"));
	}

	public void printDivider(){
		//System.out.println("\n+-----------------");
		//System.out.println("\n");
        System.out.println(String.format("%-80s", "\n------------------------------------------------------------------------------------------"));
	}

	public void printCell(String contents){

		System.out.print(createSubString(12, contents));

	}

	public void printIndexCell(String contents){

		System.out.print(createSubString(5, contents));

	}

	public void printTableBrowsingOptions(Boolean loggedIn, Boolean homescreen){

		System.out.println("Please select a row number to view more details.");
		System.out.println("Navigation options:");
		System.out.println("f - go forward");
		System.out.println("r - return to top of results");
		//StaticPrinter.printStandardOptions(loggedIn, homescreen);
		
	}

	public void printStandardOptions(ArrayList<String> standardOptions){

        for (String option : standardOptions){
            System.out.println(option);
        }

    }

	/** 

	public void printTableData(ResultSet rs, int startingRow, int numberOfRows, int numberOfCols){


		try {

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

	public void rowSelectionNotAvailableMessage(){
		System.out.println("That selection is not available please try again.");
	}

	public void endOfTableMessage(){
		System.out.println("No more entries available.");
	}

	public String createSubString(int cellWidth, String text){
		if (text == null){
			return String.format("%-" + cellWidth + "s", "");
		} else if (text.length() >= 9){
			return String.format("%-" + cellWidth + "s", text.substring(0,9) + "...");
		} else {
			return String.format("%-" + cellWidth + "s", text);			
		}
	}

	public void invalidCommand(){
		System.out.println("*** Invalid Command. ***");
	}

}
