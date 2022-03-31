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
//import util.ScreenPrinter;
import model.User;

import superclass.Screen;
import screens.Home;
import screens.Search;
import screens.Shows;
import screens.ViewShow;
import screens.Performances;
import screens.ViewPerformance;
import screens.BasketScreen;

public class Engine {

	private ObjectMapper mapper; 
	private HashMap<String, String> sqlQueries;
	//http://tutorials.jenkov.com/java-json/jackson-objectmapper.html

	//private ScreenPrinter printer;
	private DBConnector db;
	private User user;
	private Parser inputParser;

	public static void main(String[] args) throws FileNotFoundException {
		
		Engine engine = new Engine();

		//Run from file (uncomment the line below, add comments if using UserInput below):
		engine.openForBusiness("inputFromFile"); 

		//Run with UserInput (uncomment the below, add comments if using InputFile above):
		//engine.openForBusiness("userInput");

	}

	public Engine() throws FileNotFoundException {

		db = new DBConnector();
		inputParser = new Parser();

		//File testfile = new File(".");
		//for(String fileNames : testfile.list()) System.out.println(fileNames);

		mapper = new ObjectMapper();
		File file = new File("./database-files/json-files/theatre-queries.json");
		try {	  
		  sqlQueries = mapper.readValue(file, new TypeReference<HashMap<String, String>>(){});
		 }catch (JsonGenerationException e) {
		  e.printStackTrace();
		} catch (JsonMappingException e) {
		  e.printStackTrace();
		} catch (IOException e) {
		  e.printStackTrace();
		}

		user = new User(sqlQueries, db);

	}

	public void openForBusiness(String inputType) throws FileNotFoundException {

		db.connect();
		createdb();				
		addTableData(); 
		selectdb();

		if(inputType.equals("inputFromFile")){
			inputParser.addFile("./txt-files/user-input.txt");
			user.setAutomated(true);
		} 
		
		user.initialiseBasket(inputParser);
		addScreens();
		user.start();
		inputParser.closeScanner();
		db.close();

	}

	private void selectdb(){
		db.runQuery("USE theatreroyal;");
	}

	private void createdb() throws FileNotFoundException {

		Parser createDbParser = new Parser();
		createDbParser.addFile("./database-files/version2/theatre-royal-db.sql");

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

		// Customer, DeliveryType, Gender, LiveMusic, PaymentType, Peformance, PriceBand, Reservation, Seat, ShowDetail, ShowLanguage, TypeOfShow


		addTableDataParser.addFile("./database-files/version2/csv-files/Gender.csv");
		db.runQuery(addTableDataParser.createSqlDataFromCSV("Gender"));

			addTableDataParser.addFile("./database-files/version2/csv-files/Customer.csv");
			db.runQuery(addTableDataParser.createSqlDataFromCSV("Customer"));


		addTableDataParser.addFile("./database-files/version2/csv-files/LiveMusic.csv");
		db.runQuery(addTableDataParser.createSqlDataFromCSV("LiveMusic"));

		addTableDataParser.addFile("./database-files/version2/csv-files/ShowLanguage.csv");
		db.runQuery(addTableDataParser.createSqlDataFromCSV("ShowLanguage"));

		addTableDataParser.addFile("./database-files/version2/csv-files/TypeOfShow.csv");
		db.runQuery(addTableDataParser.createSqlDataFromCSV("TypeOfShow"));

		addTableDataParser.addFile("./database-files/version2/csv-files/PriceBand.csv");
		db.runQuery(addTableDataParser.createSqlDataFromCSV("PriceBand"));

			addTableDataParser.addFile("./database-files/version2/csv-files/ShowDetail.csv");
			db.runQuery(addTableDataParser.createSqlDataFromCSV("ShowDetail"));

				addTableDataParser.addFile("./database-files/version2/csv-files/Performance.csv");
				db.runQuery(addTableDataParser.createSqlDataFromCSV("Performance"));


				addTableDataParser.addFile("./database-files/version2/csv-files/Seat.csv");
				db.runQuery(addTableDataParser.createSqlDataFromCSV("Seat"));

				addTableDataParser.addFile("./database-files/version2/csv-files/DeliveryType.csv");
				db.runQuery(addTableDataParser.createSqlDataFromCSV("DeliveryType"));
		
				addTableDataParser.addFile("./database-files/version2/csv-files/PaymentType.csv");
				db.runQuery(addTableDataParser.createSqlDataFromCSV("PaymentType"));

					addTableDataParser.addFile("./database-files/version2/csv-files/Reservation.csv");
					db.runQuery(addTableDataParser.createSqlDataFromCSV("Reservation"));

		addTableDataParser.closeScanner();
	}

	public void addScreens(){

		Screen newScreen = new Home("home-screen", inputParser, db);
		newScreen.registerUser(user);
		user.addScreen(newScreen);

		newScreen = new Search("search-screen", inputParser, db);
	    newScreen.registerUser(user);
		user.addScreen(newScreen);

		newScreen = new Shows("shows-screen", inputParser, db);
	    newScreen.registerUser(user);
		user.addScreen(newScreen);

		newScreen = new ViewShow("view-show", inputParser, db);
	    newScreen.registerUser(user);
		user.addScreen(newScreen);

		newScreen = new Performances("performances-screen", inputParser, db);
	    newScreen.registerUser(user);
		user.addScreen(newScreen);

		newScreen = new ViewPerformance("view-performance", inputParser, db);
	    newScreen.registerUser(user);
		user.addScreen(newScreen);

		newScreen = new BasketScreen("basket-screen", inputParser, db);
	    newScreen.registerUser(user);
		user.addScreen(newScreen);

	}

}
