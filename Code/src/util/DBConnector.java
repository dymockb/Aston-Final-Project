package util;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
import java.sql.*;

public class DBConnector {
	private Connection conn;
	private Parser parser;

	public DBConnector() {
		conn = null;
		parser = new Parser();
	}

	public void connect() {
		try {

			String username = parser.getInput("username");
			String password = parser.getInput("password");
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/",username, password);			

		} catch (SQLException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
			return;
		}

		if (conn != null) {
			System.out.println("Connection established.");
		} else {
			System.out.println("Connection null still.");
		}
	}

	/*
	 * 4. Prepare a query statement to run - DONE :) 5. Execute query - DONE
	 */

	public ResultSet runQuery(String sql) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_SENSITIVE, // allows us to move forward and back in the ResultSet
                    ResultSet.CONCUR_UPDATABLE);
			pst.execute();
			
			ResultSet results = pst.getResultSet();
			/* */
			if (results != null) {
				int rowcount = 0;
				if (results.last()) {
					rowcount = results.getRow();
					results.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first
											// element
				}
				//System.out.println(sql + "\n Success.  Result set has " + rowcount + " rows");
			} else {
				//System.out.println(sql + "\n Success.  No results returned");
			}
			/* */
			return results;
		} catch (SQLException e) {
			System.out.println(sql + "\n failed to run.");
			e.printStackTrace();
			return null;
		}

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
	

	public void close() {
		try {
			conn.close();
			System.out.println("Connection closed.");
		} catch (SQLException e) {
			System.out.println("Connection not closed.");
			e.printStackTrace();
		}
	}


}
