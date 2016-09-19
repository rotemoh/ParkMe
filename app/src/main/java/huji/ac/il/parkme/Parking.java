package huji.ac.il.parkme;

import java.util.Date;

/**
 * Created by rotemoh on 06/09/2016.
 */
public class Parking {

    public String address;
    public double latitude;
    public double longitude;
    public Date startDate;
    public Date endDate;
    public String ownerID;
    public String cost;


    public Parking(String address, double latitude, double longitude, Date startDate,
                   Date endDate, String ownerID, String cost){
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ownerID = ownerID;
        this.cost = cost;
    }
}
