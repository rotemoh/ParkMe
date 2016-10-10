package huji.ac.il.parkme;

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
    public String startTimeP;
    public String endTimeP;
    public String key;
    public String isAvailable;
    public Parking(){
    }

    public Parking(String address, double latitude, double longitude, long startDate,
                   long endDate, String ownerID, String cost, String startTimeP,
                   String endTimeP, String key, String isAvailable){
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ownerID = ownerID;
        this.cost = cost;
        this.startTimeP = startTimeP;
        this.endTimeP = endTimeP;
        this.key = key;
        this.isAvailable = isAvailable;
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

    public String getStartTimeP() { return startTimeP; }

    public String getEndTimeP() { return endTimeP; }

    public String getKey() { return key; }

    public String getIsAvailable() { return isAvailable; }
}
