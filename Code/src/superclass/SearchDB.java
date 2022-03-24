package superclass;

import java.sql.*;
import util.DBConnector;

public class SearchDB {

    private String searchString;
    private DBConnector db;

    public SearchDB(String searchString, DBConnector db){
        this.searchString = searchString;
        this.db = db;
    }

    //public static SearchDB createSearch(String searchString, DBConnector db){
    //      return new SearchDB(searchString, );
   // }

    public String getSearchString(){
        return searchString;
    }

    public ResultSet runSearch(){
        return db.runQuery(searchString);
    }
    
}
