package com.example.visudhaajivamapp.DoctorConsultation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.visudhaajivamapp.DoctorConsultation.DateTimeInfo.RecyclerActivity;
import com.example.visudhaajivamapp.R;

public class Specialist extends AppCompatActivity {
    TextView text,text1,text2,text3,text4;
    ImageView photo;
    String id;
    Button bookappoint;
    ImageView backbutt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist);
        text  = findViewById(R.id.doctorspeciality);
        text1 = findViewById(R.id.doctoraddress);
        text2  = findViewById(R.id.doctorprice);
        text3  = findViewById(R.id.doctorname);
        text4  = findViewById(R.id.doctorexperience);
        bookappoint=findViewById(R.id.bookappointment);
        photo=findViewById(R.id.doctorphoto);
        backbutt = findViewById(R.id.back_icon);
        text.setText(getIntent().getStringExtra("speciality"));
        text1.setText(getIntent().getStringExtra("address"));
        text2.setText("₹ "+getIntent().getStringExtra("price"));
        text3.setText(getIntent().getStringExtra("name"));
        text4.setText(getIntent().getStringExtra("experience"));
        Glide.with(Specialist.this)
                .load(getIntent().getStringExtra("photo"))
                .into(photo);
        backbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bookappoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Specialist.this, RecyclerActivity.class);
                intent.putExtra("id",getIntent().getStringExtra("id"));
                intent.putExtra("price",getIntent().getStringExtra("price"));
                intent.putExtra("name",getIntent().getStringExtra("name"));
                intent.putExtra("photo",getIntent().getStringExtra("photo"));
                intent.putExtra("exp",getIntent().getStringExtra("address"));
                startActivity(intent);
            }
        });

    }
}