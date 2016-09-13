package huji.ac.il.parkme;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    String fullName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        Intent intent = getIntent();
        fullName = intent.getStringExtra("fullName");
        toolbar.setSubtitle("Hello, " + fullName); //TODO: check the username name
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(), getApplicationContext()));

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        tabLayout.setTabTextColors(R.color.unselected, R.color.default_color);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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
                        Intent intentPayment = new Intent(MainActivity.this, UpdatePaymentActivity.class);
                        startActivity(intentPayment);
                        break;
                    case R.id.change_parking:
                        Intent intentParking = new Intent(MainActivity.this, UpdateParkingActivity.class);
                        startActivity(intentParking);
                        break;
                    case R.id.logout:
                        Intent intentEmailPassword = new Intent(MainActivity.this, EmailPasswordActivity.class);
                        startActivity(intentEmailPassword);
                        break;
                    case R.id.home:
                        Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        break;
                     case R.id.contact_us:
                        Intent contactIntent = new Intent(MainActivity.this, EmailSend.class);
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

    private class CustomAdapter extends FragmentPagerAdapter {
        private String fragments[] = {"My calender", "Add park", "Find park"};
        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new CalenderFragment();
                case 1:
                    return new PublishParkingFragment();
                case 2:
                    return new FindParkingFragment();
                default:
                    break;
            }
            return null;
        }
        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position];
        }
    }
}
