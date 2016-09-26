package huji.ac.il.parkme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vdesmet.lib.calendar.MultiCalendarView;
import com.vdesmet.lib.calendar.OnDayClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Adi on 22/08/2016.
 */
public class CalenderFragment extends Fragment implements OnDayClickListener {
    private TextView mSelectedTextView;
    private Typeface mSelectedTypeface;
    private MultiCalendarView multiMonth;
    private ListView publishLV, orderedLV;
    //todo: change to the dates of the user
    private ArrayList<Long> orders = new ArrayList<>(), rents = new ArrayList<>();
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
        CustomDayAdapter adapter = new CustomDayAdapter(orders, rents);
//        updateCalendar();

        // Set listener and adapter
        multiMonth.setOnDayClickListener(this);
        multiMonth.setDayAdapter(adapter);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            String startDateStr = bundle.get("startDateStr").toString();
            Date startDate;
            String endDateStr = bundle.get("endDateStr").toString();
            Date endDate;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                startDate = dateFormat.parse(startDateStr);
                endDate = dateFormat.parse(endDateStr);
                orders.add(startDate.getTime());
                orders.add(endDate.getTime());
                rents.add(startDate.getTime());
                rents.add(endDate.getTime());
                //todo-  check (startDate.after(new Date()))

            } catch (Exception e) {
                Toast.makeText(getActivity(), "Invalid date scheduling",
                        Toast.LENGTH_SHORT).show();
            }
        }
        multiMonth.notifyDataSetChanged();

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
        dialog.show();

        //TODO: change to the addresses in the area. will get it from the DB.
        final String[] addresses = new String[] {"Android List View",
                "Gilboa 94 Alfei Menashe",
                "hi",
                "Create List View Android"};
        final String[] info = new String[] {"dis 1, cost 1",
                "address 2, cost 2",
                "address 3, cost 3",
                "address 4, cost 4"};
        final String[] addresses2 = new String[] {"Android List View",
                "Gilboa 94 Alfei Menashe",
                "hi",
                "Create List View Android"};
        final String[] info2 = new String[] {"dis 1, cost 1",
                "address 2, cost 2",
                "address 3, cost 3",
                "address 4, cost 4"};

        ArrayAdapter orderedLVAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, addresses) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(addresses[position]);
                text2.setText(info[position]);
                return view;
            }
        };

        ArrayAdapter publishLVAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, addresses2) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(addresses2[position]);
                text2.setText(info2[position]);
                return view;
            }
        };
        publishLV = (ListView)dialog.findViewById(R.id.published_lv);
        publishLV.setAdapter(publishLVAdapter);
        orderedLV = (ListView)dialog.findViewById(R.id.ordered_lv);
        orderedLV.setAdapter(orderedLVAdapter);

        publishLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        // Then you can create a listener like so:
        orderedLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
