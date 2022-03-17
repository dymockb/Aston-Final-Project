package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
//import java.sql.SQLException;
import java.io.File;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonGenerationException;

import util.DBConnector;
import util.Parser;
import util.ScreenPrinter;


public class Engine {

	private ObjectMapper mapper; 
	private HashMap<String, String> sqlQueries;
	//http://tutorials.jenkov.com/java-json/jackson-objectmapper.html
	
	private String sqlString;
	private String userInput;
	private ScreenPrinter printer;
	
	private DBConnector db;
	private ResultSet rs;

	public static void main(String[] args) throws FileNotFoundException {
		
		Engine engine = new Engine();

		//Run from file:
		//engine.openForBusiness("inputFromFile");

		//Run with UserInput:
		engine.openForBusiness("userInput");

	}

	public Engine() throws FileNotFoundException {

		db = new DBConnector();
		printer = new ScreenPrinter();

		mapper = new ObjectMapper();
		File file = new File("./jsonFiles/theatre-queries.json");
		try
		{
		  
		  sqlQueries = mapper.readValue(file, new TypeReference<HashMap<String, String>>(){});
		   
		  //Print JSON output
		  //System.out.println("sql hashmap:");
		  //System.out.println(sqlQueries);
		} 
		catch (JsonGenerationException e) {
		  e.printStackTrace();
		} catch (JsonMappingException e) {
		  e.printStackTrace();
		} catch (IOException e) {
		  e.printStackTrace();
		}

		

	}

	public void openForBusiness(String inputType) throws FileNotFoundException {

		db.connect();
				
		Parser createDbParser = new Parser();
		createDbParser.addFile("./ZuleyhaSQL/create-theatre.sql");

		boolean sqlrunning = true;
		while (sqlrunning){
			sqlString = createDbParser.getSQL();
		
			if(sqlString.equals("END")){
				sqlrunning = false;
			} else {
				db.runQuery(sqlString);
			}
					
		}

		createDbParser.closeScanner();
		
		Parser addTableDataParser = new Parser();

		addTableDataParser.addFile("./csvFiles/TypeOfShow.csv");
		db.runQuery(addTableDataParser.createSqlDataFromCSV("TypeOfShow"));

		addTableDataParser.addFile("./csvFiles/ShowDetail.csv");
		db.runQuery(addTableDataParser.createSqlDataFromCSV("ShowDetail"));

		addTableDataParser.closeScanner();

		Parser userInputParser;

		if(inputType.equals("inputFromFile")){
			userInputParser = new Parser();
			userInputParser.addFile("./txtFiles/userInput.txt");
		} else {
			userInputParser = new Parser();
		}	

		Boolean userActive = true;

		while(userActive){

			userInput = userInputParser.getInput("commands-list");

			if (userInput.equals("q")) {
				System.out.println("quit.");
				userActive = false;
			} else if (userInput.equals("b")){
				rs = db.runQuery(sqlQueries.get("browse-shows"));
				printer.browseTable(rs, 5);
			} else if (userInput.equals("a")) {
				rs = db.runQuery(sqlQueries.get("select-all-shows"));
				printer.printResults(rs);
			} else {
				System.out.println("Invalid command");				
			}

		}

		userInputParser.closeScanner();

		db.close();

	}

}
