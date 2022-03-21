package model;

import java.sql.*;
//import util.ScreenPrinter;

public class PerformanceTable extends Table {

    //private ScreenPrinter printer;

    public PerformanceTable(ResultSet rs){
        super(rs);
    }

    public void browseTable(int startingRow, int rowsToDisplay) {

        try {

			ResultSetMetaData rsmd = rs.getMetaData();
			
			int noOfRows = getNumberOfRows();
			int noOfCols = rsmd.getColumnCount();

			int endingRow = (startingRow + rowsToDisplay) < noOfRows ? startingRow + rowsToDisplay : noOfRows; 	

			printer.printTableHeading(startingRow, endingRow, noOfRows);
			printer.printColumnTitles(rsmd);
			printer.printTableData(rs, startingRow, endingRow, noOfCols);
					
		} catch (SQLException e) {
			e.printStackTrace();
		}

    }

    public void selectRow(){

    }
    
}
