package huji.ac.il.parkme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Adi on 21/08/2016.
 */
public class PublishParkingFragment extends Fragment {
    NumberPicker numberpicker;
    Button resetBtn, updateBtn;
    EditText addressIn, commentsIn, sDateIn, eDateIn;
    CheckBox approve;

    public DatabaseReference PPdatabase;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.publish_park_layout, container, false);
        addressIn = (EditText)rootView.findViewById(R.id.address_input);
        commentsIn = (EditText)rootView.findViewById(R.id.comments_input);
        sDateIn = (EditText)rootView.findViewById(R.id.publish_start_date_input);
        eDateIn = (EditText)rootView.findViewById(R.id.publish_end_date_input);
        PPdatabase = FirebaseDatabase.getInstance().getReference();

//.setError
        approve = (CheckBox)rootView.findViewById(R.id.approve_check);
        approve.setError("Required.");
        approve.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ) {
                    approve.setError(null);
                } else {
                    approve.setError("Required.");
                }
            }
        });
        numberpicker = (NumberPicker)rootView.findViewById(R.id.cost_picker);
        numberpicker.setMinValue(0);
        numberpicker.setMaxValue(100);

        numberpicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //TODO
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
                numberpicker.setValue(0);
            }
        });

        updateBtn = (Button)rootView.findViewById(R.id.update_btn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDateStr = sDateIn.getText().toString();
                String endDateStr = eDateIn.getText().toString();
                //TODO check address
//                String address = addressIn.getText().toString();

//                if (address.equals("")) {
//                    addressIn.setError("Incorrect or missing address");
//                }
                if (startDateStr.equals("") || !isValidDate(startDateStr)) {
                    sDateIn.setError("Incorrect or missing date");
                }
                if (endDateStr.equals("") || !isValidDate(endDateStr)) {
                    eDateIn.setError("Incorrect or missing date");
                }

//                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
//                try {
//                    List<Address> addresses = geocoder.getFromLocationName(address, 1);
//                    LatLng point = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
//
//                } catch (IOException e) {
//                    addressIn.setError("Incorrect or missing address");
//                }
                if(approve.isChecked() && isValidDate(startDateStr) && isValidDate(endDateStr)/* && !address.equals("")*/){
                    Toast.makeText(getActivity(), "Data updated successfully",
                            Toast.LENGTH_SHORT).show();
                }

                String key = PPdatabase.child("Parking").push().getKey();
                Parking addParking = new Parking(addressIn.getText().toString(), "Azrieli",
                        "Tel Aviv",FirebaseAuth.getInstance().getCurrentUser().getUid(), "" + numberpicker.getValue());
//                Parking addParking = new Parking(addressInput.getText().toString(), "Azrieli",
//                        "Tel Aviv", FirebaseAuth.getInstance().getCurrentUser().getUid(), "" + costPicker.getValue());

                PPdatabase.child("Parking").child(key).setValue(addParking);
                //todo- add messege "publish parking is succ
                Toast.makeText(getActivity(), "Parking published successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });


//        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
//                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

//
//        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
//                .build();
//        autocompleteFragment.setFilter(typeFilter);
//
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                // TODO: Get info about the selected place.
////                Log.i(TAG, "Place: " + place.getName());//get place details here
//                Toast.makeText(getActivity(), "in on place selected",
//                        Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(Status status) {
//                // TODO: Handle the error.
//                Toast.makeText(getActivity(), "in on error",
//                        Toast.LENGTH_SHORT).show();            }
//        });


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

}
