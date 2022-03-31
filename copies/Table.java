package model;

import java.sql.*;
import util.ScreenPrinter;

public abstract class Table {

    protected ResultSet rs;
    protected ScreenPrinter printer;

    public Table(ResultSet rs){

        this.rs = rs;
        printer = new ScreenPrinter();

    }

    //public abstract void startBrowsing(int startingRow, int rowsToDisplay);

    public void startBrowsing(int startingRow, int rowsToDisplay) {

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

    //protected int getNumberOfRows(ResultSet rs){
    public int getNumberOfRows(){	

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
    
    public abstract void selectRow();


    

}
