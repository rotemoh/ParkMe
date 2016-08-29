package huji.ac.il.parkme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ParkingInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_info);
        Intent intent = getIntent();
        String itemValue = intent.getStringExtra("itemValue");
        TextView editText = (TextView) findViewById(R.id.address_txt);
        editText.setText(itemValue);
    }

}
