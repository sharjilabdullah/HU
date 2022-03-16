package com.example.visudhaajivamapp.addedbysam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.visudhaajivamapp.DashboardMain;
import com.example.visudhaajivamapp.LoginActivity;
import com.example.visudhaajivamapp.R;
import com.example.visudhaajivamapp.adapter.ProductCatAdapter;
import com.example.visudhaajivamapp.adapter.ProductSubCAdapter;
import com.example.visudhaajivamapp.models.Category;
import com.example.visudhaajivamapp.models.SubCategory;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;


public class ProductCategory extends AppCompatActivity {

    public static ProductCategory productCategory;

    private static final String URL_Category = "https://visudhajivam.in/android/category.php";
    private static final String URL_PRODUCTS = "https://visudhajivam.in/android/orders.php";

    //a list to store all the products
    ArrayList<SubCategory> productList;
    ArrayList<Category>CategoryList;
    NestedScrollView nestedScrollView;
    //the recyclerview
    RecyclerView subcrecyclerView,recyclerView;
    ImageButton morebutt, backbutt;
    private ShimmerFrameLayout mShimmerViewContainer;
    int resource1, resource2;
    SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_category);
        mShimmerViewContainer = findViewById(R.id.mShimmerViewContainer);

        morebutt=findViewById(R.id.more_icon);
        backbutt = findViewById(R.id.back_icon);
        //resource1 = R.layout.lyt_item_grid;
        //isGrid = true;

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


        //subcrecyclerView = findViewById(R.id.recyclerView);
        //subcrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        nestedScrollView = findViewById(R.id.nestedScrollView);


        //recyclerView = findViewById(R.id.Categoryrecycleview);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        //initializing the productlist
        productList = new ArrayList<>();
        CategoryList = new ArrayList<>();



        new MaterialIntroView.Builder(this)
                .enableDotAnimation(false)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.NORMAL)
                .setDelayMillis(500)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText("Click this to get ALL MEDICINES")
                .setShape(ShapeType.CIRCLE)
                .setTarget(morebutt)
                .setUsageId("intro_card_moreincategory") //THIS SHOULD BE UNIQUE ID
                .show();

        new MaterialIntroView.Builder(this)
                .enableDotAnimation(true)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(500)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText("Click here to go back  to Dashboard")
                .setShape(ShapeType.CIRCLE)
                .setTarget(backbutt)
                .setUsageId("intro_card_backincategory") //THIS SHOULD BE UNIQUE ID
                .show();

        //int categoryId = getIntent().getIntExtra("id",0);
        //this method will fetch and parse json
        //to display it in recyclerview

        morebutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(ProductCategory.this, morebutt);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu2);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.allmed)
                        {
                            productCategory = ProductCategory.this;
                            startActivity(new Intent(getApplicationContext(), AllSubCategory.class));
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

            }
        });

        backbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productCategory = null;
                onBackPressed();
            }
        });


        loadCategory();
        //loadProducts();
    }





    private void loadCategory() {
        nestedScrollView.setVisibility(View.GONE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         *

         */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_Category,
                new Response.Listener<String>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray( URLDecoder.decode( response, "UTF-8" ));

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                Log.d("JsonArayyyyyyyyyyyyyofcategory", String.valueOf(product));

                                //adding the product to product list
                                CategoryList.add(new Category(
                                        product.getInt("id"),
                                        product.getInt("row_order"),
                                        product.getString("name"),
                                        product.getString("subtitle"),
                                        product.getString("web_image"),
                                        product.getInt("status"),
                                        product.getString("image")

                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            ProductCatAdapter adapter = new ProductCatAdapter(ProductCategory.this, CategoryList,R.layout.category_list);
                            recyclerView.setAdapter(adapter);
                            //ProductSubCAdapter adapter1 = new ProductSubCAdapter(ProductCategory.this,prod)
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




    private void loadProducts() {
        nestedScrollView.setVisibility(View.GONE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();
        //subcrecyclerView.setVisibility(View.GONE);
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
                            JSONArray array = new JSONArray( URLDecoder.decode( response, "UTF-8" ));

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                Log.d("JsonArayyyyyyyyyyyyy", String.valueOf(product));

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

                            //creating adapter object and setting it to recyclerview
                            ProductSubCAdapter adapter1 = new ProductSubCAdapter(ProductCategory.this, productList,R.layout.sub_category_list);
                            subcrecyclerView.setAdapter(adapter1);
                            //ProductSubCAdapter adapter1 = new ProductSubCAdapter(ProductCategory.this,prod)
                            nestedScrollView.setVisibility(View.VISIBLE);
                            mShimmerViewContainer.stopShimmer();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            //subcrecyclerView.setVisibility(View.VISIBLE);
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
