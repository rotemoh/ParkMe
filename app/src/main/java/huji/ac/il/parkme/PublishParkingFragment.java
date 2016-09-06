package huji.ac.il.parkme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Adi on 21/08/2016.
 */
public class PublishParkingFragment extends Fragment {
    public EditText addressInput;
    public EditText publishStartDateInput;
    public EditText publishEndDateInput;
    public NumberPicker costPicker;
    public EditText commentsInput;

    public DatabaseReference PPdatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View PublishParkingView = inflater.inflate(R.layout.publish_park_layout, container, false);

        addressInput = (EditText) PublishParkingView.findViewById(R.id.address_input);
        publishStartDateInput = (EditText) PublishParkingView.findViewById(R.id.publish_start_date_input);
        publishEndDateInput = (EditText) PublishParkingView.findViewById(R.id.publish_end_date_input);
        costPicker = (NumberPicker) PublishParkingView.findViewById(R.id.cost_picker);
        commentsInput = (EditText) PublishParkingView.findViewById(R.id.comments_input);

        PPdatabase = FirebaseDatabase.getInstance().getReference();

//        PublishParkingView.findViewById(R.id.update_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String key = PPdatabase.child("Parking").push().getKey();
//                Parking addParking = new Parking(addressInput.getText().toString(), "Azrieli",
//                        "Tel Aviv", FirebaseAuth.getInstance().getCurrentUser().getUid(), "" + costPicker.getValue());
////                Parking addParking = new Parking(addressInput.getText().toString(), "Azrieli",
////                        "Tel Aviv", FirebaseAuth.getInstance().getCurrentUser().getUid(), "" + costPicker.getValue());
//
//                PPdatabase.child("Parking").child(key).setValue(addParking);
//                //todo- add messege "publish parking is succ
//            }
//        });
        return PublishParkingView;
    }

}