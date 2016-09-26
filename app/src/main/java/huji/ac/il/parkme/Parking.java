package huji.ac.il.parkme;

import java.util.Date;

/**
 * Created by rotemoh on 06/09/2016.
 */
public class Parking {

    public String address;
    public double latitude;
    public double longitude;
    public long startDate;
    public long endDate;
    public String ownerID;
    public String cost;

    public Parking(){
    }

    public Parking(String address, double latitude, double longitude, long startDate,
                   long endDate, String ownerID, String cost){
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ownerID = ownerID;
        this.cost = cost;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public String getCost() {
        return cost;
    }
}
