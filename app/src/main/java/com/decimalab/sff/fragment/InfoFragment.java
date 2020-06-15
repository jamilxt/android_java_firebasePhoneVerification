package com.decimalab.sff.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.decimalab.sff.R;
import com.decimalab.sff.ui.OtpActivity;
import com.google.firebase.auth.FirebaseAuth;


public class InfoFragment extends Fragment {


    private FragmentActivity fragmentActivity;
    View view;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_info, container, false);

        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });


        (view.findViewById(R.id.fab_edit_info)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager manager = fragmentActivity.getSupportFragmentManager();

                manager.beginTransaction().replace(R.id.fragment_container, new EditInfoFragment()).commit();

            }
        });

        return view;
    }

    private void logOut() {

        FirebaseAuth.getInstance().signOut();

        // TODO: clear sharePreference
        Intent intent = new Intent(view.getContext(), OtpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}