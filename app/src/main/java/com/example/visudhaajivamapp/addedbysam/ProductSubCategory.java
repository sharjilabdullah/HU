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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductSubCategory extends AppCompatActivity {

    public static ProductSubCategory productSubCategory;
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
        setContentView(R.layout.product_sub_category);

        activity= ProductSubCategory.this;
        session = new Session(activity);
        backbutt = findViewById(R.id.back_icon);
cart=findViewById(R.id.cart_subc);


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
        resource = R.layout.sub_category_list;

        recyclerView = findViewById(R.id.recyclerView);
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
        nestedScrollView = findViewById(R.id.nestedScrollView);
        mShimmerViewContainer = findViewById(R.id.mShimmerViewContainer);


        String subcid = String.valueOf(getIntent().getIntExtra("category_id",0));

        //this method will fetch and parse json
        //to display it in recyclerview
        backbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productSubCategory = null;
                onBackPressed();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productSubCategory = ProductSubCategory.this;
                Intent refresh = new Intent(ProductSubCategory.this, CartListActivity.class);
                refresh.putExtra("FromCartIcon","FromCartIcon");
                startActivity(refresh);
            }
        });
        sendid(subcid);
        //loadProducts(subcid);

    }

    private void sendid(final String subcid) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
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
                            //int id, String name, double price, double discounted_price, int category_id, int subcategory_id, String manufacture, String mande_in, int stock, int product_id, String image) {
                            //creating adapter object and setting it to recyclerview
                            Log.d("Subcategoryy", String.valueOf(subcid));
                            //Log.d("ProductCategoryy", String.valueOf(prosub.getCategory_id()));
                            //if(subcid==prosub.getCategory_id()) {
                            ProductSubCAdapter adapter = new ProductSubCAdapter(ProductSubCategory.this, productList, resource);
                            recyclerView.setAdapter(adapter);
                            mShimmerViewContainer.stopShimmer();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            /*}else{
                                //CardView nothing = (CardView) findViewById(R.layout.empty_lyt);
                                mShimmerViewContainer.stopShimmer();
                                mShimmerViewContainer.setVisibility(View.GONE);
                            }

                             */

                        } catch (JSONException e) {
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
                prams.put("cid", subcid);
                //prams.put(KEY_PASSWORD, password);
                //prams.put(Key_loggedinas,login);

                return prams;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /*
    private void sendcatid(int id) {
        String urlSuffix = "?cid" + String.valueOf(id) ;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(ProductSubCategory.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //Toast.makeText(context,"Registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferReader=null;
                try {
                    URL url=new URL(URL+s);
                    HttpURLConnection con=(HttpURLConnection)url.openConnection();
                    bufferReader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String result;
                    result=bufferReader.readLine();
                    Log.d("responsefromIdddd",result);
                    return  result;
                }catch (Exception e){
                    return null;
                }
            }

        }
        RegisterUser ur=new RegisterUser();
        ur.execute(urlSuffix);
    }

     */




    private void loadProducts(final int subcid) {
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
                            //int id, String name, double price, double discounted_price, int category_id, int subcategory_id, String manufacture, String mande_in, int stock, int product_id, String image) {
                            //creating adapter object and setting it to recyclerview
                            Log.d("Subcategoryy", String.valueOf(subcid));
                            //Log.d("ProductCategoryy", String.valueOf(prosub.getCategory_id()));
                            //if(subcid==prosub.getCategory_id()) {
                            ProductSubCAdapter adapter = new ProductSubCAdapter(ProductSubCategory.this, productList, resource);
                            recyclerView.setAdapter(adapter);
                            mShimmerViewContainer.stopShimmer();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            /*}else{
                                //CardView nothing = (CardView) findViewById(R.layout.empty_lyt);
                                mShimmerViewContainer.stopShimmer();
                                mShimmerViewContainer.setVisibility(View.GONE);
                            }

                             */

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
