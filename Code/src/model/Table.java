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

    public abstract void browseTable(int startingRow, int rowsToDisplay);

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
