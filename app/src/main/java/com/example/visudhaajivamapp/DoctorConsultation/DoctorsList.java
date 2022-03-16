package com.example.visudhaajivamapp.DoctorConsultation;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.visudhaajivamapp.HelperClasses.Session;
import com.example.visudhaajivamapp.R;
import com.example.visudhaajivamapp.adapter.DoctorSubCatAdapter;
import com.example.visudhaajivamapp.adapter.ProductSubCAdapter;
import com.example.visudhaajivamapp.addedbysam.AllSubCategory;
import com.example.visudhaajivamapp.addedbysam.CartListActivity;
import com.example.visudhaajivamapp.models.DoctorsSubCat;
import com.example.visudhaajivamapp.models.SubCategory;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorsList extends AppCompatActivity {
    String url = "https://visudhajivam.in/android/doctor.php";
    List<DoctorsSubCat> productList;
    SubCategory prosub;
    //the recyclerview
    RecyclerView recyclerView;
    ImageView backbutt;
    SwipeRefreshLayout swipeLayout;
    private ShimmerFrameLayout mShimmerViewContainer;
    NestedScrollView nestedScrollView;
    public static DoctorsList doctorsList;
    String pin,typename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);
        //resource = R.layout.sub_category_list;
        backbutt = findViewById(R.id.back_icon);
        recyclerView = findViewById(R.id.doclist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        typename = getIntent().getStringExtra("typename");
        pin = getIntent().getStringExtra("pin");
        Log.d("pinnnnnnnnn",pin);
        Log.d("typenameeee",typename);
        //initializing the productlist
        productList = new ArrayList<>();

        swipeLayout = findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(false);

            }
        });
        //tvAlert = findViewById(R.id.tvAlert);
        mShimmerViewContainer = findViewById(R.id.mShimmerViewContainer);
        backbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               doctorsList = null;
                onBackPressed();
            }
        });


        loadProducts();
    }

    private void loadProducts() {
        recyclerView.setVisibility(View.GONE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("REsponseFRomKarannn", response);
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(URLDecoder.decode( response, "UTF-8" ));

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);


                                //adding the product to product list
                                productList.add(new DoctorsSubCat(
                                        product.getInt("id"),
                                        product.getString("name"),
                                        product.getString("exp"),
                                        product.getString("address"),
                                        product.getString("owner_photo"),
                                        product.getString("speciality"),
                                        product.getString("price")

                                ));

                            }

                            //Log.d("ProductCategoryy", String.valueOf(prosub.getCategory_id()));

                            DoctorSubCatAdapter adapter = new DoctorSubCatAdapter(DoctorsList.this, productList, R.layout.doctor_subcat_lyt);
                            recyclerView.setAdapter(adapter);
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
                }){



            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prams = new HashMap<>();
                prams.put("pin",pin);
                prams.put("typename",typename);

                return prams;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}