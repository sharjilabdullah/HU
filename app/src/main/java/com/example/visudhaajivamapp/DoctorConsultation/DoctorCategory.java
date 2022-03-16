package com.example.visudhaajivamapp.DoctorConsultation;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.visudhaajivamapp.R;
import com.example.visudhaajivamapp.adapter.DoctorCatAdapter;
import com.example.visudhaajivamapp.adapter.ProductCatAdapter;
import com.example.visudhaajivamapp.addedbysam.ProductCategory;
import com.example.visudhaajivamapp.models.Category;
import com.example.visudhaajivamapp.models.DoctorsCat;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class DoctorCategory extends AppCompatActivity {

    RecyclerView recyclerView;
    DoctorCategory doctorCategory;
    ArrayList<DoctorsCat>CategoryList;
    ImageButton back_icon;
    private static String JSON_URL = "https://visudhajivam.in/android/doct_category.php";
    //Adapter adapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    SwipeRefreshLayout swipeLayout;
    NestedScrollView nestedScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_category);
        mShimmerViewContainer = findViewById(R.id.mShimmerViewContainer);
        back_icon=findViewById(R.id.back_icon);
        swipeLayout = findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(false);

            }
        });
        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.Categoryrecycleview);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));



        nestedScrollView = findViewById(R.id.nestedScrollView);
        CategoryList = new ArrayList<>();
        extractSongs();

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorCategory = null;
                onBackPressed();
            }
        });
    }

    private void extractSongs() {
        nestedScrollView.setVisibility(View.GONE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(String response) {
                        Log.d("JsonArayyyyyyyyyyyyyofcategory", response);
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray( URLDecoder.decode( response, "UTF-8" ));

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);


                                CategoryList.add(new DoctorsCat(
                                        product.getInt("id"),
                                        product.getString("name"),
                                        product.getString("description"),
                                        product.getString("photo")

                                ));


                                //adding the product to product list

                            }

                            //creating adapter object and setting it to recyclerview
                            DoctorCatAdapter adapter = new DoctorCatAdapter(DoctorCategory.this, CategoryList,R.layout.doctor_category_lyt);
                            recyclerView.setAdapter(adapter);
                            nestedScrollView.setVisibility(View.VISIBLE);
                            mShimmerViewContainer.stopShimmer();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        } catch (JSONException | UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


}