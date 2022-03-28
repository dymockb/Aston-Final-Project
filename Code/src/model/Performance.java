package model;
import java.util.HashMap;
import java.util.ArrayList;
import java.sql.*;
import superclass.SearchDB;

public class Performance {

    private HashMap<String, String> performanceDetails;
    private ResultSetMetaData rsmd;
    private User user;
    private ArrayList<Integer> stallsSeatsAvailable;
    private ArrayList<Integer> circleSeatsAvailable;

    public Performance(ResultSet rs, User user){

        this.user = user;

        performanceDetails = new HashMap<String, String>();

        try {
            rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            rs.beforeFirst();
            rs.next();
            for (int i = 1; i <= cols; i ++){
                //System.out.println("col " +  i  + " " + rsmd.getColumnName(i) + rs.getString(i));
                performanceDetails.put(rsmd.getColumnName(i), rs.getString(i));
            }
            


        } catch (SQLException e) {
            e.printStackTrace();
        }

        //setNumberOfSeatsAvailable();
        //System.out.println("Show hashmap: ");
        //System.out.println(performanceDetails);

    }

    public HashMap<String, String> getPerformanceDetails(){
        return performanceDetails;
    } 

    public void setNumberOfSeatsAvailable(){

        stallsSeatsAvailable = new ArrayList<Integer>();
        circleSeatsAvailable = new ArrayList<Integer>();

        String performanceID = performanceDetails.get("ID");

        //check stalls seats
        for(int i = 1; i <= 120; i++){

            String stringTemplate = user.getSqlQueries().get("check-seat-available");
            stringTemplate = stringTemplate.replace("performance-id-from-java", performanceID);
            stringTemplate = stringTemplate.replace("seat-id-from-java", String.valueOf(i));
            stringTemplate += ";";
            
            //System.out.println(stringTemplate);
            SearchDB checkSeatAvailability = new SearchDB(stringTemplate, user.getDBConnector());
            ResultSet rs = checkSeatAvailability.runSearch();
            
            try {
                rs.beforeFirst();
                rs.next();
                int seatAvailable = rs.getInt(1);
                //System.out.println("seat: " + i + "rs.result: " + seatAvailable);
                if (seatAvailable == 0){
                    stallsSeatsAvailable.add(i);
                } 
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //check circle seats
        for(int i = 121; i <= 200; i++){

            String stringTemplate = user.getSqlQueries().get("check-seat-available");
            stringTemplate = stringTemplate.replace("performance-id-from-java", performanceID);
            stringTemplate = stringTemplate.replace("seat-id-from-java", String.valueOf(i));
            stringTemplate += ";";
            
            //System.out.println(stringTemplate);
            SearchDB checkSeatAvailability = new SearchDB(stringTemplate, user.getDBConnector());
            ResultSet rs = checkSeatAvailability.runSearch();
            
            try {
                rs.beforeFirst();
                rs.next();
                int seatAvailable = rs.getInt(1);
                //System.out.println("seat: " + i + "rs.result: " + seatAvailable);
                if (seatAvailable == 0){
                    circleSeatsAvailable.add(i);
                } 
    
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        
    }

    public int getTotalNumberOfAvailableSeats(){
        setNumberOfSeatsAvailable();
        return stallsSeatsAvailable.size() + circleSeatsAvailable.size();
    }

    public ArrayList<Integer> getAvailableStallsSeats(){
        setNumberOfSeatsAvailable();
        return stallsSeatsAvailable;
    }
    
    public ArrayList<Integer> getAvailableCircleSeats(){
        setNumberOfSeatsAvailable();
        return circleSeatsAvailable;
    }
}
