package com.decimalab.sff.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.decimalab.sff.R;
import com.decimalab.sff.phoneotp.EnterNumberActivity;
import com.google.firebase.auth.FirebaseAuth;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;


public class DashFragment extends Fragment {

    String phoneNumber;
    TextView mobileNumber;
    Button btnLogOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_dash, container, false);


        mobileNumber = view.findViewById(R.id.mobileNumber);
        btnLogOut = view.findViewById(R.id.buttonLogout);

        mobileNumber.setText(phoneNumber);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getActivity(), EnterNumberActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get saved phone number
        SharedPreferences prefs = getActivity().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
        phoneNumber = prefs.getString("phoneNumber", NULL);

    }


}