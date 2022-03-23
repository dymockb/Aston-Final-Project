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

    //private ScreenPrinter printer;

    /**
     * Methods to interact with SQL query results .    
     * */ 

    public ResultProcessor() {

        //printer = new ScreenPrinter();

    }


    public int getNumberOfRows(ResultSet rs){
		
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

	public String getShowByRowNumber(ResultSet rs, int selectedRow){

		String output = "";
		try {
		
			rs.beforeFirst();
			while(rs.next()){
				if (rs.getRow() == selectedRow){
					break;
				}

			}

			output = rs.getString(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;

	}


}