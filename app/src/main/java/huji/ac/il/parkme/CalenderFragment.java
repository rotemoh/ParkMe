package huji.ac.il.parkme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vdesmet.lib.calendar.MultiCalendarView;
import com.vdesmet.lib.calendar.OnDayClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

/**
 * Created by Adi on 22/08/2016.
 */
public class CalenderFragment extends Fragment implements OnDayClickListener {
    private TextView mSelectedTextView;
    private Typeface mSelectedTypeface;
    private MultiCalendarView multiMonth;
    private ListView publishLV, orderedLV;
    //public ArrayList<Long> startDates , endDates ;
    //public ArrayList myParksId;
    public ArrayList<Parking> myPublishedParks;
    public ArrayList<Parking> myOrderedParks;
    public ArrayList<String> myOrderedParksID;
    public String userId;
    //todo: change to the dates of the user
    private ArrayList<Long> orders, rents;

    public FirebaseAuth CalFragAuth;
    public DatabaseReference CalFragDatabase;

    //public ArrayList<String> publishParkingAddresses;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calander_layout, container, false);
        // Retrieve the CalendarView
        multiMonth = (MultiCalendarView) rootView.findViewById(R.id.multi_calendar);
        // Set the first valid day
        final Calendar firstValidDay = Calendar.getInstance();
        multiMonth.setFirstValidDay(firstValidDay);

        CalFragDatabase = FirebaseDatabase.getInstance().getReference();
        CalFragAuth = getInstance();
        userId = CalFragAuth.getCurrentUser().getUid();
        //startDates = new ArrayList<>();
        //endDates = new ArrayList<>();
        rents = new ArrayList<>();
        orders = new ArrayList<>();
        //myParksId = new ArrayList<>();
        myPublishedParks = new ArrayList<>();
        myOrderedParks = new ArrayList<>();
        myOrderedParksID = new ArrayList<>();
        //publishParkingAddresses = new ArrayList<>();
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

//        ValueEventListener userListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot userPublicParkingSnapshot) {
//                for (DataSnapshot parking : userPublicParkingSnapshot.getChildren()) {
//                    final String myParkID = parking.getValue().toString();
//                    CalFragDatabase.child("Parking").child(myParkID);
//                    myParksId.add(myParkID);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                // ...
//            }
//        };
//        CalFragDatabase.child("Users").child(CalFragAuth.getCurrentUser().getUid()).
//                child("myPublicParking").addValueEventListener(userListener);

//        CalFragDatabase.child("Users").child(userId).child("myOrderedParking").addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot orderedParkID : dataSnapshot.getChildren()) {
//                            System.out.println("orderedParkID");
//                            myOrderedParksID.add(orderedParkID.getValue().toString());
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                });

        ValueEventListener userIdListener =
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataSnapshot myOrderedParking = dataSnapshot.child("myOrderedParking");
                        for (DataSnapshot orderedParkID : myOrderedParking.getChildren()) {
                            System.out.println("orderedParkID");
                            myOrderedParksID.add(orderedParkID.getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                };
        CalFragDatabase.child("Users").child(userId).addValueEventListener(userIdListener);

        System.out.println("myOrderedParksID.size() "  + myOrderedParksID.size());
        ValueEventListener parkingListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot parkingSnapshot) {
                for (DataSnapshot parking : parkingSnapshot.getChildren()) {
                    Parking parkFromDB = parking.getValue(Parking.class);
                    if ((parkFromDB.ownerID).equals(userId)) {
                        //publishParkingAddresses.add(parkFromDB.getAddress());
                        //startDates.add(parkFromDB.getStartDate());
                        //endDates.add(parkFromDB.getEndDate());
                        myPublishedParks.add(parkFromDB);
                        rents.addAll(getDates(parkFromDB.getStartDate(), parkFromDB.getEndDate()));
                    }
                    else if (myOrderedParksID.contains(parkFromDB.getKey())) {
                        myOrderedParks.add(parkFromDB);
                        orders.addAll(getDates(parkFromDB.getStartDate(), parkFromDB.getEndDate()));
                    }
                }
//                for (int i = 0; i < startDates.size(); i++) {
//                    rents.addAll(getDates(startDates.get(i), endDates.get(i)));
//                }
                multiMonth.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        CalFragDatabase.child("Parking").addListenerForSingleValueEvent(parkingListener);



//
//        for (int i = 0; i < startDates.size(); i++) {
//            System.out.println("hi3 " + startDates.get(i) + "  :  " + endDates.get(i));
//            rents.addAll(getDates(startDates.get(i), endDates.get(i)));
//        }
//        multiMonth.notifyDataSetChanged();
//        Intent intent = getActivity().getIntent();
//        Bundle bundle = intent.getExtras();
//        if(bundle != null) {
//            startDates = (ArrayList<Long>)bundle.getSerializable("startDates");
//            endDates = (ArrayList<Long>)bundle.getSerializable("endDates");
//            for (int i = 0; i < startDates.size(); i++){
//                rents.addAll(getDates(startDates.get(i), endDates.get(i)));
//            }
//        }

        return rootView;
    }


    private static ArrayList<Long> getDates(Long startDate, Long endDate)
    {
        ArrayList<Long> dates = new ArrayList<>();

        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(startDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(endDate);

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTimeInMillis());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
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


        ArrayAdapter orderedLVAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, myOrderedParks) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(myOrderedParks.get(position).getAddress());
                text2.setText(myOrderedParks.get(position).getStartTimeP() + "- " + myOrderedParks.get(position).getEndTimeP() + " , " + myOrderedParks.get(position).getCost() + " NIS.");
                return view;
            }
        };

//        ArrayAdapter publishLVAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, addresses2) {
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
//                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
//
//                text1.setText(addresses2[position]);
//                text2.setText(info2[position]);
//                return view;
//            }
//        };

        ArrayAdapter publishLVAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_2, android.R.id.text1, myPublishedParks) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(myPublishedParks.get(position).getAddress());
                text2.setText(myPublishedParks.get(position).getStartTimeP() + "- " + myPublishedParks.get(position).getEndTimeP() + " , " + myPublishedParks.get(position).getCost() + " NIS.");
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
