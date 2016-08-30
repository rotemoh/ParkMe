package huji.ac.il.parkme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Adi on 21/08/2016.
 */
public class FindParkingFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.find_park_layout, container, false);
        Button continueBtn = (Button) rootView.findViewById(R.id.continue_btn);
        final EditText address = (EditText)rootView.findViewById(R.id.by_address_input);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AvailableParkingListActivity.class);
                intent.putExtra("address", address.getText());
                startActivity(intent);

            }
        });
        return rootView;
    }
    
}
