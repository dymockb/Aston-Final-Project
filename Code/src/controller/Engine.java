package controller;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
//import ObjectMapper;

//import java.util.HashMap;

import util.DBConnector;
import util.Parser;


public class Engine {

	//private String createDatabaseFile;
	//private String populateDataBaseFile;
	//private String runQueriesFile;

	private ObjectMapper ObjectMapper; 
	//http://tutorials.jenkov.com/java-json/jackson-objectmapper.html

	private ArrayList<String> searchQueries;
	
	private String sqlString;
	private String userInput;
	
	private DBConnector db;
	private ResultSet rs;

	public Engine() throws FileNotFoundException {

		db = new DBConnector();
		searchQueries = new ArrayList<String>();

		Parser sqlQueriesParser = new Parser();
		sqlQueriesParser.addFile("./ZuleyhaSQL/run-theatre-queries.sql", "run-theatre-queries");

		Boolean sqlrunning = true;
		rs = null;
		while (sqlrunning){
			sqlString = sqlQueriesParser.getSQL();
			//System.out.println("sql: " + sql);
			if(sqlString.equals("END")){
				sqlrunning = false;
			} else {
				searchQueries.add(sqlString);
			} 	
		}


		sqlQueriesParser.closeScanner();

	}

	public void openForBusiness() throws FileNotFoundException {

		db.connect();
				
		//Parser createDbParser = new Parser("./ZuleyhaSQL/create-theatre.sql", "create-theatre");
		Parser createDbParser = new Parser();
		createDbParser.addFile("./ZuleyhaSQL/create-theatre.sql", "create-theatre");

		boolean sqlrunning = true;
		while (sqlrunning){
			sqlString = createDbParser.getSQL();
			//System.out.println("sql: " + sql);
			if(sqlString.equals("END")){
				sqlrunning = false;
			} else {
				db.runQuery(sqlString);
			} 
					
		}

		createDbParser.closeScanner();

		//Parser addTableDataParser = new Parser("./ZuleyhaSQL/theatre-add-data.sql", "theatre-add-data");
		
		Parser addTableDataParser = new Parser();
		addTableDataParser.addFile("./csvFiles/TypeOfShow.csv", "TypeOfShow");
		db.runQuery(addTableDataParser.createSqlDataFromCSV());

		addTableDataParser.addFile("./csvFiles/ShowDetail.csv", "ShowDetail");
		db.runQuery(addTableDataParser.createSqlDataFromCSV());

		addTableDataParser.closeScanner();

		/*
		sqlrunning = true;
		while (sqlrunning){
			sqlString = addTableDataParser.getSQL();
			if(sqlString.equals("END")){
				sqlrunning = false;
			} else {
				db.runQuery(sqlString);
			} 
			
		}
		*/

		Parser userInputParser = new Parser();

		Boolean userActive = true;

		while(userActive){

			userInput = userInputParser.getInput("commands-list");

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

		userInputParser.closeScanner();

		db.close();

	}

	public static void main(String[] args) throws FileNotFoundException {
		
		//DBConnector db = new DBConnector();
		Engine engine = new Engine();
		engine.openForBusiness();

	}

}
