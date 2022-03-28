package model;

public class Ticket {

    private double price;
    private int seatID;
    private int performanceID;
    private int customerID;
    private String performanceName;
    private String showName;
    private String performanceTime;
    private String performanceTimeType;

    public Ticket(double price, int seatID, int performanceID, int customerID, String showName, String performanceTime, String performanceTimeType){
        this.price = price;
        this.seatID = seatID;
        this.performanceID = performanceID;
        this.customerID = customerID;
        this.showName = showName;
        this.performanceTime = performanceTime;
        this.performanceTimeType = performanceTimeType;
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

    public int getPerformanceID(){
        return performanceID;
    }

    public int getCustomerID(){
        return customerID;
    }

    
}
