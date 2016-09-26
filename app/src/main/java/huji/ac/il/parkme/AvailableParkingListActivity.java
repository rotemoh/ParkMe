package huji.ac.il.parkme;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

public class AvailableParkingListActivity extends AppCompatActivity {
    ListView listView;
    public String address;
    public double addressLat;
    public double addressLng;
    public long thisStartDate;
    public long thisEndDate;
    public ArrayList<String> addressesList;
    public ArrayList<String> disCostList;
    TextView dest;

    public Context context = this;
    private Toolbar toolbar;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public DatabaseReference availableParkingDatabase;
    public ArrayList<ParkPair> parkingDistances;
    public float[] results;

    public class ParkPair implements Comparable {
        public double distance;
        public String parkId;

        public ParkPair(String parkId, double distance) {
            this.parkId = parkId;
            this.distance = distance;
        }

        @Override
        public int compareTo(Object another) {
            ParkPair p2 = (ParkPair)another;
            if (this.distance > p2.distance) {
                return 1;
            } else if (this.distance < p2.distance){
                return -1;
            } else {
                return 0;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_parking_list);
        listView = (ListView) findViewById(R.id.listViewParking);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("ParkeMe");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        availableParkingDatabase = FirebaseDatabase.getInstance().getReference();

        parkingDistances = new ArrayList();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        address = bundle.get("address").toString();
        //TODO: check if good
        addressLat = intent.getDoubleExtra("addressLat", 0);
        addressLng = intent.getDoubleExtra("addressLng", 0);
//        thisStartDate = bundle.getLong("startDateF", 0);
//        thisStartDate = bundle.getLong("startDateF", 0);
//        thisStartDate = (Long)intent.getSerializableExtra("startDateF");
//        thisEndDate = (Long)intent.getSerializableExtra("endDateF");

//        thisEndDate = bundle.getLong("endDateF", 0);
        thisStartDate = intent.getLongExtra("startDateF", 0);
        thisEndDate = intent.getLongExtra(("endDateF"), 0);
//        System.out.println("thisStartDate type " + Objects.thisStartDate);
        System.out.println("thisStartDate class" + ((Object)thisStartDate).getClass());

        addressesList = new ArrayList<>();
        disCostList = new ArrayList<>();

        dest = (TextView)findViewById(R.id.destination_txt);
        dest.setText("Available parking for address: " + address);

        ValueEventListener parkingListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot parkingSnapshot) {
                int i = 0;
                for(DataSnapshot parking : parkingSnapshot.getChildren()) {
                    //(StartA <= EndB) and (EndA >= StartB)
                    if (!(thisStartDate <=  parking.child("endDate").getValue(Long.class)) &&
                            (thisEndDate >= Long.parseLong(parking.child("startDate").getValue().toString()))) {
                        continue;
                    }
                    results = new float[]{0 , 0 , 0, 0};
                    Location.distanceBetween(
                            (double) parking.child("latitude").getValue(),
                            (double) parking.child("longitude").getValue(),
                            addressLat,
                            addressLng,
                            results);
                    parkingDistances.add(new ParkPair(parking.getKey(), results[0] / 1000));

                    System.out.println("parkingDistances[" + i + "] :  " + parkingDistances.get(i).distance);
                    i++;
                }
                Collections.sort(parkingDistances);
                System.out.println(parkingDistances.get(0).distance);
                System.out.println(parkingDistances.get(1).distance);

                for (ParkPair relevantParkPair : parkingDistances) {
                    Parking addPark = parkingSnapshot.child(relevantParkPair.parkId).getValue(Parking.class);
                    addressesList.add(addPark.address);
                    disCostList.add(relevantParkPair.distance + " , " + addPark.cost);
                }
                System.out.println("addressesList " + addressesList.get(0));
                System.out.println("disCostList " + disCostList.get(0));

                System.out.println("addressesList " + addressesList.get(1));
                System.out.println("disCostList " + disCostList.get(1));

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        };
        availableParkingDatabase.child("Parking").addValueEventListener(parkingListener);


        // Defined Array values to show in ListView
        //TODO: change to the addresses in the area. will get it from the DB.
        final String[] addresses = new String[] {"Android List View",
                "Gilboa 94 Alfei Menashe",
                "hi",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"};
        final String[] info = new String[] {"dis 1, cost 1",
                "address 2, cost 2",
                "address 3, cost 3",
                "address 4, cost 4",
                "address 5, cost 5",
                "address 6, cost 6",
                "address 7, cost 7",
                "address 8, cost 8"};


        // Define a new Adapter:
        //Context, Layout for the row, ID of the TextView to which
        // the data is written and the Array of data.
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_2, android.R.id.text1, addresses) {
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
        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ListView Clicked item value
                String address = (String) listView.getItemAtPosition(position);

                Intent intent = new Intent(AvailableParkingListActivity.this, ParkingInfoActivity.class);
//                Intent intent = new Intent(AvailableParkingListActivity.this, ParkingPaymentActivity.class);
                intent.putExtra("address", address);
                startActivityForResult(intent,1);//TODO: check if need result
            }
        });
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //Closing drawer on item click
                drawerLayout.closeDrawers();

                switch (item.getItemId()) {
                    case R.id.change_payment:
                        Intent intentPayment = new Intent(AvailableParkingListActivity.this, UpdatePaymentActivity.class);
                        startActivity(intentPayment);
                        break;
                    case R.id.change_parking:
                        Intent intentParking = new Intent(AvailableParkingListActivity.this, UpdateParkingActivity.class);
                        startActivity(intentParking);
                        break;
                    case R.id.logout:
                        Intent intentEmailPassword = new Intent(AvailableParkingListActivity.this, EmailPasswordActivity.class);
                        startActivity(intentEmailPassword);
                        break;
                    case R.id.home:
                        Intent homeIntent = new Intent(AvailableParkingListActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        break;
                    case R.id.contact_us:
                        Intent contactIntent = new Intent(AvailableParkingListActivity.this, EmailSend.class);
                        startActivity(contactIntent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}