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

    public void browseTable(ResultSet rs, int noOfRows) {

        try {

			ResultSetMetaData rsmd = rs.getMetaData();
			printer.printColumnTitles(rsmd);
			
			int cols = rsmd.getColumnCount();

            int currentRow = 1;

			while (rs.next() && currentRow <= noOfRows){

				for (int i = 1; i <= cols; i ++){
					System.out.print("| ");
					System.out.print(printer.createSubString(rs.getString(i)));
				}
				System.out.println(" |");
                currentRow++;	
			}

			System.out.println("+-----------------");
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
