package huji.ac.il.parkme;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.vdesmet.lib.calendar.MultiCalendarView;
import com.vdesmet.lib.calendar.OnDayClickListener;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Adi on 22/08/2016.
 */
public class CalenderFragment extends Fragment implements OnDayClickListener {
    private TextView mSelectedTextView;
    private Typeface mSelectedTypeface;
    private MultiCalendarView multiMonth;
    private ListView publishLV, orderedLV;
    private ArrayAdapter<String> adapter;

   //todo: change to the dates of the user
    private long[] orders, rents;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calander_layout, container, false);
// Retrieve the CalendarView
        multiMonth = (MultiCalendarView) rootView.findViewById(R.id.multi_calendar);

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

        // custom dialog
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.day_dialog);
//        dialog.setTitle("");

        dialog.show();
        ArrayList<String> planetList = new ArrayList<String>();
        planetList.add("hiiiii");
//        publishLV = (ListView)dialog.findViewById(R.id.published_lv);
        orderedLV = (ListView)dialog.findViewById(R.id.ordered_lv);
//        registerForContextMenu(orderedLV);
        adapter = new ArrayAdapter<String>(getContext(), R.layout.simplerow, R.id.rowTextView, planetList);
        orderedLV.setAdapter( adapter );

// Then you can create a listener like so:
        orderedLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                String dialogTitle = notes.get(position);
                // 2. Chain together various setter methods to set the dialog characteristics
//                builder.setTitle("in this date...");

//                if (dialogTitle.startsWith("call") || dialogTitle.startsWith("Call")) {
                //TODO: get the owner number
                    final String numberToCall = "0508655309";
                    builder.setPositiveButton("call owner", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intentCall = new Intent(Intent.ACTION_CALL);
                            intentCall.setData(Uri.parse("tel:" + numberToCall));
                            startActivity(intentCall);
                        }
                    });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            }
        });

    }
}
