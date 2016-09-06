package huji.ac.il.parkme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AvailableParkingListActivity extends AppCompatActivity {
    ListView listView;
//    String address;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_parking_list);
        listView = (ListView) findViewById(R.id.listViewParking);

        // Defined Array values to show in ListView
        //TODO: change to the addresses in the area. will get it from the DB.
        final String[] values = new String[] {"Android List View",
                "Gilboa 94 Alfei Menashe",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"};
        final String[] addresses = new String[] {"address 1, cost 1",
                "address 2, cost 2",
                "address 3, cost 3",
                "address 4, cost 4",
                "address 5, cost 5",
                "address 6, cost 6",
                "address 7, cost 7",
                "address 8, cost 8"};
        Intent intent = getIntent();
//        address = intent.getStringExtra("address");

        // Define a new Adapter:
        //Context, Layout for the row, ID of the TextView to which
        // the data is written and the Array of data.
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_2, android.R.id.text1, values) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(values[position]);
                text2.setText(addresses[position]);
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
    }
}