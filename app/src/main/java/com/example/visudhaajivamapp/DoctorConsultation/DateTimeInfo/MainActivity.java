package com.example.visudhaajivamapp.DoctorConsultation.DateTimeInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.visudhaajivamapp.DoctorConsultation.Specialist;
import com.example.visudhaajivamapp.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    DocterModel docterModel;

    EditText name1,EmailAddress1,number1,prblm;
    Button sbmt,datetime;
    TextView DateText;
    ImageView docphoto, backbutt;
    TextView TimeText,doc_name,doc_disc;
    String price,name,photo,exp,id;

    private static final String url="https://visudhajivam.in/android/bookaptverify.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main_doc_datetime);
        name1=(EditText) findViewById(R.id.name1);
        EmailAddress1=(EditText)findViewById(R.id.EmailAddress1);
        number1=(EditText)findViewById(R.id.number1);
        prblm=(EditText)findViewById(R.id.prblm);
        doc_disc=findViewById(R.id.doc_disc);
        doc_name=findViewById(R.id.doc_name);
        docphoto=findViewById(R.id.pic_doc);
        backbutt = findViewById(R.id.back_icon);
        //
        DateText=findViewById(R.id.datein);
        TimeText=findViewById(R.id.time);
        Intent intentreceiver=getIntent();
        if (intentreceiver.hasExtra("DATE")) {
            DateText.setText(intentreceiver.getStringExtra("DATE"));
            TimeText.setText(intentreceiver.getStringExtra("TIME"));
            price=intentreceiver.getStringExtra("price");
            name=intentreceiver.getStringExtra("name");
            photo=intentreceiver.getStringExtra("photo");
            exp=intentreceiver.getStringExtra("exp");
            id=intentreceiver.getStringExtra("id");
            doc_name.setText(name);
            doc_disc.setText(exp);

            Glide.with(MainActivity.this)
                    .load(photo)
                    .into(docphoto);
        }

        //DateTym
        //datetime=(Button)findViewById(R.id.availdate);


        //Submit Button
        sbmt=(Button)findViewById(R.id.getappoint);
        sbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });
        backbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void insert() {


         String nme=name1.getText().toString().trim();
         String email=EmailAddress1.getText().toString().trim();
         String nmb=number1.getText().toString().trim();
        final String prb=prblm.getText().toString().trim();

        StringRequest request= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                if(response.equals("success")){
                    fillDetails();
                    if (!nme.equals("") && !email.equals("") && !nmb.equals("") && !prb.equals("")) {
                        Intent intent = new Intent(MainActivity.this, ConfirmOrderDocterActivity.class);
                        intent.putExtra("DOCTER_MODEL", docterModel);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "sucess", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Please Fill All Details",Toast.LENGTH_LONG).show();
                    }
                }
               /* name1.setText("");
                EmailAddress1.setText("");
                number1.setText("");
                prblm.setText(""); */

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String, String>();
                param.put("other",prblm.getText().toString());
                param.put("date",DateText.getText().toString());
                param.put("time",TimeText.getText().toString());
                param.put("mobile",number1.getText().toString());
                param.put("email",EmailAddress1.getText().toString() );
                param.put("name",name);
                param.put("price",price );
                param.put("docid",id);
                param.put("razorpay_payment_id","567556"  );
                Log.d("name",name1.getText().toString()+","+EmailAddress1.getText().toString()+","+number1.getText().toString()+prblm.getText().toString());
                return param;
            }

        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void fillDetails() {
        docterModel=new DocterModel();
        docterModel.setDocName(name);
        docterModel.setDocDesc(exp);
        docterModel.setDocFees(price);
        docterModel.setUserName(name1.getText().toString());
        docterModel.setUserEmail(EmailAddress1.getText().toString());
        docterModel.setSelectedDate(DateText.getText().toString());
        docterModel.setSelectedTime(TimeText.getText().toString());
        docterModel.setUserMobile(number1.getText().toString());
        docterModel.setUserProblem(prblm.getText().toString());
    }
}