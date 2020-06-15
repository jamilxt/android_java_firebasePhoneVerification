package com.decimalab.sff.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.decimalab.sff.R;


public class InfoFragment extends Fragment {


    private FragmentActivity myContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_info, container, false);


        (view.findViewById(R.id.fab_edit_info)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager manager = myContext.getSupportFragmentManager();

                manager.beginTransaction().replace(R.id.fragment_container, new EditInfoFragment()).commit();

            }
        });

        return view;


    }


    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

}