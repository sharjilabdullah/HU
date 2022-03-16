package com.example.visudhaajivamapp.addedbysam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.visudhaajivamapp.DashboardMain;
import com.example.visudhaajivamapp.LoginActivity;
import com.example.visudhaajivamapp.R;
import com.example.visudhaajivamapp.adapter.CartItemsAdapter;
import com.example.visudhaajivamapp.manageaddress.AddressDetailsActivity;
import com.example.visudhaajivamapp.manageaddress.BuyNowModel;
import com.example.visudhaajivamapp.models.CartItems;
import com.example.visudhaajivamapp.models.SubCategory;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CartListActivity extends AppCompatActivity {
    public static CartListActivity cartListActivity;
    private static Context mContext;
    SubCategory product;
    private static final String Cart_URL = "https://visudhajivam.in/android/d.php";
    private static final String Cart_recURL ="https://visudhajivam.in/android/cart.php";
    private BuyNowModel finalModel;
    public static ArrayList<CartItems> productList;
    RecyclerView cartrecyclerView;
    private TextView paymentText, totalpriceTxt;
    ArrayList<SubCategory> productArray;
    ArrayList<String> quantityArray;
    private int totalQuantity = 0;
    private double totalPrice = 0;
    private ShimmerFrameLayout mShimmerViewContainer;
    ImageButton backbutt;
    int empty_cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        backbutt = findViewById(R.id.back_icon);
        mContext = CartListActivity.this;
        productList = new ArrayList<>();

        empty_cart= R.layout.empty_message_layout_action;
        //Show cart layout based on items
        LinearLayout layoutCartItems = (LinearLayout) findViewById(R.id.layout_items);
        LinearLayout layoutCartPayments = (LinearLayout) findViewById(R.id.layout_payment);
        LinearLayout layoutCartNoItems = (LinearLayout) findViewById(R.id.layout_cart_empty);

        mShimmerViewContainer = findViewById(R.id.mShimmerViewContainer);

        cartrecyclerView = findViewById(R.id.cartrecyclerview);
        cartrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences pref = getSharedPreferences("tech", Context.MODE_PRIVATE);
        String userid = String.valueOf(pref.getInt("id",0)); //user id
        String identity = pref.getString("identity","");

        Log.d("cartitems", String.valueOf(CartItemsAdapter.Cartitems));
        Log.d("userid", userid);
        Log.d("identity", identity);


        Intent intent = getIntent();
        if (intent.hasExtra("PRODUCT") ) {

            layoutCartNoItems.setVisibility(View.GONE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            mShimmerViewContainer.startShimmer();
            product = (SubCategory) intent.getSerializableExtra("PRODUCT");
            String product_id = String.valueOf(product.getProduct_id()); //product_id
            String quantity = getIntent().getStringExtra("QUANTITY"); //quantity =1
            String product_variant_id =String.valueOf(product.getId()); // product_variant_id
            double priceToSend = product.getDiscounted_price() * Integer.parseInt(quantity);


            SharedPreferences sharedPreferences = CartListActivity.this.getSharedPreferences("Cart", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("product", product_id);
            editor.putString("quantity", quantity);
            editor.putString("pvid",product_variant_id);
            editor.commit();

            sendCartInfoadd(userid,product_id, quantity,product_variant_id,identity,layoutCartItems,layoutCartNoItems, layoutCartPayments);

        }else if(intent.hasExtra("FromCartIcon") || intent.hasExtra("FromAdapter")){
            layoutCartNoItems.setVisibility(View.GONE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            mShimmerViewContainer.startShimmer();
            CartReceiveInfo(userid,identity,layoutCartItems,layoutCartNoItems, layoutCartPayments);

        }

        else{

            layoutCartNoItems.setVisibility(View.VISIBLE);
            layoutCartItems.setVisibility(View.GONE);
            layoutCartPayments.setVisibility(View.GONE);

            Button bStartShopping = (Button) findViewById(R.id.bAddNew);
            bStartShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(CartListActivity.this,AllSubCategory.class));
                }
            });

        }

        totalpriceTxt=findViewById(R.id.totalcartprice);

        backbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //**********************Payment option work(by rohit)********************
        paymentText = findViewById(R.id.paymentcart);
        paymentText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartListActivity = CartListActivity.this;
                fillDetailsInArray();
                Intent sendIntent = new Intent(CartListActivity.this, AddressDetailsActivity.class);
                sendIntent.putExtra("PRODUCT_LIST",productArray);
                sendIntent.putExtra("QUANTITY_LIST",quantityArray);
                startActivity(sendIntent);
            }
        });

        Log.d("productpricecartarray", String.valueOf(productArray));




    }


    private void fillDetailsInArray(){
        productArray = new ArrayList<SubCategory>();
        quantityArray = new ArrayList<String>();
        SubCategory prod;
        if(productList!=null){
            for(CartItems item : productList){
                prod = new SubCategory();
                prod.setId(item.getId());
                prod.setDiscounted_price(item.getDiscounted_price());
                prod.setPrice(item.getPrice());
                prod.setName(item.getName());
                productArray.add(prod);
                quantityArray.add(String.valueOf(item.getQuantity()));
            }
        }
    }
    private void showtotalprice(){
        if (productList != null) {
            for (CartItems item : productList) {

                totalPrice += (item.getDiscounted_price())*item.getQuantity();
                Log.d("priceofsingleiteam", String.valueOf(item.getDiscounted_price()));
                Log.d("qquantofiteam", String.valueOf(item.getQuantity()));
            }

            totalpriceTxt.setText("₹ " + String.valueOf(totalPrice));
        }
    }


    public  void shopNow(View view){

        startActivity(new Intent(this,AllSubCategory.class));

    }

    private void sendCartInfoadd(String userid, String product_id, String quantity, String product_varid,String identity,LinearLayout layoutCartItems,LinearLayout layoutCartNoItems, LinearLayout layoutCartPayments) {



        StringRequest stringRequest = new StringRequest(Request.Method.POST, Cart_URL,
                new Response.Listener<String>() {

                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(String response) {
                        Log.d("showwwwwwwwwwwcartsend", String.valueOf(response));
                        if(response.equals("seccess")){

                            CartReceiveInfo(userid,identity,layoutCartItems,layoutCartNoItems, layoutCartPayments);
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
                prams.put("user_id", userid);
                prams.put("product_id", product_id);
                prams.put("product_variant_id",product_varid);
                prams.put("quantity",quantity);
                prams.put("identity",identity);

                return prams;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void CartReceiveInfo(String userid,String identity,LinearLayout layoutCartItems, LinearLayout layoutCartNoItems, LinearLayout layoutCartPayments) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Cart_recURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("JsonArayyyyyyyyyyyyy", response);

                            //converting the string to json array object
                            JSONArray array = new JSONArray( URLDecoder.decode( response, "UTF-8" ));

                            mShimmerViewContainer.stopShimmer();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            layoutCartItems.setVisibility(View.VISIBLE);
                            layoutCartPayments.setVisibility(View.VISIBLE);


                            if(array.length()==0){
                                layoutCartNoItems.setVisibility(View.VISIBLE);
                                layoutCartItems.setVisibility(View.GONE);
                                layoutCartPayments.setVisibility(View.GONE);
                            }
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                Log.d("JsonArayyyyyyyyyyyyy", String.valueOf(product));

                                //adding the product to product list
                                productList.add(new CartItems(
                                        product.getInt("urid"),
                                        product.getString("identity"),
                                        product.getInt("id"),
                                        product.getString("name"),
                                        product.getDouble("price"),
                                        product.getDouble("discounted_price"),
                                        product.getInt("quantity"),
                                        product.getString("image"),
                                        true
                                ));
                            }
                            //"id":"31","name":"paracitamol","price":"150","quuantity":"1","image":"img\/products\/paracitamol73685.png"
                            //creating adapter object and setting it to recyclerview

                            Log.d("CartItemsListt", String.valueOf(productList));
                            CartItemsAdapter adapter1 = new CartItemsAdapter(CartListActivity.this,R.layout.layout_cartlist_item,layoutCartItems,layoutCartNoItems, layoutCartPayments);
                            cartrecyclerView.setAdapter(adapter1);

                            //mShimmerViewContainer.stopShimmer();
                            //mShimmerViewContainer.setVisibility(View.GONE);
                            cartrecyclerView.setVisibility(View.VISIBLE);
                            showtotalprice();


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
                prams.put("id", userid);
                //prams.put("product_id", product_id);
                //prams.put("product_variant_id",product_varid);
                //prams.put("quantity",quantity);
                prams.put("identity",identity);

                return prams;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cartListActivity = null;
    }
}
