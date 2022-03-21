package model;

import java.sql.*;

public abstract class Table {

    protected ResultSet rs;

    public Table(ResultSet rs){

        this.rs = rs;

    }

    public abstract void browseTable();

    public abstract void selectRow();
    

}
