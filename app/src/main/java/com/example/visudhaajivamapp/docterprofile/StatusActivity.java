package com.example.visudhaajivamapp.docterprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.visudhaajivamapp.R;

import java.util.ArrayList;

public class StatusActivity extends AppCompatActivity {
    TextView nameStatus,emailStatus,phoneStatus,detailsStatus,othersStatus;
    Button acceptStatus,declineStatus;
    RecyclerView statusRecycler;
    ArrayList<AppointmentModel> appointmentModelsList;
    StatusAdapter statusAdapter;
    String typeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        statusRecycler = findViewById(R.id.status_recycler);

        Intent intent = getIntent();
        if(intent.hasExtra("APP_MODEL_LIST")){
            appointmentModelsList = (ArrayList<AppointmentModel>) intent.getSerializableExtra("APP_MODEL_LIST");
            typeName = intent.getStringExtra("ListType");
        }

        statusRecycler.setLayoutManager(new LinearLayoutManager(this));
        statusAdapter = new StatusAdapter(StatusActivity.this,appointmentModelsList,typeName);
        statusRecycler.setAdapter(statusAdapter);
        
    }
}