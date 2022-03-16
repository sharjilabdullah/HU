package com.example.visudhaajivamapp.docterprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.visudhaajivamapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DocterInfoActivity extends AppCompatActivity {
    private final String SPECIALITY_URL = "https://visudhajivam.in/android/speciality.php";
    private final String DATE_TIME_URL = "https://visudhajivam.in/android/doc_ava_date_time.php";
    EditText specialityText,priceText,startDateText,endDateText,startTimeText,endTimeText;
    Button docterUpdateButton,timeUpdateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_info);

        specialityText = findViewById(R.id.speciality_type);
        priceText = findViewById(R.id.pricedoc);
        startDateText = findViewById(R.id.startdateinput);
        startTimeText =findViewById(R.id.starttime_input);
        endDateText = findViewById(R.id.Enddate_input);
        endTimeText = findViewById(R.id.Endtime_input);
        docterUpdateButton = findViewById(R.id.docter_update);
        timeUpdateButton = findViewById(R.id.date_update);


        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabe1();

            }

            private void updateLabe1() {
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                startDateText.setText(sdf.format(myCalendar.getTime()));

            }
        };
        startDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DocterInfoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        DatePickerDialog.OnDateSetListener dates = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabe2();

            }



            private void updateLabe2() {
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                endDateText.setText(sdf.format(myCalendar.getTime()));

            }
        };


        endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DocterInfoActivity.this, dates, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // **************************************** Rohit Work(Server Part Starts)******************************************


        //**************** Sending Docter data to server****************
        docterUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkDocterFields()){
                    Toast.makeText(DocterInfoActivity.this,"Fill All Fields of Docter",Toast.LENGTH_SHORT).show();
                    return;
                }

                StringRequest request= new StringRequest(Request.Method.POST, SPECIALITY_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("rresponse",response);

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
                        param.put("id","37");// send the docter id
                        param.put("speciality",specialityText.getText().toString()); // send docter speciality
                        param.put("price",priceText.getText().toString()); // send docter price
                        return param;
                    }

                };
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                queue.add(request);

            }
        });

        //**************Sending Date information to server*******************

        timeUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkDateAndTimeFields()){
                    Toast.makeText(DocterInfoActivity.this,"Fill All Date And Time Fields",Toast.LENGTH_SHORT).show();
                    return;
                }

                StringRequest request= new StringRequest(Request.Method.POST, DATE_TIME_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",response);

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
                        param.put("id","37");// send the docter id
                        param.put("savdt",startDateText.getText().toString()); //start date
                        param.put("eavdt",endDateText.getText().toString()); //end date
                        param.put("savtm",startTimeText.getText().toString());// starting time
                        param.put("eavtm",endTimeText.getText().toString());// ending time
                        return param;
                    }

                };
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                queue.add(request);

            }
        });
    }

    private boolean checkDocterFields(){
        if(specialityText.getText().toString().trim().equals("")){
            return false;
        }
        if(priceText.getText().toString().trim().equals("")){
            return false;
        }
        return true;
    }

    private boolean checkDateAndTimeFields(){
        if(startDateText.getText().toString().trim().equals(""))
        {
            return false;
        }
        if(endDateText.getText().toString().trim().equals(""))
        {
            return false;
        }
        if(startTimeText.getText().toString().trim().equals(""))
        {
            return false;
        }
        if(endTimeText.getText().toString().trim().equals(""))
        {
            return false;
        }
        return true;
    }
}