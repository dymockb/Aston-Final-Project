package model;

import java.util.HashMap;
import java.sql.*;


public class Show {

    private HashMap<String, String> showDetails;
    private ResultSetMetaData rsmd;

    public Show(ResultSet rs){

        showDetails = new HashMap<String, String>();

        try {
            rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            rs.beforeFirst();
            rs.next();
            for (int i = 1; i <= cols; i ++){
                //System.out.println("col " +  i  + " " + rsmd.getColumnName(i) + rs.getString(i));
                showDetails.put(rsmd.getColumnName(i), rs.getString(i));
            }
            


        } catch (SQLException e) {
            e.printStackTrace();
        }

        //System.out.println("Show hashmap: ");
        //System.out.println(showDetails);

    }

    public HashMap<String, String> getShowDetails(){
        return showDetails;
    }

    
}
