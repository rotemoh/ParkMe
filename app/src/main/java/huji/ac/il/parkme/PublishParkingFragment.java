package huji.ac.il.parkme;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

/**
 * Created by Adi on 21/08/2016.
 */
public class PublishParkingFragment extends Fragment {
    public EditText costInput;
    public Button resetBtn, updateBtn, seeMapBtn;
    public EditText addressIn, commentsIn, sDateIn, eDateIn;
    public CheckBox approve;
    public FirebaseAuth PPauth;
    public DatabaseReference PPdatabase;
    public Geocoder geocoder;
    public List<Address> addresses;
    public TimePicker startTimePickerP, endTimePickerP;
    public String parkingStartDate, parkingEndDate;
    public SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    View rootView;
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.publish_park_layout, container, false);
        addressIn = (EditText)rootView.findViewById(R.id.address_input);
        commentsIn = (EditText)rootView.findViewById(R.id.comments_input);
        sDateIn = (EditText)rootView.findViewById(R.id.publish_start_date_input);
        eDateIn = (EditText)rootView.findViewById(R.id.publish_end_date_input);
        PPdatabase = FirebaseDatabase.getInstance().getReference();
        PPauth = getInstance();
        approve = (CheckBox)rootView.findViewById(R.id.approve_check);
        geocoder = new Geocoder(container.getContext(), Locale.getDefault());
        costInput = (EditText) rootView.findViewById(R.id.cost_input);
        startTimePickerP = (TimePicker)rootView.findViewById(R.id.startTimePickerP);
        endTimePickerP = (TimePicker)rootView.findViewById(R.id.endTimePickerP);
        startTimePickerP.setIs24HourView(true);
        endTimePickerP.setIs24HourView(true);

        seeMapBtn = (Button)rootView.findViewById(R.id.see_map_btn);
        seeMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if address is legal
                try {
                    addresses = geocoder.getFromLocationName(addressIn.getText().toString(), 1);
                    if (addresses.size() < 1) {
                        throw new Exception();
                    } else {
                        Intent intent = new Intent(getContext(), seeMapActivity.class);
                        intent.putExtra("address", addressIn.getText().toString());
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    addressIn.setError("Illegal address");
                }
            }
        });

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

                try {
                    startDate = dateFormat.parse(startDateStr);
                    endDate = dateFormat.parse(endDateStr);
//                    startDates.add(startDate.getTime());
//                          endDates.add(endDate.getTime());
                    //todo-  check (startDate.after(new Date()))
//                    if (startDate.after(endDate))
//                    {
//                        throw new Exception();
//                    }
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
                String key = PPdatabase.child("Parking").push().getKey();

                if (Build.VERSION.SDK_INT >= 23 ){
                    parkingStartDate = startTimePickerP.getHour() + ":" + startTimePickerP.getMinute();
                    parkingEndDate = endTimePickerP.getHour() + ":" + endTimePickerP.getMinute();
                }else{
                    parkingStartDate = startTimePickerP.getCurrentHour() + ":" + startTimePickerP.getCurrentMinute();
                    parkingEndDate = endTimePickerP.getCurrentHour() + ":" + endTimePickerP.getCurrentMinute();
                }

                Parking addParking = new Parking(addressIn.getText().toString(),
                        addresses.get(0).getLatitude(), addresses.get(0).getLongitude(), startDate.getTime(),
                        endDate.getTime(),PPauth.getCurrentUser().getUid(), "" + costInput.getText().toString());
                PPdatabase.child("Parking").child(key).setValue(addParking);
                PPdatabase.child("Users").child(PPauth.getCurrentUser().getUid()).child("myPublicParking").push().setValue(key);
                Toast.makeText(getActivity(), "Parking published successfully",
                        Toast.LENGTH_SHORT).show();
                Intent homeIntent = new Intent(getActivity(), MainActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(homeIntent);
            }
        });
        return rootView;
    }


    public boolean isValidDate(String inDate) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(inDate.trim());
            Date today = new Date();
            today = setCalendarObj(today, 0, 0, 0, 0);

            if(dateFormat.parse(inDate.trim()).before(today) &&
                    !dateFormat.parse(inDate.trim()).equals(today)){
                throw new Exception();
            }
        } catch (ParseException pe) {
            return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
public Date setCalendarObj(Date date, int h, int m, int s, int ms) throws ParseException {

    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, h);
    cal.set(Calendar.MINUTE, m);
    cal.set(Calendar.SECOND, s);
    cal.set(Calendar.MILLISECOND, ms);

    return cal.getTime();
}
    public boolean isValidTime(String sDate, String eDate, TimePicker startTimePickerP, TimePicker endTimePickerP) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        int startHour = startTimePickerP.getCurrentHour();
        int startMinute = startTimePickerP.getCurrentMinute();
        int endHour = endTimePickerP.getCurrentHour();
        int endMinute = endTimePickerP.getCurrentMinute();
        try {
            Date sd = dateFormat.parse(sDate.trim());
            Date ed = dateFormat.parse(eDate.trim());

            sd = setCalendarObj(sd, startHour, startMinute, 0, 0);
            ed = setCalendarObj(ed, endHour, endMinute, 0, 0);

            if(ed.before(sd) || ed.equals(sd)){
                throw new Exception();
            }
        } catch (ParseException e) {
            return false;
        } catch (Exception e) {
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
        try {
            if (endDateStr.equals("") || !isValidDate(endDateStr) ||
                    dateFormat.parse(endDateStr.trim()).before(dateFormat.parse(startDateStr.trim()))) {
                eDateIn.setError("Incorrect or missing date");
                valid = false;
            } else {
                eDateIn.setError(null);
            }
        } catch (ParseException e) {
            valid = false;
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
        if(!isValidTime(startDateStr, endDateStr, startTimePickerP, endTimePickerP)){
            Toast.makeText(getActivity(), "Illegal time scheduling", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }
}