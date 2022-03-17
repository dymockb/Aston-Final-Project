package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
		//engine.openForBusiness("inputFromFile");
		engine.openForBusiness("userInput");

	}

	public Engine() throws FileNotFoundException {

		db = new DBConnector();
		printer = new ScreenPrinter();

		/*
		searchQueries = new ArrayList<String>();

		Parser sqlQueriesParser = new Parser();
		sqlQueriesParser.addFile("./ZuleyhaSQL/run-theatre-queries.sql", "run-theatre-queries");

		Boolean sqlrunning = true;
		rs = null;
		while (sqlrunning){
			sqlString = sqlQueriesParser.getSQL();
			if(sqlString.equals("END")){
				sqlrunning = false;
			} else {
				searchQueries.add(sqlString);
			public static void main(String[] args) throws FileNotFoundException {
		
		Engine engine = new Engine();
		engine.openForBusiness();

	}	} 	
		}

		sqlQueriesParser.closeScanner();

		*/

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
				printer.printResults(rs);
				//if(rs == null){
				//	System.out.println("Nothing to print.");
				//} else {
				//	db.printResults(rs);									
				//}
			} else if (userInput.equals("a")) {
				rs = db.runQuery(sqlQueries.get("select-all-shows"));
				printer.printResults(rs);
				//if(rs == null){
			    //System.out.println("Nothing to print.");
				//} else {
				//	db.printResults(rs);									
				//}
			} else {
				System.out.println("Invalid command");				
			}

		}

		userInputParser.closeScanner();

		db.close();

	}

	/*
	private void printSqlResult(ResultSet result){
		if(result == null){
			System.out.println("Nothing to print.");
		} else {
	
			try {
				
				ResultSetMetaData rsmd = result.getMetaData();
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
			
		}        inputLine
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
	
	*/



}
