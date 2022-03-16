package com.example.visudhaajivamapp.addedbysam;

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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.visudhaajivamapp.DashboardMain;
import com.example.visudhaajivamapp.HelperClasses.Session;
import com.example.visudhaajivamapp.R;
import com.example.visudhaajivamapp.adapter.ProductSubCAdapter;
import com.example.visudhaajivamapp.models.SubCategory;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllSubCategory extends AppCompatActivity {

    public static AllSubCategory allSubCategory;
    private static final String URL_PRODUCTS = "https://visudhajivam.in/android/orders.php";
    //private static final String URL= "https://visudhajivam.in/android/orders.php";
    //a list to store all the products
    List<SubCategory> productList;
    SubCategory prosub;
    //the recyclerview
    RecyclerView recyclerView;
    boolean isGrid = false;
    int resource;
    Activity activity;
    Session session;
    ImageView backbutt,cart;


    SwipeRefreshLayout swipeLayout;
    private ShimmerFrameLayout mShimmerViewContainer;
    NestedScrollView nestedScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_subcategory);
        backbutt = findViewById(R.id.back_icon);

        activity= AllSubCategory.this;
        session = new Session(activity);



       /* if (session.getGrid("grid")) {
            resource = R.layout.lyt_item_grid;
            isGrid = true;

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));

        } else {
            resource = R.layout.sub_category_list;
            isGrid = false;

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        */
        cart=findViewById(R.id.cart_allsub);
        resource = R.layout.sub_category_list;

        recyclerView = findViewById(R.id.allprodrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //getting the recyclerview from xml

        //recyclerView = findViewById(R.id.recylcerView);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                allSubCategory = null;
                onBackPressed();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allSubCategory = AllSubCategory.this;
                Intent refresh = new Intent(AllSubCategory.this, CartListActivity.class);
                refresh.putExtra("FromCartIcon","FromCartIcon");
                startActivity(refresh);
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                Log.d("REsponseFRomKarannn", String.valueOf(product));

                                //adding the product to product list
                                productList.add(new SubCategory(
                                        product.getInt("id"),
                                        product.getString("name"),
                                        product.getDouble("price"),
                                        product.getDouble("discounted_price"),
                                        product.getInt("category_id"),
                                        product.getInt("subcategory_id"),
                                        product.getString("manufacturer"),
                                        product.getString("made_in"),
                                        product.getInt("stock"),
                                        product.getInt("product_id"),
                                        product.getString("image")
                                ));

                            }

                            //Log.d("ProductCategoryy", String.valueOf(prosub.getCategory_id()));

                            ProductSubCAdapter adapter = new ProductSubCAdapter(AllSubCategory.this, productList, resource);
                            recyclerView.setAdapter(adapter);
                            mShimmerViewContainer.stopShimmer();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

}
