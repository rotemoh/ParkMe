package huji.ac.il.parkme;

import android.graphics.Color;
import android.widget.TextView;

import com.vdesmet.lib.calendar.DayAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class CustomDayAdapter implements DayAdapter {
    private final Random mRandom;
    private boolean inOrders = false, inRents = false;
    private ArrayList<Long> order = new ArrayList<>(), rent = new ArrayList<>();
    private static final int[][] CATEGORY_COLORS = {
            null,
            {Color.parseColor("#A40D44")},
            {Color.parseColor("#81007F")},
            {Color.parseColor("#A40D44"), Color.parseColor("#81007F")}
    };

    //    private final Random mRandom;
    private final long mToday;

    public CustomDayAdapter(ArrayList<Long> orders, ArrayList<Long> rents) {
        mRandom = new Random();
        order = orders;
        rent = rents;
        //    Context context = ;

        // Get the time in millis of today
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        mToday = calendar.getTimeInMillis();
        System.out.println();
    }

    @Override
    public int[] getCategoryColors(final long dayInMillis) {

        if(rent.size() != 0) {
//            System.out.println("in rent");
//            System.out.println("rent " + rent.get(0));
//            System.out.println("date " + (new Date(dayInMillis).toString()));
//            System.out.println("date rent " + (new Date(rent.get(0)).toString()));
//            System.out.println("dayInMillis " + dayInMillis);
//            System.out.println(rent.contains(dayInMillis));
            inRents = rent.contains(dayInMillis);
        }
        if(order.size() != 0){
            inOrders = order.contains(dayInMillis);
        }

        if (inOrders && inRents) {
            return CATEGORY_COLORS[3];
        } else if (inOrders) {
            return CATEGORY_COLORS[2];
        } else if (inRents) {
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

                header.setTextColor(Color.parseColor("#00bfa5"));
                break;
            default:
                header.setTextColor(Color.parseColor("#7a4aaa"));
                break;
        }
    }

}