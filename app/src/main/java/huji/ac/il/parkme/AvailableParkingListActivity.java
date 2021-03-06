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

public class AvailableParkingListActivity extends AppCompatActivity {
    ListView listView;
    public String address;
    public double addressLat;
    public double addressLng;
    public long thisStartDate;
    public long thisEndDate;
    public ArrayList<String> addressesList;
    public ArrayList<String> disCostList;
    public ArrayList<String> IDparkList;
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
        addressesList = new ArrayList<>();
        disCostList = new ArrayList<>();
        IDparkList = new ArrayList<>();
        // Define a new Adapter:
        //Context, Layout for the row, ID of the TextView to which
        // the data is written and the Array of data.
        final ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_2,
                android.R.id.text1, addressesList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(addressesList.get(position));
                text2.setText(disCostList.get(position));
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
                String addressFromArr = (String) listView.getItemAtPosition(position);
//                String addressFromArr = (String)addressesAL.get(position);
                Intent intent = new Intent(AvailableParkingListActivity.this, ParkingInfoActivity.class);
                intent.putExtra("address", addressFromArr);
                intent.putExtra("parkID", IDparkList.get(position));
                startActivity(intent);
            }
        });
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
        thisStartDate = intent.getLongExtra("startDateF", 0);
        thisEndDate = intent.getLongExtra("endDateF", 0);

        dest = (TextView)findViewById(R.id.destination_txt);
        dest.setText("Available parking for address: " + address);

        ValueEventListener parkingListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot parkingSnapshot) {
//                int i = 0;
                for (DataSnapshot parking : parkingSnapshot.getChildren()) {
                    Parking parkFromDB = parking.getValue(Parking.class);

                    if (!(thisStartDate <= parkFromDB.getEndDate() &&
                            thisEndDate >= parkFromDB.getStartDate())) {
                        continue;
                    }
                    if (!parkFromDB.getIsAvailable().equals("true")) {
                        continue;
                    }
                    //******** 1 ********
                    //(StartA <= EndB) and (EndA >= StartB)
//                    if (!(thisStartDate <=  Long.parseLong(parking.child("endDate").getValue().toString())) &&
//                            (thisEndDate >= Long.parseLong(parking.child("startDate").getValue().toString()))) {
//                        continue;
//                    }
                    results = new float[]{0, 0, 0, 0};

                    //******** 2 ********
                    Location.distanceBetween(
                            (double) parking.child("latitude").getValue(),
                            (double) parking.child("longitude").getValue(),
                            addressLat,
                            addressLng,
                            results);
                    parkingDistances.add(new ParkPair(parking.getKey(), (results[0] / 1000)));

                }

                Collections.sort(parkingDistances);

                for (ParkPair relevantParkPair : parkingDistances) {
                    Parking addPark = parkingSnapshot.child(relevantParkPair.parkId).getValue(Parking.class);
                    IDparkList.add(addPark.getKey());
                    addressesList.add(addPark.address);
                    disCostList.add("Distance from asked address: " + String.format("%.2f", relevantParkPair.distance) + "km, cost: " + addPark.cost + " nis.");
                }
//                adapter.add(addressesList);
//                adapter.add(disCostList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        };
        availableParkingDatabase.child("Parking").addValueEventListener(parkingListener);

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
                        intentEmailPassword.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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