package huji.ac.il.parkme;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

/**
 * Created by Adi on 21/08/2016.
 */
public class PublishParkingFragment extends Fragment {
    public EditText costInput;
    public Button resetBtn, updateBtn;
    public EditText addressIn, commentsIn, sDateIn, eDateIn;
    public CheckBox approve;
    public FirebaseAuth PPauth;
    public DatabaseReference PPdatabase;
    public Geocoder geocoder;
    public List<Address> addresses;
    public TimePicker startTimePicker;
    public TimePicker endTimePicker;

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.publish_park_layout, container, false);
        addressIn = (EditText)rootView.findViewById(R.id.address_input);
        commentsIn = (EditText)rootView.findViewById(R.id.comments_input);
        sDateIn = (EditText)rootView.findViewById(R.id.publish_start_date_input);
        eDateIn = (EditText)rootView.findViewById(R.id.publish_end_date_input);
        PPdatabase = FirebaseDatabase.getInstance().getReference();
        PPauth = getInstance();
        approve = (CheckBox)rootView.findViewById(R.id.approve_check);
        geocoder = new Geocoder(container.getContext(), Locale.getDefault());
        costInput = (EditText) rootView.findViewById(R.id.cost_input);
        startTimePicker = (TimePicker)rootView.findViewById(R.id.startTimePicker);
        endTimePicker = (TimePicker)rootView.findViewById(R.id.endTimePicker);


        resetBtn = (Button)rootView.findViewById(R.id.reset_btn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressIn.setText("");
                commentsIn.setText("");
                sDateIn.setText("");
                eDateIn.setText("");
                approve.setChecked(false);
                costInput.setText("");
            }
        });

        updateBtn = (Button)rootView.findViewById(R.id.update_btn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()) {
                    return;
                }

                //checking the dates scheduling
                String startDateStr = sDateIn.getText().toString();
                Date startDate;
                String endDateStr = eDateIn.getText().toString();
                Date endDate;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    startDate = dateFormat.parse(startDateStr);
                    endDate = dateFormat.parse(endDateStr);

                    //todo-  check (startDate.after(new Date()))
                    if (startDate.after(endDate))
                    {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Invalid date scheduling",
                            Toast.LENGTH_SHORT).show();
                    return;
                }


//                LatLng point;
//                point = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
//                String key = PPdatabase.child("Parking").push().getKey();
//                Parking addParking = new Parking(addressIn.getText().toString(), point.latitude, point.longitude, "Azrieli",
//                        "Tel Aviv",PPauth.getCurrentUser().getUid(), "" + numberpicker.getValue());
                long addToStartDate = TimeUnit.MINUTES.toMillis(startTimePicker.getCurrentMinute()) + TimeUnit.HOURS.toMillis(startTimePicker.getCurrentHour());
                long addToEndDate = TimeUnit.MINUTES.toMillis(endTimePicker.getMinute()) + TimeUnit.HOURS.toMillis(endTimePicker.getHour());

                String key = PPdatabase.child("Parking").push().getKey();
                Parking addParking = new Parking(addressIn.getText().toString(),
                        addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), startDate.getTime() + addToStartDate,
                        endDate.getTime() + addToEndDate,PPauth.getCurrentUser().getUid(), "" + costInput.getText().toString());
                PPdatabase.child("Parking").child(key).setValue(addParking);
                Toast.makeText(getActivity(), "Parking published successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }


    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    private boolean validateForm() {
        boolean valid = true;
        String startDateStr = sDateIn.getText().toString();
        String endDateStr = eDateIn.getText().toString();

        if (startDateStr.equals("") || !isValidDate(startDateStr)) {
            sDateIn.setError("Incorrect or missing date");
            valid = false;
        } else {
            sDateIn.setError(null);
        }

        if (endDateStr.equals("") || !isValidDate(endDateStr)) {
            eDateIn.setError("Incorrect or missing date");
            valid = false;
        } else {
            eDateIn.setError(null);
        }

        if (!approve.isChecked()) {
            approve.setError("Required.");
            valid = false;
        } else {
            approve.setError(null);
        }

        //check if address is legal
        try {
            addresses = geocoder.getFromLocationName(addressIn.getText().toString(), 1);
            if (addresses.size() < 1) {
                throw new Exception();
            }
        } catch (Exception e) {
            addressIn.setError("Illegal address");
            valid = false;
        }
        return valid;
    }
}