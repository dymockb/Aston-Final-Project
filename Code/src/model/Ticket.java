package model;

public class Ticket {

    private double price;
    private int seatID;
    private int performanceID;
    private String performanceName;
    private String showName;
    private String performanceTime;
    private String performanceTimeType;

    public Ticket(double price, int seatID, int performanceID, String showName, String performanceTime, String performanceTimeType){
        this.price = price;
        this.seatID = seatID;
        this.performanceID = performanceID;
        this.showName = showName;
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
