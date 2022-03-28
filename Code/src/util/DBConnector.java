package util;

import java.sql.*;
import java.util.ArrayList;


public class DBConnector {
	private Connection conn;
	private Parser parser;

	public DBConnector() {
		conn = null;
		parser = new Parser();
	}

	public void connect() {
		try {

			ArrayList<Boolean> switches = new ArrayList<Boolean>();
			String username = parser.getInput("username", "database", switches);
			String password = parser.getInput("password", "database", switches);
			
			//String username = "pi";
			//String password = "raspberry";
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

	public ResultSet runQuery(String sql) {
		PreparedStatement pst = null;
		try {
			//System.out.println(sql);
			pst = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_SENSITIVE, // allows us to move forward and back in the ResultSet
                    ResultSet.CONCUR_UPDATABLE);
			pst.execute();
			
			ResultSet results = pst.getResultSet();
			/* 
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
			*/
			return results;
		} catch (SQLException e) {
			System.out.println(sql + "\n failed to run.");
			e.printStackTrace();
			return null;
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
