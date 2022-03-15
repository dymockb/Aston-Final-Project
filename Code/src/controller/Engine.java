package controller;

import java.io.FileNotFoundException;
import java.sql.ResultSet;

//import com.mysql.cj.xdevapi.Result;

import util.DBConnector;
import util.Parser;

public class Engine {

	

	public static void main(String[] args) throws FileNotFoundException {
		DBConnector db = new DBConnector();
		db.connect();
		
		Parser parser;		
		parser = new Parser("1-create-database.sql");

		ResultSet rs;

		boolean sqlrunning = true;
		while (sqlrunning){
			String sql = parser.getSQL();
			//System.out.println("sql: " + sql);
			if(sql.equals("END")){
				sqlrunning = false;
			} else {
				db.runQuery(sql);
			} 
					
		}

		parser = new Parser("3-populate-data.sql");

		sqlrunning = true;
		while (sqlrunning){
			String sql = parser.getSQL();
			if(sql.equals("END")){
				sqlrunning = false;
			} else {
				db.runQuery(sql);
			} 
			
		}

		parser = new Parser("4-run-queries.sql");

		sqlrunning = true;
		rs = null;
		while (sqlrunning){
			String sql = parser.getSQL();
			//System.out.println("sql: " + sql);
			if(sql.equals("END")){
				sqlrunning = false;
			} else {
				rs = db.runQuery(sql);
				if(rs == null){
					System.out.println("Nothing to print.");
				} else {
					db.printResults(rs);									
				}
			} 	
		}

		db.close();

	}

}
