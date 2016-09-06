package huji.ac.il.parkme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Objects;

/**
 * Created by rotemoh on 23/08/2016.
 */
public class FirstLoginActivity extends AppCompatActivity {

    private EditText fullName;
    private EditText email;
    private EditText phone;
    private EditText password;
    private EditText rePassword;
    private EmailPasswordActivity EPActivity;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_login_activity);
        final Intent data = new Intent();
        EPActivity = new EmailPasswordActivity();

        fullName = (EditText) findViewById(R.id.full_name_input);
        email = (EditText) findViewById(R.id.email_input);
        phone = (EditText) findViewById(R.id.phone_input);
        password = (EditText) findViewById(R.id.password_input);
        rePassword = (EditText) findViewById(R.id.re_password_input);


        findViewById(R.id.sign_in_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()) {
                    return;
                }
                //EPActivity.onCreate(savedInstanceState);
                //EPActivity.createAccount(email.getText().toString(), password.getText().toString());
                data.putExtra("emailF", email.getText().toString());
                data.putExtra("passwordF", password.getText().toString());
                data.putExtra("phoneF", phone.getText().toString());
                data.putExtra("fullNameF", fullName.getText().toString());

                // Activity finished ok, return the data
                setResult(RESULT_OK, data);
                //EPActivity.signIn(email.getText().toString(), password.getText().toString(), EPActivity.FirstLoginActivityID);
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private boolean validateForm() {
        boolean valid = true;

        String fullNameF = fullName.getText().toString();
        if (TextUtils.isEmpty(fullNameF)) {
            fullName.setError("Required.");
            valid = false;
        } else {
            fullName.setError(null);
        }

        String emailF = email.getText().toString();
        if (TextUtils.isEmpty(emailF)) {
            email.setError("Required.");
            valid = false;
        } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailF).matches()) {
            email.setError("A valid email is required.");
            valid = false;
        } else {
            email.setError(null);
        }


        String phoneF = phone.getText().toString();
        if (TextUtils.isEmpty(phoneF)) {
            phone.setError("Required.");
            valid = false;
        } else if(phoneF.length() < 10) {
            phone.setError("Phone must be 10 characters.");
            valid = false;
        } else {
            phone.setError(null);
        }

        String passwordF = password.getText().toString();
        if (TextUtils.isEmpty(passwordF)) {
            password.setError("Required.");
            valid = false;
        } else if(passwordF.length() < 6) {
            password.setError("Password must be at least 6 characters.");
            valid = false;
        } else {
            password.setError(null);
        }

        String rePasswordF = rePassword.getText().toString();
        if (TextUtils.isEmpty(rePasswordF)) {
            rePassword.setError("Required.");
            valid = false;
        } else if (!passwordF.equals(rePasswordF)) {
            rePassword.setError("Password doesn't match.");
            valid = false;
        } else {
            rePassword.setError(null);
        }
        return valid;
    }


    @Override
    public void onStop() {
        super.onStop();
    }
}
