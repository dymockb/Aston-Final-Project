package util;

import java.sql.*;

public class ScreenPrinter {

	public ScreenPrinter() {
		
	}

	public void printResults(ResultSet rs) {
		try {
			//get the headers and output them
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			System.out.println("+-----------------");
			for (int i = 1; i <= cols; i ++){
				System.out.print("| ");
				System.out.print(createSubString(rsmd.getColumnName(i)));
				//System.out.print(String.format("%-10s", rsmd.getColumnName(i)));
			}
			System.out.print(" |");
			System.out.println("\n+-----------------");
			// while there is another row
			while (rs.next()) {
				for (int i = 1; i <= cols; i ++){
					System.out.print("| ");
					System.out.print(createSubString(rs.getString(i)));
					//System.out.print(String.format("%-10s", rs.getString(i)));
				}
				System.out.println(" |");	
			}
			//bottom border of the output
			System.out.println("+-----------------");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public String createSubString(String text){
		if (text == null){
			return String.format("%-12s", "");
		} else if (text.length() >= 9){
			return String.format("%-12s", text.substring(0,9) + "...");
		} else {
			return String.format("%-12s", text);			
		}
	}

}
