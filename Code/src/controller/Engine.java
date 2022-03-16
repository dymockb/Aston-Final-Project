package controller;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.DBConnector;
import util.Parser;


public class Engine {

	String createDatabaseFile;
	String populateDataBaseFile;
	String runQueriesFile;

	ArrayList<String> searchQueries;
	
	String sqlString;
	String userInput;
	
	DBConnector db;
	ResultSet rs;
	Parser parser;

	public Engine() throws FileNotFoundException {

		db = new DBConnector();
		searchQueries = new ArrayList<String>();

		parser = new Parser("./ZuleyhaSQL/run-theatre-queries.sql");

		Boolean sqlrunning = true;
		rs = null;
		while (sqlrunning){
			sqlString = parser.getSQL();
			//System.out.println("sql: " + sql);
			if(sqlString.equals("END")){
				sqlrunning = false;
			} else {
				searchQueries.add(sqlString);
			} 	
		}

	}

	public void openForBusiness() throws FileNotFoundException {

		db.connect();
				
		parser = new Parser("./ZuleyhaSQL/create-theatre.sql");

		boolean sqlrunning = true;
		while (sqlrunning){
			sqlString = parser.getSQL();
			//System.out.println("sql: " + sql);
			if(sqlString.equals("END")){
				sqlrunning = false;
			} else {
				db.runQuery(sqlString);
			} 
					
		}

		

		parser = new Parser("./ZuleyhaSQL/theatre-add-data.sql");

		sqlrunning = true;
		while (sqlrunning){
			sqlString = parser.getSQL();
			if(sqlString.equals("END")){
				sqlrunning = false;
			} else {
				db.runQuery(sqlString);
			} 
			
		}

		

		parser = new Parser();

		Boolean userActive = true;

		while(userActive){

			userInput = parser.getInput("commands-list");

			if (userInput.equals("q")) {
				System.out.println("quit.");
				userActive = false;
			} else if (userInput.equals("s")){
				rs = db.runQuery(searchQueries.get(0));
				if(rs == null){
					System.out.println("Nothing to print.");
				} else {
					db.printResults(rs);									
				}
			} else if (userInput.equals("a")) {
				rs = db.runQuery(searchQueries.get(0));
				if(rs == null){
					System.out.println("Nothing to print.");
				} else {
					db.printResults(rs);									
				}
			} else {
				System.out.println("Invalid command");				
			}

		}

		db.close();

	}

	public static void main(String[] args) throws FileNotFoundException {
		
		//DBConnector db = new DBConnector();
		Engine engine = new Engine();
		engine.openForBusiness();

	}

}
