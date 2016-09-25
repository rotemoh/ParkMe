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
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Adi on 21/08/2016.
 */
public class FindParkingFragment extends Fragment{
    public EditText addressIn, sDateIn, eDateIn;
    public List<Address> addresses;
    public Geocoder geocoder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.find_park_layout, container, false);
        addressIn = (EditText)rootView.findViewById(R.id.by_address_input);
        sDateIn = (EditText)rootView.findViewById(R.id.publish_start_date_input);
        eDateIn = (EditText)rootView.findViewById(R.id.publish_end_date_input);
        geocoder = new Geocoder(container.getContext(), Locale.getDefault());

        Button continueBtn = (Button) rootView.findViewById(R.id.continue_btn);

        final EditText address = (EditText)rootView.findViewById(R.id.by_address_input);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()) {
                    return;
                }

                Intent intent = new Intent(getContext(), AvailableParkingListActivity.class);
                intent.putExtra("address", address.getText().toString());
                intent.putExtra("addressLat", addresses.get(0).getLatitude());
                intent.putExtra("addressLng", addresses.get(0).getLongitude());
                startActivity(intent);
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
