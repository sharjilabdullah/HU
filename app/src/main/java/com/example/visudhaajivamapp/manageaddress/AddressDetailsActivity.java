package com.example.visudhaajivamapp.manageaddress;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.visudhaajivamapp.MainActivity;
import com.example.visudhaajivamapp.R;
import com.example.visudhaajivamapp.models.SubCategory;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AddressDetailsActivity extends AppCompatActivity {

    public static AddressDetailsActivity addressDetailsActivity;
    public static final int LOCATION_REQUEST_CODE = 333;
    private EditText name;
    private EditText mobile;
    private EditText fullAddress;
    private EditText landMark;
    private EditText pinCode;
    private EditText city;
    private EditText state;
    private EditText country;
    private TextView quantityText;
    private TextView priceText;
    private Button continueButton;
    private ImageButton backbutt;
    private ArrayList<SubCategory> productList;
    private BuyNowModel finalModel;
    private ArrayList<String>  quantityList;
    private int totalQuantity = 0;
    private double totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);

        name = findViewById(R.id.name_id);
        mobile = findViewById(R.id.mobile_number_id);
        fullAddress = findViewById(R.id.full_address_id);
        landMark = findViewById(R.id.landmark_id);
        pinCode = findViewById(R.id.pincode_id);
        city = findViewById(R.id.city_id);
        state = findViewById(R.id.state_id);
        country = findViewById(R.id.country_id);
        quantityText = findViewById(R.id.quantity_id);
        priceText = findViewById(R.id.price_id);
        continueButton = findViewById(R.id.continue_id);
        backbutt = findViewById(R.id.back_icon);


        backbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Receiving intent for Edit Address
        Intent intent = getIntent();
        if (intent.hasExtra("PRODUCT_LIST")) {

            productList = (ArrayList<SubCategory>) intent.getSerializableExtra("PRODUCT_LIST");
            quantityList = (ArrayList<String>) getIntent().getSerializableExtra("QUANTITY_LIST");

            calculateTotalQuantity();
            quantityText.setText(totalQuantity + " items");
            calculateTotalPrice();
            priceText.setText(String.valueOf(totalPrice));

        }


        //continue button clicked
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trimAllFields();
                if (!name.getText().toString().equals("") && !mobile.getText().toString().equals("") && !fullAddress.getText().toString().equals("") && !pinCode.getText().toString().equals("") && !landMark.getText().toString().equals("") && !city.getText().toString().equals("") && !state.getText().toString().equals("") && !country.getText().toString().equals("")) {
                    Boolean status = addDataToBuyNowModel();
                    if (!status) {
                        return;
                    }
                    addressDetailsActivity = AddressDetailsActivity.this;
                    //move to ConfirmOrderActivity
                    Intent sendIntent = new Intent(AddressDetailsActivity.this,ConfirmOrderActivity.class);
                    sendIntent.putExtra("BUY_NOW_MODEL",finalModel);
                    sendIntent.putExtra("TOTAL_QUANTITY",quantityText.getText());
                    startActivity(sendIntent);

                } else {
                    Toast.makeText(AddressDetailsActivity.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }// end of onCreate()

    private void calculateTotalQuantity(){
        for(String i : quantityList){
            totalQuantity = totalQuantity + Integer.parseInt(i);
        }
    }

    private void calculateTotalPrice(){
        for(int i=0;i<productList.size();i++){
            totalPrice = totalPrice + productList.get(i).getDiscounted_price() * Integer.parseInt(quantityList.get(i));
        }
    }

    private void trimAllFields() {
        name.setText(name.getText().toString().trim());
        mobile.setText(mobile.getText().toString().trim());
        fullAddress.setText(fullAddress.getText().toString().trim());
        landMark.setText(landMark.getText().toString().trim());
        pinCode.setText(pinCode.getText().toString().trim());
        city.setText(city.getText().toString().trim());
        state.setText(state.getText().toString().trim());
    }

    private Boolean addDataToBuyNowModel() {
        SharedPreferences pref = getSharedPreferences("tech", Context.MODE_PRIVATE);
        finalModel = new BuyNowModel();
        finalModel.setUser_id(String.valueOf(pref.getInt("id",0))); //user id
        finalModel.setUser_name(pref.getString("user_name", "")); // email
        Log.i("UserIdAndUserName",pref.getInt("id",0)+"**************"+finalModel.getUser_name());

        if(!isNumberCorrect(mobile.getText().toString())){

            //If number is not valid
            showInvalidNumberToast();
            return false;
        }
        finalModel.setMobile(mobile.getText().toString());
        finalModel.setTax(0); // demo
        finalModel.setTotal(Double.parseDouble((String) priceText.getText())); //Total price

        ArrayList<Double> priceList = new ArrayList<Double>();
        ArrayList<Integer> prodIdList = new ArrayList<Integer>();
        ArrayList<String> prodNameList = new ArrayList<String>();
        for(SubCategory prod : productList)
        {
            priceList.add(prod.getPrice());
            prodIdList.add(prod.getId());
            prodNameList.add(prod.getName());
        }


        finalModel.setId(prodIdList); // product id
        finalModel.setPrice(priceList); //product price array

        ArrayList<Integer> prodQuantityList = new ArrayList<Integer>();
        for(String quan : quantityList){
            prodQuantityList.add(Integer.parseInt(quan));
        }
        finalModel.setQuantity(prodQuantityList);
        finalModel.setPname(prodNameList); //procuct name array
        finalModel.setUsername(name.getText().toString());
        finalModel.setAddress(fullAddress.getText().toString());

        if(!isPinCodeCorrect(pinCode.getText().toString())){
            showInvalidPinCodeToast();
            return false;
        }
        finalModel.setAddress1(pinCode.getText().toString());
        finalModel.setAddress2(landMark.getText().toString());
        finalModel.setAddress3(city.getText().toString());
        finalModel.setCountry(country.getText().toString());
        finalModel.setState(state.getText().toString());

        Log.i("AllData","id = "+finalModel.getUser_id()+", user_name = "+finalModel.getUser_name()+", mobile = "+finalModel.getMobile()+
                ", Delivery Method = "+finalModel.getDeliverymethod()+", Tax = "+finalModel.getTax()+ ", Total = "+finalModel.getTotal()+", Cash = "+finalModel.getCash() +
                ", Product Id = "+finalModel.getId()+", price = "+finalModel.getPrice()+", quanitity = "+finalModel.getQuantity()+", Pname = "+finalModel.getPname()+
                ", Username = "+finalModel.getUsername()+", Address = "+finalModel.getAddress()+", Address1 = "+finalModel.getAddress1()+
                ", Address2 = "+finalModel.getAddress2()+ ", Address3 = "+finalModel.getAddress3()+", PayMethod = "+finalModel.getPaymethod()+
                ", Country = "+finalModel.getCountry()+", state = "+finalModel.getState());
        return true;
    }

    private Boolean isNumberCorrect(String number) {
        String regex = "(0)?[7-9][0-9]{9}";
        return Pattern.compile(regex).matcher(number).matches(); // Return true if number is correctly formatted.
    }

    private Boolean isPinCodeCorrect(String pincode) {
        String regex = "[1-9][0-9]{5}";
        return Pattern.compile(regex).matcher(pincode).matches(); // Return true if pincode is correctly formatted.
    }

    private void showInvalidNumberToast() {
        Toast.makeText(AddressDetailsActivity.this, "Enter Valid Number!", Toast.LENGTH_SHORT).show();
    }

    private void showInvalidPinCodeToast() {
        Toast.makeText(AddressDetailsActivity.this, "Enter Valid PinCode!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        addressDetailsActivity = null;
    }
}