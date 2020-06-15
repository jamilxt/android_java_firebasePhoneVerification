package com.decimalab.sff.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.decimalab.sff.R;
import com.decimalab.sff.fragment.EditInfoFragment;
import com.decimalab.sff.fragment.InfoFragment;
import com.decimalab.sff.fragment.PaymentFragment;
import com.decimalab.sff.util.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class DashboardActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;

    Button btnLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        btnLogOut = findViewById(R.id.buttonLogout);


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logOut();
            }
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_menus);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        boolean isUserExist = Constants.isUserExist;
        if (isUserExist) {
            Toast.makeText(this, "Old User", Toast.LENGTH_LONG).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InfoFragment()).commit();
        } else {
            Toast.makeText(this, "New User", Toast.LENGTH_LONG).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditInfoFragment()).commit();
            bottomNavigationView.getMenu().getItem(1).setEnabled(false);
        }
    }

    private void logOut() {

        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, OtpActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selected_fragment = null;
            switch (menuItem.getItemId()) {

                case R.id.info:
                    selected_fragment = new InfoFragment();
                    break;
                case R.id.payment:
                    selected_fragment = new PaymentFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selected_fragment).commit();

            return true;
        }
    };
}
