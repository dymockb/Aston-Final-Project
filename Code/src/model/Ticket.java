package model;

public class Ticket {

    private double price;
    private int seatID;
    private String performanceID;
    private int customerID;
    private String performanceName;
    private String showName;
    private String performanceDate;
    private String performanceTime;
    private String performanceTimeName;

    public Ticket(double price, int seatID, String performanceID, int customerID, String showName, String performanceDate, String performanceTime, String performanceTimeName){
        this.price = price;
        this.seatID = seatID;
        this.performanceID = performanceID;
        this.customerID = customerID;
        this.showName = showName;
        this.performanceDate = performanceDate;
        this.performanceTime = performanceTime;
        this.performanceTimeName = performanceTimeName;
    }

    public Ticket(double price, int seatID){
        this.price = price;
        this.seatID = seatID;
    }

    public int getSeatID(){
        return seatID;
    }

    public double getPrice(){
        return price;
    }

    public String getPerformanceID(){
        return performanceID;
    }

    public int getCustomerID(){
        return customerID;
    }

    
}
