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
			int rowsToDisplay = 7;

			printer.printTableHeading(noOfRows, startingRow, rowsToDisplay);

			printer.printColumnTitles(rsmd);

			printer.printTableData(rs, startingRow, rowsToDisplay, noOfCols);

		} catch (SQLException e) {
			e.printStackTrace();
		}

    }

    private int getNumberOfRows(ResultSet rs){
		
		int output = 0;
		try {
			rs.last();
			output = rs.getRow();
			rs.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return output;

	}


}
