package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;

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

	private ScreenPrinter printer;
	private DBConnector db;
	private UserInterface userInterface;
	private Parser inputParser;

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
		inputParser = new Parser();

		mapper = new ObjectMapper();
		File file = new File("./database-files/json-files/theatre-queries.json");
		try
		{
		  
		  sqlQueries = mapper.readValue(file, new TypeReference<HashMap<String, String>>(){});
		   
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

		if(inputType.equals("inputFromFile")){
			inputParser.addFile("./txt-files/user-input.txt");
		} 

		userInterface.setInputParser(inputParser);

		Boolean userActive = true;
		
		while(userActive){

			userActive = userInterface.mainMenu();

		}

		inputParser.closeScanner();
		db.close();
	}

	private void createdb() throws FileNotFoundException {

		Parser createDbParser = new Parser();
		createDbParser.addFile("./database-files/zuleyha-db-files/version2/testdb.sql");

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

		addTableDataParser.addFile("./database-files/zuleyha-db-files/version2/csv-files/LiveMusic.csv");
		db.runQuery(addTableDataParser.createSqlDataFromCSV("LiveMusic"));

		addTableDataParser.addFile("./database-files/zuleyha-db-files/version2/csv-files/ShowLanguage.csv");
		db.runQuery(addTableDataParser.createSqlDataFromCSV("ShowLanguage"));

		addTableDataParser.addFile("./database-files/zuleyha-db-files/version2/csv-files/TypeOfShow.csv");
		db.runQuery(addTableDataParser.createSqlDataFromCSV("TypeOfShow"));

		addTableDataParser.addFile("./database-files/zuleyha-db-files/version2/csv-files/PriceBand.csv");
		db.runQuery(addTableDataParser.createSqlDataFromCSV("PriceBand"));

		addTableDataParser.addFile("./database-files/zuleyha-db-files/version2/csv-files/ShowDetail.csv");
		db.runQuery(addTableDataParser.createSqlDataFromCSV("ShowDetail"));



		addTableDataParser.closeScanner();

	}

}
