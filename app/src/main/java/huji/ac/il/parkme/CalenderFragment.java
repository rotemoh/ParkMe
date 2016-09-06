package huji.ac.il.parkme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Adi on 22/08/2016.
 */
public class CalenderFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calander_layout, container, false);
//        DatePicker simpleDatePicker = (DatePicker)rootView.findViewById(R.id.datePicker); // initiate a date picker
//
//        simpleDatePicker.setSpinnersShown(false);
//        int day = simpleDatePicker.getDayOfMonth(); // get the selected day of the month
//        int month = simpleDatePicker.getMonth(); // get the selected month
//        int year = simpleDatePicker.getYear(); // get the selected year
//        Date minDate = new Date(year-1, month, day);
//        long minDateMilliSeconds = minDate.getTime();
//        Date maxDate = new Date(year+5, month, day);
//        long maxDateMilliSeconds = maxDate.getTime();
//        simpleDatePicker.setMinDate(minDateMilliSeconds);
//        simpleDatePicker.setMaxDate(maxDateMilliSeconds);

        return rootView;

    }
}
