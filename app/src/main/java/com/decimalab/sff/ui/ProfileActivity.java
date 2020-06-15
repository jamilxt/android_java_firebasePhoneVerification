package com.decimalab.sff.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.decimalab.sff.R;
import com.decimalab.sff.util.Constants;
import com.decimalab.sff.util.SharedPrefUtils;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    String phoneNumber;
    TextView mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        phoneNumber = SharedPrefUtils.getCurrentUserID(ProfileActivity.this);

        mobileNumber = findViewById(R.id.mobileNumber);
        mobileNumber.setText(phoneNumber);

        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(ProfileActivity.this, OtpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        boolean isUserExist = Constants.isUserExist;
        if (isUserExist) {
            Toast.makeText(this, "Old User", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "New User", Toast.LENGTH_LONG).show();
        }
    }
}
