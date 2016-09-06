package huji.ac.il.parkme;

/**
 * Created by rotemoh on 06/09/2016.
 */
public class Parking {

    public String address;
    public String centralLocation;
    public String city;
    public String ownerID;
    public String cost;

    public Parking(String address, String centralLocation,
                   String city, String ownerID, String cost){
        this.address = address;
        this.centralLocation = centralLocation;
        this.city = city;
        this.ownerID = ownerID;
        this.cost = cost;
    }
}
