package com.decimalab.sff.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.decimalab.sff.R;
import com.decimalab.sff.ui.OtpActivity;
import com.google.firebase.auth.FirebaseAuth;


public class DashFragment extends Fragment {

    String phoneNumber;
    TextView mobileNumber;
    Button btnLogOut;


    private RecyclerView recyclerview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_dash, container, false);


        mobileNumber = view.findViewById(R.id.mobileNumber);
        btnLogOut = view.findViewById(R.id.buttonLogout);
        recyclerview = view.findViewById(R.id.rv_dash_colleges);

        mobileNumber.setText(phoneNumber);


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                // TODO: clear sharePreference
                Intent intent = new Intent(getActivity(), OtpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


}