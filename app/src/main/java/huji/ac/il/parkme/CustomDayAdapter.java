package huji.ac.il.parkme;

import android.graphics.Color;
import android.widget.TextView;

import com.vdesmet.lib.calendar.DayAdapter;

import java.util.Arrays;
import java.util.Calendar;

public class CustomDayAdapter implements DayAdapter {
    private long[] orders;
    private long[] rents;

    private static final int[][] CATEGORY_COLORS = {
            null, null, null,
            { Color.CYAN },
            { Color.GREEN },
            { Color.GREEN, Color.CYAN }
    };

//    private final Random mRandom;
    private final long mToday;

    public CustomDayAdapter(long[] orders, long[] rents) {
//        mRandom = new Random();
        orders = orders;
        rents = rents;
        // Get the time in millis of today
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        mToday = calendar.getTimeInMillis();
    }

    @Override
    public int[] getCategoryColors(final long dayInMillis) {
        // Fill the category colors with random int arrays.
//        final int index = mRandom.nextInt(CATEGORY_COLORS.length);
        Boolean inOrders = Arrays.asList(orders).contains(dayInMillis),
                inRents = Arrays.asList(rents).contains(dayInMillis);

        if(inOrders && inRents){
            return CATEGORY_COLORS[3];
        }
        else if (inOrders){
            return CATEGORY_COLORS[2];
        }
        else if (inRents){
            return CATEGORY_COLORS[1];
        }
        return CATEGORY_COLORS[0];
    }

    @Override
    public boolean isDayEnabled(final long dayInMillis) {
        return true;
    }

    @Override
    public void updateTextView(final TextView dateTextView, final long dayInMillis) {
        if(mToday == dayInMillis) {
            // Do something with the selected date
            dateTextView.setTextColor(Color.RED);
        }
        // else, we don't need to update the TextView
    }

    @Override
    public void updateHeaderTextView(final TextView header, final int dayOfWeek) {
        switch(dayOfWeek) {
            case Calendar.SATURDAY:
                header.setTextColor(Color.RED);
                break;
            default:
                header.setTextColor(Color.BLUE);
                break;
        }
    }
}