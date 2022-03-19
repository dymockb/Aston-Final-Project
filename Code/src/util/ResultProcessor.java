package util;

//import java.sql.ResultSet;
import java.sql.*;
/**
 * 
 * This responds to the user input 
 *  
 * @author  
 * @version 1.0
 */
public class ResultProcessor
{

    private ScreenPrinter printer;
    /**
     * Methods to interact with SQL query results .    
     * */ 
    public ResultProcessor() {

        printer = new ScreenPrinter();

    }

    public void browseTable(ResultSet rs) {

        try {

			ResultSetMetaData rsmd = rs.getMetaData();
			
			int noOfRows = getNumberOfRows(rs);
			int noOfCols = rsmd.getColumnCount();
			int startingRow = 1;
			int rowsToDisplay = 5;

			printer.printTableHeading(noOfRows, startingRow, rowsToDisplay);

			printer.printColumnTitles(rsmd);

			printer.printTableData(rs, startingRow, rowsToDisplay, noOfCols);

			/*
			while (rs.next() && startingRow <= rowsToDisplay){

				for (int i = 1; i <= noOfCols; i ++){
					System.out.print("| ");
					System.out.print(printer.createSubString(rs.getString(i)));
				}
				System.out.println(" |");
				//this variabls is wrong / out of date/ unused:
                currentRow++;	
			}
			*/

			//System.out.println("+-----------------");
		} catch (SQLException e) {
			e.printStackTrace();
		}

    }

    private int getNumberOfRows(ResultSet rs){
		
		int output = 0;
		try {
			rs.last();
			output = rs.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return output;

	}


}
