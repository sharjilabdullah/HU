package com.example.visudhaajivamapp.DoctorConsultation;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.visudhaajivamapp.R;

public class PincodeandCat extends AppCompatActivity {
    EditText e1;
    Button b1;
    String pin,typename;
    //ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincodeand_cat);
        TextView text = findViewById(R.id.speciality);
        TextView text1 = findViewById(R.id.description);
        e1 = (EditText)findViewById(R.id.pincode);
        b1 = (Button)findViewById(R.id.button);
        typename = getIntent().getStringExtra("name1");
        Log.d("TAG", "onCreate: intent received "+typename);
        text.setText(getIntent().getStringExtra("name1"));
        text1.setText(getIntent().getStringExtra("description"));

        Log.d("TAG", "onCreate: data setttttt "+typename);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin = e1.getText().toString();

                Intent intent = new Intent(v.getContext(),DoctorsList.class );

                intent.putExtra("pin", pin);
                intent.putExtra("typename", typename);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivities(new Intent[]{intent});

            }
        });

    }
}