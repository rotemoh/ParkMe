package huji.ac.il.parkme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vdesmet.lib.calendar.MultiCalendarView;

import java.util.Calendar;

/**
 * Created by Adi on 22/08/2016.
 */
public class CalenderFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calander_layout, container, false);
// Retrieve the CalendarView
        MultiCalendarView multiMonth = (MultiCalendarView) rootView.findViewById(R.id.multi_calendar);

// Set the first valid day
        final Calendar firstValidDay = Calendar.getInstance();
        multiMonth.setFirstValidDay(firstValidDay);

// Set the last valid day
        final Calendar lastValidDay = Calendar.getInstance();
        lastValidDay.add(Calendar.YEAR, 1);
        multiMonth.setLastValidDay(lastValidDay);
        return rootView;

    }
}
