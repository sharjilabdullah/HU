package com.example.visudhaajivamapp.DoctorConsultation.DateTimeInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.visudhaajivamapp.R;

public class Extra extends AppCompatActivity {
    TextView feeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
        feeText=findViewById(R.id.last);
        Intent receiver1= getIntent();
        feeText.setText(receiver1.getStringExtra("FEE"));
        //receiver1.getStringExtra("FEE");

    }
}