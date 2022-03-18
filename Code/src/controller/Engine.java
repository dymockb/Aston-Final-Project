package controller;

//import java.sql.SQLException;
//import java.sql.ResultSetMetaData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
//import java.sql.ResultSet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonGenerationException;

import util.DBConnector;
import util.Parser;
import util.ScreenPrinter;
import util.UserInterface;

public class Engine {

	private ObjectMapper mapper; 
	private HashMap<String, String> sqlQueries;
	//http://tutorials.jenkov.com/java-json/jackson-objectmapper.html
	
	//private String sqlString;
	//private String userInput;
	private ScreenPrinter printer;
	private DBConnector db;
	private UserInterface userInterface;

	//private ResultSet rs;

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

		userInterface = new UserInterface(db, printer, sqlQueries);

	}

	public void openForBusiness(String inputType) throws FileNotFoundException {

		db.connect();

		createdb();
				
		addTableData();

		Parser userInputParser;

		if(inputType.equals("inputFromFile")){
			userInputParser = new Parser();
			userInputParser.addFile("./txtFiles/userInput.txt");
		} else {
			userInputParser = new Parser();
		}

		userInterface.setInputParser(userInputParser);

		Boolean userActive = true;
		//String userInput;

		while(userActive){

			//userInput = userInputParser.getInput("commands-list");
			userActive = userInterface.mainMenu();

		}

		userInputParser.closeScanner();

		db.close();

	}

	private void createdb() throws FileNotFoundException {

		Parser createDbParser = new Parser();
		createDbParser.addFile("./ZuleyhaSQL/create-theatre.sql");

		boolean sqlrunning = true;
		while (sqlrunning){
			String sqlString = createDbParser.getSQL();
		
			if(sqlString.equals("END")){
				sqlrunning = false;
			} else {
				db.runQuery(sqlString);
			}
					
		}

		createDbParser.closeScanner();

	}

	private void addTableData() throws FileNotFoundException {
				
		Parser addTableDataParser = new Parser();

		addTableDataParser.addFile("./csvFiles/TypeOfShow.csv");
		db.runQuery(addTableDataParser.createSqlDataFromCSV("TypeOfShow"));

		addTableDataParser.addFile("./csvFiles/ShowDetail.csv");
		db.runQuery(addTableDataParser.createSqlDataFromCSV("ShowDetail"));

		addTableDataParser.closeScanner();

	}

}
