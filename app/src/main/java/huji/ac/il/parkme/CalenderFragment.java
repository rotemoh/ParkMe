package huji.ac.il.parkme;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vdesmet.lib.calendar.MultiCalendarView;
import com.vdesmet.lib.calendar.OnDayClickListener;

import java.util.Calendar;

/**
 * Created by Adi on 22/08/2016.
 */
public class CalenderFragment extends Fragment implements OnDayClickListener {
    private TextView mSelectedTextView;
    private Typeface mSelectedTypeface;
    private MultiCalendarView multiMonth;

   //todo: change to the dates of the user
    private long[] orders, rents;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calander_layout, container, false);
// Retrieve the CalendarView
        multiMonth = (MultiCalendarView) rootView.findViewById(R.id.multi_calendar);
//SquaredImageView square = new SquaredImageView(rootView, )
// Set the first valid day
        final Calendar firstValidDay = Calendar.getInstance();
        multiMonth.setFirstValidDay(firstValidDay);

// Set the last valid day
        final Calendar lastValidDay = Calendar.getInstance();
        lastValidDay.add(Calendar.YEAR, 1);
        multiMonth.setLastValidDay(lastValidDay);

        // Create adapter
        final CustomDayAdapter adapter = new CustomDayAdapter(orders, rents);

        // Set listener and adapter
        multiMonth.setOnDayClickListener(this);
        multiMonth.setDayAdapter(adapter);
        return rootView;
    }
    @Override
    public void onDayClick(final long dayInMillis) {
        // Reset the previously selected TextView to his previous Typeface
        if(mSelectedTextView != null) {
            mSelectedTextView.setTypeface(mSelectedTypeface);
        }

        final TextView day = multiMonth.getTextViewForDate(dayInMillis);
        if(day != null) {
            // Remember the selected TextView and it's font
            mSelectedTypeface = day.getTypeface();
            mSelectedTextView = day;
            // Show the selected TextView as bold
            day.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }
}
