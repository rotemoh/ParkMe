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

public class MapsActivity extends AppCompatActivity /*FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener*/ {

//    private GoogleMap mMap;
//    ArrayList<LatLng> MarkerPoints;
//    GoogleApiClient mGoogleApiClient;
//    Location mLastLocation;
//    Marker mCurrLocationMarker;
//    LocationRequest mLocationRequest;
    private Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ParkeMe");
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                        Intent intentPayment = new Intent(MapsActivity.this, UpdatePaymentActivity.class);
                        startActivity(intentPayment);
                        break;
                    case R.id.change_parking:
                        Intent intentParking = new Intent(MapsActivity.this, UpdateParkingActivity.class);
                        startActivity(intentParking);
                        break;
                    case R.id.logout:
                        Intent intentEmailPassword = new Intent(MapsActivity.this, EmailPasswordActivity.class);
                        startActivity(intentEmailPassword);
                        break;
                    case R.id.home:
                        Intent homeIntent = new Intent(MapsActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        break;
                    case R.id.contact_us:
                        Intent contactIntent = new Intent(MapsActivity.this, EmailSend.class);
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
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkLocationPermission();
//        }
//        // Initializing
//        MarkerPoints = new ArrayList<>();
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }

//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        //Initialize Google Play Services
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED) {
//                buildGoogleApiClient();
//                mMap.setMyLocationEnabled(true);
//            }
//        }
//        else {
//            buildGoogleApiClient();
//            mMap.setMyLocationEnabled(true);
//        }
//
////        Intent intent = getIntent();
////        String address = intent.getStringExtra("address");
////        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
////        List<Address> addresses = null;
////    //getting the asked address's lat & long:
////        try {
////            addresses = geocoder.getFromLocationName(address, 1);
////        } catch (IOException e1) {
////            e1.printStackTrace();
////        }
////
////        if(addresses.size() > 0) {
////            double addLatitude= addresses.get(0).getLatitude();
////            double addLongitude= addresses.get(0).getLongitude();
////            //TODO: remove the toast
////            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + addLatitude + "\nLong: " + addLongitude, Toast.LENGTH_LONG).show();
////        }
//        //getting the current position's lat & long:
////        GPSTracker gps = new GPSTracker(MapsActivity.this);
////
////        // check if GPS enabled
////        if(gps.canGetLocation()){
////            double latitude = gps.getLatitude();
////            double longitude = gps.getLongitude();
////            //TODO: remove the toast
////            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
////        }else{
////            // can't get location
////            // GPS or Network is not enabled
////            // Ask user to enable GPS/network in settings
////            gps.showSettingsAlert();
////        }
//
//        // Setting onclick event listener for the map
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//
//            @Override
//            public void onMapClick(LatLng point) {
//
//                // Already two locations
//                if (MarkerPoints.size() > 1) {
//                    MarkerPoints.clear();
//                    mMap.clear();
//                }
//
//                // Adding new item to the ArrayList
//                MarkerPoints.add(point);
//
//                // Creating MarkerOptions
//                MarkerOptions options = new MarkerOptions();
//
//                // Setting the position of the marker
//                options.position(point);
//
//                /**
//                 * For the start location, the color of marker is GREEN and
//                 * for the end location, the color of marker is RED.
//                 */
//                if (MarkerPoints.size() == 1) {
//                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//                } else if (MarkerPoints.size() == 2) {
//                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                }
//
//
//                // Add new marker to the Google Map Android API V2
//                mMap.addMarker(options);
//
//                // Checks, whether start and end locations are captured
//                if (MarkerPoints.size() >= 2) {
//                    LatLng origin = MarkerPoints.get(0);
//                    LatLng dest = MarkerPoints.get(1);
//
//                    // Getting URL to the Google Directions API
//                    String url = getUrl(origin, dest);
//                    Log.d("onMapClick", url.toString());
//                    FetchUrl FetchUrl = new FetchUrl();
//
//                    // Start downloading json data from Google Directions API
//                    FetchUrl.execute(url);
//                    //move map camera
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
//                    mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
//                }
//
//            }
//        });
//
//    }
//
//    private String getUrl(LatLng origin, LatLng dest) {
//
//        // Origin of route
//        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
//        // Destination of route
//        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
//        // Sensor enable
//        String sensor = "sensor=false";
//        // Building the parameters to the web service
//        String parameters = str_origin + "&" + str_dest + "&" + sensor;
//        // Output format
//        String output = "json";
//        // Building the url to the web service
//        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
//
//        return url;
//    }
//
//    /**
//     * A method to download json data from url
//     */
//    private String downloadUrl(String strUrl) throws IOException {
//        String data = "";
//        InputStream iStream = null;
//        HttpURLConnection urlConnection = null;
//        try {
//            URL url = new URL(strUrl);
//            // Creating an http connection to communicate with url
//            urlConnection = (HttpURLConnection) url.openConnection();
//            // Connecting to url
//            urlConnection.connect();
//
//            // Reading data from url
//            iStream = urlConnection.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
//            StringBuffer sb = new StringBuffer();
//
//            String line = "";
//            while ((line = br.readLine()) != null) {
//                sb.append(line);
//            }
//
//            data = sb.toString();
//            Log.d("downloadUrl", data.toString());
//            br.close();
//
//        } catch (Exception e) {
//            Log.d("Exception", e.toString());
//        } finally {
//            iStream.close();
//            urlConnection.disconnect();
//        }
//        return data;
//    }
//
//    // Fetches data from url passed
//    private class FetchUrl extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... url) {
//
//            // For storing data from web service
//            String data = "";
//
//            try {
//                // Fetching the data from web service
//                data = downloadUrl(url[0]);
//                Log.d("Background Task data", data.toString());
//            } catch (Exception e) {
//                Log.d("Background Task", e.toString());
//            }
//            return data;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            ParserTask parserTask = new ParserTask();
//            // Invokes the thread for parsing the JSON data
//            parserTask.execute(result);
//        }
//    }
//
//    /**
//     * A class to parse the Google Places in JSON format
//     */
//    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
//
//        // Parsing the data in non-ui thread
//        @Override
//        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
//
//            JSONObject jObject;
//            List<List<HashMap<String, String>>> routes = null;
//
//            try {
//                jObject = new JSONObject(jsonData[0]);
//                Log.d("ParserTask",jsonData[0].toString());
//                DataParser parser = new DataParser();
//                Log.d("ParserTask", parser.toString());
//
//                // Starts parsing data
//                routes = parser.parse(jObject);
//                Log.d("ParserTask","Executing routes");
//                Log.d("ParserTask",routes.toString());
//
//            } catch (Exception e) {
//                Log.d("ParserTask",e.toString());
//                e.printStackTrace();
//            }
//            return routes;
//        }
//
//        // Executes in UI thread, after the parsing process
//        @Override
//        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
//            ArrayList<LatLng> points;
//            PolylineOptions lineOptions = null;
//
//            // Traversing through all the routes
//            for (int i = 0; i < result.size(); i++) {
//                points = new ArrayList<>();
//                lineOptions = new PolylineOptions();
//
//                // Fetching i-th route
//                List<HashMap<String, String>> path = result.get(i);
//
//                // Fetching all the points in i-th route
//                for (int j = 0; j < path.size(); j++) {
//                    HashMap<String, String> point = path.get(j);
//                    double lat = Double.parseDouble(point.get("lat"));
//                    double lng = Double.parseDouble(point.get("lng"));
//                    LatLng position = new LatLng(lat, lng);
//                    points.add(position);
//                }
//
//                // Adding all the points in the route to LineOptions
//                lineOptions.addAll(points);
//                lineOptions.width(10);
//                lineOptions.color(Color.RED);
//                Log.d("onPostExecute","onPostExecute lineoptions decoded");
//            }
//
//            // Drawing polyline in the Google Map for the i-th route
//            if(lineOptions != null) {
//                mMap.addPolyline(lineOptions);
//            }
//            else {
//                Log.d("onPostExecute","without Polylines drawn");
//            }
//        }
//    }
//
//    protected synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    public void onConnected(Bundle bundle) {
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(1000);
//        mLocationRequest.setFastestInterval(1000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//        }
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//
//        mLastLocation = location;
//        if (mCurrLocationMarker != null) {
//            mCurrLocationMarker.remove();
//        }
//
//        //Place current location marker
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("Current Position");
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//        mCurrLocationMarker = mMap.addMarker(markerOptions);
//
//        //move map camera
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
//
//        //stop location updates
//        if (mGoogleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//        }
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//
//    }
//
//    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
//    public boolean checkLocationPermission(){
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            // Asking user if explanation is needed
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//                //Prompt the user once explanation has been shown
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//            } else {
//                // No explanation needed, we can request the permission.
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//            }
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission was granted. Do the
//                    // contacts-related task you need to do.
//                    if (ContextCompat.checkSelfPermission(this,
//                            Manifest.permission.ACCESS_FINE_LOCATION)
//                            == PackageManager.PERMISSION_GRANTED) {
//                        if (mGoogleApiClient == null) {
//                            buildGoogleApiClient();
//                        }
//                        mMap.setMyLocationEnabled(true);
//                    }
//                } else {
//
//                    // Permission denied, Disable the functionality that depends on this permission.
//                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other permissions this app might request.
//            // You can add here other case statements according to your requirement.
//        }
//    }
}