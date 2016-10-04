package huji.ac.il.parkme;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Adi on 21/08/2016.
 */
public class FindParkingFragment extends Fragment{
    public EditText addressIn, sDateIn, eDateIn;
    public List<Address> addresses;
    public Geocoder geocoder;
    public CheckBox commit;
    public TimePicker startTimePickerF;
    public TimePicker endTimePickerF;
    public SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public  Button continueBtn, seeMapBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.find_park_layout, container, false);
        addressIn = (EditText)rootView.findViewById(R.id.by_address_input);
        sDateIn = (EditText)rootView.findViewById(R.id.publish_start_date_input);
        eDateIn = (EditText)rootView.findViewById(R.id.publish_end_date_input);
        geocoder = new Geocoder(container.getContext(), Locale.getDefault());
        commit = (CheckBox)rootView.findViewById(R.id.checkBox);
        startTimePickerF = (TimePicker)rootView.findViewById(R.id.startTimePickerF);
        endTimePickerF = (TimePicker)rootView.findViewById(R.id.endTimePickerF);
        startTimePickerF.setIs24HourView(true);
        endTimePickerF.setIs24HourView(true);
        seeMapBtn = (Button)rootView.findViewById(R.id.see_map_btn);
        seeMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String addressFromArr = ;
//                String addressFromArr = (String)addressesAL.get(position);
                //check if address is legal
                try {
                    addresses = geocoder.getFromLocationName(addressIn.getText().toString(), 1);
                    if (addresses.size() < 1) {
                        throw new Exception();
                    }
                    else{
                        Intent intent = new Intent(getContext(), seeMapActivity.class);
                        intent.putExtra("address", addressIn.getText().toString());
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    addressIn.setError("Illegal address");
                }
            }
        });

        continueBtn = (Button) rootView.findViewById(R.id.continue_btn);

        final EditText address = (EditText)rootView.findViewById(R.id.by_address_input);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()) {
                    return;
                }
                String startDateStr = sDateIn.getText().toString();
                Date startDate;
                String endDateStr = eDateIn.getText().toString();
                Date endDate;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    startDate = dateFormat.parse(startDateStr);
                    endDate = dateFormat.parse(endDateStr);

                    //todo-  check (startDate.after(new Date()))
                    if (startDate.after(endDate)) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Invalid date scheduling",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                long addToStartDate = TimeUnit.MINUTES.toMillis(startTimePickerF.getCurrentMinute()) + TimeUnit.HOURS.toMillis(startTimePickerF.getCurrentHour() + 3);
                long addToEndDate = TimeUnit.MINUTES.toMillis(endTimePickerF.getCurrentMinute()) + TimeUnit.HOURS.toMillis(endTimePickerF.getCurrentHour() + 3);

                Intent intent = new Intent(getContext(), AvailableParkingListActivity.class);
                intent.putExtra("address", address.getText().toString());
                intent.putExtra("addressLat", addresses.get(0).getLatitude());
                intent.putExtra("addressLng", addresses.get(0).getLongitude());
                intent.putExtra("startDateF", startDate.getTime() + addToStartDate);
                intent.putExtra("endDateF", endDate.getTime() + addToEndDate);
                startActivity(intent);
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

        if (!commit.isChecked()) {
            commit.setError("Required.");
            valid = false;
        } else {
            commit.setError(null);
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
        if(!isValidTime(startDateStr, endDateStr, startTimePickerF, endTimePickerF)){
            Toast.makeText(getActivity(), "Illegal time scheduling", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }
}
