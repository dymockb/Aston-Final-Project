package model;

import java.sql.*;
import util.DBConnector;

public abstract class Explore {

    protected DBConnector db;
    
    public Explore(DBConnector db){

        this.db = db;

    }

    public abstract void fetchData();

    public abstract ResultSet returnResults();
    
}
