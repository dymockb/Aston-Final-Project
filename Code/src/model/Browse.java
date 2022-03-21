package model;

import util.DBConnector;
import java.sql.*;

public class Browse extends Explore {

    private String sqlQuery;
    private ResultSet rs;


    public Browse(DBConnector db, String sqlQuery){

        super(db); 
        this.sqlQuery = sqlQuery;

    }

    public void fetchData(){

        rs = db.runQuery(sqlQuery);

    }

    public ResultSet returnResults(){
        return rs;
    }
    
}
