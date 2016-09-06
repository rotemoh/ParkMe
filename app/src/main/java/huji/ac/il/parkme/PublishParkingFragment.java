package huji.ac.il.parkme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

/**
 * Created by Adi on 21/08/2016.
 */
public class PublishParkingFragment extends Fragment {
    NumberPicker numberpicker;
    Button resetBtn, updateBtn;
    EditText addressIn, commentsIn, sDateIn, eDateIn;
    CheckBox approve, save;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.publish_park_layout, container, false);
        addressIn = (EditText)rootView.findViewById(R.id.address_input);
        commentsIn = (EditText)rootView.findViewById(R.id.comments_input);
        sDateIn = (EditText)rootView.findViewById(R.id.publish_start_date_input);
        eDateIn = (EditText)rootView.findViewById(R.id.publish_end_date_input);
//.setError
        approve = (CheckBox)rootView.findViewById(R.id.approve_check);
        save = (CheckBox)rootView.findViewById(R.id.save_details_check);

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
                save.setChecked(true);

                numberpicker.setValue(0);
            }
        });

        updateBtn = (Button)rootView.findViewById(R.id.update_btn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Data updated successfully",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}
