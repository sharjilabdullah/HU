package com.example.visudhaajivamapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OnBoardingFragment3 extends Fragment {

    FloatingActionButton fab;
    private boolean loggedIn=false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_on_boarding3,container,false);

        fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("tech", Context.MODE_PRIVATE);
                loggedIn = sharedPreferences.getBoolean("loggedin", false);
                if(loggedIn){
                    Intent intent = new Intent(v.getContext(), DashboardMain.class);
                    startActivity(intent);
                }else {

                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
            }
        });

        return root;
    }
}
