package model;

public class Ticket {

    private double price;
    private int seatID;
    private int performanceID;
    private String performanceName;
    private String showName;
    private int showID;
    private String performanceTime;
    private String performanceTimeType;

    public Ticket(double price, int seatID, int performanceID, String performanceName, String showName, int showID, String performanceTime, String performanceTimeType){
        this.price = price;
        this.seatID = seatID;
        this.performanceID = performanceID;
        this.performanceName = performanceName;
        this.showName = showName;
        this.showID = showID;
        this.performanceTime = performanceTime;
        this.performanceTimeType = performanceTimeType;

    }

    public Ticket(double price, int seatID){
        this.price = price;
        this.seatID = seatID;
    }

    public int getTicketSeatID(){
        return seatID;
    }

    public double getTicketPrice(){
        return price;
    }
    
}
