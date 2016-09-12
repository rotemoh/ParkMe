package huji.ac.il.parkme;

/**
 * Created by rotemoh on 06/09/2016.
 */
public class Parking {

    public String address;
    public double latitude;
    public double longitude;
    public String centralLocation;
    public String city;
    public String ownerID;
    public String cost;

    public Parking(String address, double latitude, double longitude, String centralLocation,
                   String city, String ownerID, String cost){
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.centralLocation = centralLocation;
        this.city = city;
        this.ownerID = ownerID;
        this.cost = cost;
    }
}
