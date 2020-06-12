package com.decimalab.sff;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.decimalab.sff.R;
import com.decimalab.sff.fragments.DashFragment;
import com.decimalab.sff.fragments.InfoFragment;
import com.decimalab.sff.fragments.PaymentFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class HomeActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_menus);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashFragment()).commit();


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selected_fragment = null;
            switch (menuItem.getItemId()) {
                case R.id.dash:
                    selected_fragment = new DashFragment();
                    break;
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
