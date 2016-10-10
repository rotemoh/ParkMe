package huji.ac.il.parkme;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by rotemoh on 04/09/2016.
 */
@IgnoreExtraProperties
public class UserProfile {

    public String email;
    public String fullName;
    public String phone;
    public ArrayList myOrderedParking;
    public ArrayList myPublicParking;

    public UserProfile() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserProfile(String email, String fullName, String phone,
                       ArrayList myOrderedParking, ArrayList myPublicParking) {
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.myOrderedParking = myOrderedParking;
        this.myPublicParking = myPublicParking;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getPhone() {
        return this.phone;
    }

    public ArrayList getMyOrderedParking() {
        return myOrderedParking;
    }

    public ArrayList getMyPublicParking() {
        return myPublicParking;
    }

}
