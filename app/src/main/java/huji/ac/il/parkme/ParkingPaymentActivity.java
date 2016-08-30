package huji.ac.il.parkme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ParkingPaymentActivity extends AppCompatActivity {
    String address;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_payment);
        Button routBtn = (Button) findViewById(R.id.rout_btn);
        Button homeBtn = (Button) findViewById(R.id.home_btn);
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
                startActivity(homeIntent);
            }
        });
    }
}
