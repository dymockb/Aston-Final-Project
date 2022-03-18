package util;

import java.sql.*;

//import com.mysql.cj.protocol.Resultset;

public class ScreenPrinter {

	public ScreenPrinter() {
		
	}

	public void browseTable(ResultSet rs, int rowsPerScreen) {
		
		int startingRow = 1;
		System.out.println("Showing results " + startingRow + " to " + rowsPerScreen);

		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			printColumnTitles(rsmd);
			
			int cols = rsmd.getColumnCount();
			int lastRowOfCurrentTable = rowsPerScreen;

			while (rs.next() && startingRow <= lastRowOfCurrentTable) {
				for (int i = 1; i <= cols; i ++){
					System.out.print("| ");
					System.out.print(createSubString(rs.getString(i)));
				}
				System.out.println(" |");
				startingRow ++;	
			}

			lastRowOfCurrentTable += rowsPerScreen;

			Parser userInputParser = new Parser();
			String userInput = userInputParser.getInput("browse-table");

			if(userInput.equals("f")){
				System.out.println("Showing results " + rowsPerScreen + " to " + lastRowOfCurrentTable);
				printColumnTitles(rsmd);
			}

			System.out.println("+-----------------");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void printResults(ResultSet rs) {
		try {
			//get the headers and output them
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfRows = getNumberOfRows(rs);
			int numberOfCols = rsmd.getColumnCount();

			System.out.println("Showing results from " + 0 + " to " + numberOfRows);

			printColumnTitles(rsmd);

			printTableData(rs, 0, numberOfRows, numberOfCols);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

	private int getNumberOfRows(ResultSet rs){
		
		int output = 0;
		try {
			rs.last();
			output = rs.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return output;

	}

	private void printColumnTitles(ResultSetMetaData rsmd){

		try {
			int cols = rsmd.getColumnCount();
			System.out.println("+-----------------");
			for (int i = 1; i <= cols; i ++){
				System.out.print("| ");
				System.out.print(createSubString(rsmd.getColumnName(i)));
			}
			System.out.print(" |");
			System.out.println("\n+-----------------");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void printTableData(ResultSet rs, int startingRow, int numberOfRows, int numberOfCols){

		try {

			rs.beforeFirst();;
			while (rs.next()) {
				for (int i = 1; i <= numberOfCols; i ++){
					System.out.print("| ");
					System.out.print(createSubString(rs.getString(i)));
				}
				System.out.println(" |");	
			}	

			System.out.println("+-----------------");

		} catch (SQLException e) {

			e.printStackTrace();

		}
	}

	private String createSubString(String text){
		if (text == null){
			return String.format("%-12s", "");
		} else if (text.length() >= 9){
			return String.format("%-12s", text.substring(0,9) + "...");
		} else {
			return String.format("%-12s", text);			
		}
	}

}
