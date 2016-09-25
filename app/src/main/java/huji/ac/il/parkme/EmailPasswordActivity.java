package huji.ac.il.parkme;

/**
 * Created by rotemoh on 23/08/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class EmailPasswordActivity extends BaseActivity implements
        View.OnClickListener {

    public static final String TAG = "EmailPassword";
    public final static int REQUEST_CODE_FIRST_LOGIN = 80;

    public EditText mEmailField;
    public EditText mPasswordField;
    public boolean isUserExist;

    public FirebaseAuth mAuth;
    public UserProfile userProfile;
    public DatabaseReference mDatabase;

    public FirebaseAuth.AuthStateListener mAuthListener;
    public Toolbar toolbar;

    public String newFullName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailpassword);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);

        // Buttons
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_create_account_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        isUserExist = false;

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
//                updateUI(user);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    public void createAccount(String email, String password, String fullName, String phone) {
        Log.d(TAG, "createAccount:" + email);

        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(EmailPasswordActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                        hideProgressDialog();
                    }
                });
        userProfile = new UserProfile(email, fullName, phone);
    }

    /**
     * @param email
     * @param password
     */
    public void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                                        if (child.getKey().equals(mAuth.getCurrentUser().getUid())) {
                                            isUserExist = true;
                                            newFullName = mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).child("fullName").toString();
                                            System.out.println("newFullName in: " + newFullName);
                                            System.out.println("in true exist");
                                        }
                                    }
                                    if (!isUserExist) {
                                        System.out.println("in false exist");
                                        mDatabase.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(userProfile);
                                        newFullName = userProfile.fullName;
                                        System.out.println("newFullName in: " + newFullName);
                                        Intent MainActivity = new Intent(EmailPasswordActivity.this, MainActivity.class);

                                        MainActivity.putExtra("fullName", newFullName.toString());
                                        startActivity(MainActivity);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                            Intent MainActivity = new Intent(EmailPasswordActivity.this, MainActivity.class);
                            startActivity(MainActivity);
                        }

                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });

        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }


    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_create_account_button) {
            Intent addFirstLoginPage = new Intent(EmailPasswordActivity.this, FirstLoginActivity.class);
            startActivityForResult(addFirstLoginPage, REQUEST_CODE_FIRST_LOGIN);
            //startActivity(addFirstLoginPage);

            //todo: move below call to inside a onClick func of CREATE_ACCOUNT btn in the above activity:
            //createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());

        } else if (i == R.id.email_sign_in_button) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());

        } else if (i == R.id.sign_out_button) {
            signOut();
        }
    }

    //todo: delete???
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_FIRST_LOGIN) {
            if (data.hasExtra("emailF") && data.hasExtra("passwordF")
                    && data.hasExtra("phoneF") && data.hasExtra(("fullNameF"))) {
                //return to "EmailPasswordActivity" with the email & password
                //which were typed in the "FirstLoginActivity"
                mEmailField.setText(data.getStringExtra("emailF"));
                mPasswordField.setText(data.getStringExtra("passwordF"));
                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString(),
                        data.getStringExtra("fullNameF"), data.getStringExtra("phoneF"));
                //Intent MainActivity = new Intent(EmailPasswordActivity.this, MainActivity.class);
                //MainActivity.putExtra("fullName", data.getStringExtra("fullNameF"));
                //startActivity(MainActivity);
            }
        }
    }
}