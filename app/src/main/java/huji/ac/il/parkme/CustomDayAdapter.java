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
    }

    @Override
    public int[] getCategoryColors(final long dayInMillis) {
        // Fill the category colors with random int arrays.
//        final int index = mRandom.nextInt(CATEGORY_COLORS.length);
//        Boolean inOrders = Arrays.asList(orders).contains(dayInMillis),
//                inRents = Arrays.asList(rents).contains(dayInMillis);
        // Fill the category colors with random int arrays.
//        final int index = mRandom.nextInt(CATEGORY_COLORS.length);
//        return CATEGORY_COLORS[index];
//        rent.add(10000998442L);
//        order.add(10000998441L);
//        order.add(dayInMillis);
        if(rent != null) {
            inRents = rent.contains(dayInMillis);
        }
        if(order != null){
            inOrders = order.contains(dayInMillis);
        }

        //        System.out.println("dayInMillis " + dayInMillis);
//        System.out.println("inOrders " + inOrders);
//        System.out.println("inRents" + inRents);
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