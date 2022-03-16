package com.example.visudhaajivamapp.manageaddress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.visudhaajivamapp.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ConfirmOrderActivity extends AppCompatActivity {

    public static ConfirmOrderActivity confirmOrderActivity;
    public static final String CHECKOUT_URL = "https://visudhajivam.in/android/checkout.php";
    private TextView userNameText,add1Text,add2Text,add3Text,mobileText,totalPriceText,totalQuantityText;
    private RadioButton codRadio;
    private Button confirmButton;
    ImageButton backbutt;

    private BuyNowModel finalModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        userNameText = findViewById(R.id.username_id);
        add1Text = findViewById(R.id.add1);
        add2Text = findViewById(R.id.add2);
        add3Text = findViewById(R.id.add3);
        backbutt = findViewById(R.id.back_icon);
        mobileText = findViewById(R.id.user_mobile_id);
        totalPriceText = findViewById(R.id.total_price_id);
        totalQuantityText = findViewById(R.id.total_quantity_id);
        codRadio = findViewById(R.id.cod_id);
        confirmButton = findViewById(R.id.confirm_id);

        //Receiving intent from AddressDetailsActivity
        Intent intent = getIntent();
        finalModel = (BuyNowModel) intent.getSerializableExtra("BUY_NOW_MODEL");
        totalQuantityText.setText(intent.getStringExtra("TOTAL_QUANTITY"));
        totalPriceText.setText(String.valueOf(finalModel.getTotal()));
        fillUserDetails();

        backbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDeliveryMethod();
                sendDataToOnlineDB();
            }
        });


    }

    private void fillUserDetails(){
        userNameText.setText(finalModel.getUsername());
        add1Text.setText(finalModel.getAddress());
        add2Text.setText(finalModel.getAddress3()+", "+finalModel.getState()+" - "+finalModel.getAddress1()); // city, state - pincode.
        add3Text.setText(finalModel.getCountry());
        mobileText.setText(finalModel.getMobile());
    }

    private void setDeliveryMethod(){
        if(codRadio.isChecked()){
            finalModel.setDeliverymethod("Cash On Delivery");
            finalModel.setCash("Cash On Delivery");
            finalModel.setPaymethod("Cash On Delivery");
        }
    }

    private Boolean sendDataToOnlineDB() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, CHECKOUT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("ResponseData",response.trim());
                        String responseResult = response.trim();
                        if(responseResult.equals("successed")){
                            confirmOrderActivity = ConfirmOrderActivity.this;
                            startActivity(new Intent(ConfirmOrderActivity.this,SuccessOrderActivity.class));
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
                Gson gson = new Gson();
                Map<String,String> prams = new HashMap<>();
                prams.put("user_id",finalModel.getUser_id());
                prams.put("user_name",finalModel.getUser_name());
                prams.put("mobile",finalModel.getMobile());
                prams.put("deliverymethod",finalModel.getDeliverymethod());
                prams.put("tax",String.valueOf(finalModel.getTax()));
                prams.put("total",String.valueOf(finalModel.getTotal()));
                prams.put("cash",finalModel.getCash());
                prams.put("id",gson.toJson(finalModel.getId()));
                prams.put("price",gson.toJson(finalModel.getPrice()));
                prams.put("quantity",gson.toJson(finalModel.getQuantity()));
                prams.put("pname",gson.toJson(finalModel.getPname()));
                prams.put("username",finalModel.getUsername());
                prams.put("address",finalModel.getAddress());
                prams.put("address1",finalModel.getAddress1());
                prams.put("address2",finalModel.getAddress2());
                prams.put("address3",finalModel.getAddress3());
                prams.put("paymethod",finalModel.getPaymethod());
                prams.put("country",finalModel.getCountry());
                prams.put("state",finalModel.getState());

                return prams;
            }
        };
        requestQueue.add(stringRequest);


        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        confirmOrderActivity = null;
    }
}