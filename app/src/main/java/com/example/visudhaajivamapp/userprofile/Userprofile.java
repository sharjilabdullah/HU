package com.example.visudhaajivamapp.userprofile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.visudhaajivamapp.R;
import com.google.gson.Gson;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;

public class Userprofile extends AppCompatActivity {
    TextView name , email, aadhaar, mobile, address, blood, dob, expiry, identity ;
    ImageView profileimg,backbutt;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile);

        name = findViewById(R.id.profilename);
        email = findViewById(R.id.mail1);
        aadhaar = findViewById(R.id.aadhaar1);
        mobile = findViewById(R.id.nmbr1);
        address = findViewById(R.id.address1);
        blood = findViewById(R.id.blood1);
        dob = findViewById(R.id.dob1);
        expiry = findViewById(R.id.expcard1);
        identity = findViewById(R.id.identity1);
        profileimg = findViewById(R.id.profileimage);
        backbutt = findViewById(R.id.back_icon);
        new MaterialIntroView.Builder(this)
                .enableDotAnimation(false)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.NORMAL)
                .setDelayMillis(0)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText("Hi There! Click this to go back to Main Dashboard")
                .setShape(ShapeType.CIRCLE)
                .setTarget(backbutt)
                .setUsageId("intro_card_back") //THIS SHOULD BE UNIQUE ID
                .show();
        backbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SharedPreferences sharedPreferences = Userprofile.this.getSharedPreferences("tech", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        UserDetails user = gson.fromJson(sharedPreferences.getString("UserDetails",""), UserDetails.class);

        name.setText(user.getName());
        email.setText(user.getUser_name());
        aadhaar.setText(user.getAadhar());
        mobile.setText(user.getPhone());
        blood.setText(user.getBlood());
        dob.setText(user.getDob());
        expiry.setText(user.getExpiry());
        identity.setText(user.getIdentity());
        address.setText(user.getAddress()+ ", " + user.getDist() + ", " + user.getCountry() + ", " + user.getPincode());
        Glide.with(this)
                .load("https://visudhajivam.in/"+user.getPhoto())
                .into(profileimg);
    }
}
