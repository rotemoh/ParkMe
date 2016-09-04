package huji.ac.il.parkme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ParkingInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_info);
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        TextView editText = (TextView) findViewById(R.id.address_txt);
        editText.setText(address);
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        Button continueBtn = (Button) findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkingInfoActivity.this, ParkingPaymentActivity.class);
                startActivity(intent);
            }
        });
    }

}
