package model;

public class Performance {

    private String showName;
    private String typeOfShow;
    //private String language;

    //private String date;
    //private String duration;
    //private Boolean liveMusic;
    //private String performerDetails;

    private double price;

    public Performance(
        String showName,
        String typeOfShow,
//        String language,
//        String date,
//        String duration,
//        Boolean liveMusic,
//        String performerDetails,
        double price )
        {
        this.showName = showName;
        this.typeOfShow = typeOfShow;
//        this.language = language;
        this.price = price;
    }

    
    
}
