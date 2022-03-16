package com.example.visudhaajivamapp.DoctorConsultation.DateTimeInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.visudhaajivamapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerActivity extends AppCompatActivity {
    final String DATE_TIME_URL = "https://visudhajivam.in/android/gettime.php";
    RecyclerView dateTimeRecycler;
    ArrayList<DateModel> dateModel;
    DateAndTimeAdapter dateAndTimeAdapter;
    ImageView backbutt;
    String id, price,name,photo,exp;
    private ShimmerFrameLayout mShimmerViewContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        backbutt = findViewById(R.id.back_icon);
        dateTimeRecycler = findViewById(R.id.date_recycler_id);
        dateTimeRecycler.setLayoutManager(new LinearLayoutManager(this));
        mShimmerViewContainer = findViewById(R.id.mShimmerViewContainer);
        if (dateTimeRecycler.getItemDecorationCount() == 0) {
            //If decoration is not there in recycler view
            DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            decoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.date_divider));
            dateTimeRecycler.addItemDecoration(decoration);
        }
        backbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Fetching data from server and inserting it into dateModel array.
        id = getIntent().getStringExtra("id");
        price = getIntent().getStringExtra("price");
        name = getIntent().getStringExtra("name");
        photo = getIntent().getStringExtra("photo");
        exp = getIntent().getStringExtra("exp");
        dateTime();
    }

    private void dateTime() {
        dateTimeRecycler.setVisibility(View.GONE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DATE_TIME_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("Response",response);
                            JSONArray jsonArray = new JSONArray(URLDecoder.decode(response, "UTF-8"));
                            dateModel = new ArrayList<>();
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONArray jsonArray2 = jsonArray.getJSONArray(i);
                                DateModel model = new DateModel();
                                model.setDate((String) jsonArray2.get(0));
                                JSONArray jsonArray3 = jsonArray2.getJSONArray(1);
                                Log.i("size",String.valueOf(jsonArray3.length()));
                                ArrayList<String> timeArray = new ArrayList<>();
                                for(int j=0;j<jsonArray3.length();j++)
                                {
                                    timeArray.add((String) jsonArray3.get(j));
                                    Log.i("time",(String)jsonArray3.get(j));
                                }
                                model.setTimeList(timeArray);
                                dateModel.add(model);
                            }

                            for(int k=0;k<dateModel.size();k++)
                            {
                                Log.i("Data",dateModel.get(k).getDate());
                                for(int l=0;l<dateModel.get(k).timeList.size();l++)
                                {
                                    Log.i("Time",dateModel.get(k).timeList.get(l));
                                }
                            }

                            dateAndTimeAdapter = new DateAndTimeAdapter(RecyclerActivity.this,dateModel,price,name,photo,exp,id);
                            dateTimeRecycler.setAdapter(dateAndTimeAdapter);
                            mShimmerViewContainer.stopShimmer();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            dateTimeRecycler.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prams = new HashMap<>();
                prams.put("id", id);

                return prams;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}