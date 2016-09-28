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
    public String myPublicParking;

    public UserProfile() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserProfile(String email, String fullName, String phone) {
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
    }
}
