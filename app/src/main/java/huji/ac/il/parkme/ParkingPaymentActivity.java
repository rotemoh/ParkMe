package huji.ac.il.parkme;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ParkingPaymentActivity extends AppCompatActivity {
    String address;
    Intent intent;
    private Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_payment);
        Button routBtn = (Button) findViewById(R.id.rout_btn);
        Button homeBtn = (Button) findViewById(R.id.home_btn);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ParkeMe");
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intent = getIntent();
        address = intent.getStringExtra("address");
        routBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent routIntent = new Intent(ParkingPaymentActivity.this, MapsActivity.class);
                routIntent.putExtra("address", address);
                startActivity(routIntent);
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(ParkingPaymentActivity.this, MainActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(homeIntent);
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
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
                        Intent intentPayment = new Intent(ParkingPaymentActivity.this, UpdatePaymentActivity.class);
                        startActivity(intentPayment);
                        break;
                    case R.id.change_parking:
                        Intent intentParking = new Intent(ParkingPaymentActivity.this, UpdateParkingActivity.class);
                        startActivity(intentParking);
                        break;
                    case R.id.logout:
                        Intent intentEmailPassword = new Intent(ParkingPaymentActivity.this, EmailPasswordActivity.class);
                        startActivity(intentEmailPassword);
                        break;
                    case R.id.home:
                        Intent homeIntent = new Intent(ParkingPaymentActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        break;
                    case R.id.contact_us:
                        Intent contactIntent = new Intent(ParkingPaymentActivity.this, EmailSend.class);
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
    public void onConfigurationChanged(Configuration newConfig) {
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

