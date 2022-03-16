package com.example.visudhaajivamapp.addedbysam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.visudhaajivamapp.DashboardMain;
import com.example.visudhaajivamapp.R;
import com.example.visudhaajivamapp.manageaddress.AddressDetailsActivity;
import com.example.visudhaajivamapp.models.Slider;
import com.example.visudhaajivamapp.models.SubCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;

public class ProductDetail extends AppCompatActivity {

    public static ProductDetail productDetail;
    private static final String URL_PRODUCTS = "https://visudhajivam.in/android/orders.php";
    static ArrayList<Slider> sliderArrayList;
    TextView title, mainprice, discountedprice, discription, detail_madein;
    ImageView imagev, backbutt,cart;
    View root;
    TextView text_ratings_reviews;
    ImageButton imgAdd, imgMinus;
    TextView txtqty;
    SubCategory productt;
    int count;
    SubCategory productToSend;
    TextView addtocart, buynow;
    LinearLayout lytshare;



    protected void onCreate(Bundle savedInstanceState) {

        //root = inflater.inflate(R.layout.detail_activity, container, false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
cart=findViewById(R.id.cart_detail);
        text_ratings_reviews = findViewById(R.id.text_ratings_reviews);
        imagev = findViewById(R.id.image);
        discountedprice = findViewById(R.id.disprice);
        mainprice = findViewById(R.id.mainprice);
        title = findViewById(R.id.title);
        discription = findViewById(R.id.detail);
        imgAdd = findViewById(R.id.btnaddqty);
        imgMinus = findViewById(R.id.btnminusqty);
        txtqty = findViewById(R.id.txtqty);
        detail_madein = findViewById(R.id.detail2);
        lytshare = findViewById(R.id.lytshare);
        addtocart=findViewById(R.id.addtocart);
        buynow=findViewById(R.id.buyNow);
        backbutt = findViewById(R.id.back_icon);


        //id = getArguments().getString("id");
        List<SubCategory> productList = new ArrayList<>();




        new MaterialIntroView.Builder(this)
                .enableDotAnimation(false)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(500)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText("Click this to add this item to Cart")
                .setShape(ShapeType.CIRCLE)
                .setTarget(imgAdd)
                .setUsageId("intro_card_cart_quantity") //THIS SHOULD BE UNIQUE ID
                .show();


        new MaterialIntroView.Builder(this)
                .enableDotAnimation(false)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.NORMAL)
                .setDelayMillis(500)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText("This is the discounted price for the Product")
                .setShape(ShapeType.RECTANGLE)
                .setTarget(discountedprice)
                .setUsageId("intro_card_discount_price") //THIS SHOULD BE UNIQUE ID
                .show();


//        String image = getIntent().getStringExtra("image");
//        String name= getIntent().getStringExtra("name");
//        String dprice = String.valueOf(getIntent().getDoubleExtra("discounted_price",0.0));
//        String price = String.valueOf(getIntent().getDoubleExtra("price",0.0));
//        String manu = getIntent().getStringExtra("manufact");
//        String madein = getIntent().getStringExtra("madein");
//        int stock = getIntent().getIntExtra("stock",0);
        Intent intent = getIntent();
        if(intent.hasExtra("PRODUCT_DETAILS")){
            productToSend = (SubCategory) intent.getSerializableExtra("PRODUCT_DETAILS");
        }

        lytshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");
                String app_url = " https://visudhajivam.in/";
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });


        backbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productDetail = null;
                onBackPressed();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productDetail = ProductDetail.this;
                Intent refresh = new Intent(ProductDetail.this, CartListActivity.class);
                refresh.putExtra("FromCartIcon","FromCartIcon");
                startActivity(refresh);
            }
        });

        imgMinus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                count = Integer.parseInt(txtqty.getText().toString());
                if (!(count <= 0)) {

                    count--;
                    txtqty.setText("" + count);

                    }


                }


        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                count = Integer.parseInt(txtqty.getText().toString());
                if (!(count >= Float.parseFloat(String.valueOf(productToSend.getStock()))))
                {
                    count++;
                    txtqty.setText("" + count);

                } else{
                    Toast.makeText(ProductDetail.this,getString(R.string.stock_limit), Toast.LENGTH_SHORT).show();
                }
            }


        });


        //GetallProducts();

        title.setText(productToSend.getName());
        discountedprice.setText("₹ "+productToSend.getDiscounted_price());
        mainprice.setPaintFlags(mainprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        mainprice.setText("₹ "+productToSend.getPrice());
        Glide.with(this)
                .load(productToSend.getImage())
                .into(imagev);

        discription.setText("Manufacturer : "+productToSend.getManufacture());
        detail_madein.setText("Made In : "+productToSend.getMande_in());


        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt((String) txtqty.getText()) == 0){
                    Toast.makeText(ProductDetail.this,"Quantity should be more than 0",Toast.LENGTH_LONG).show();
                    return;
                }
                productDetail = ProductDetail.this;
                Intent intent = new Intent(ProductDetail.this, AddressDetailsActivity.class);
                ArrayList<SubCategory> productArray = new ArrayList<SubCategory>();
                intent.putExtra("PRODUCT",productToSend);
                intent.putExtra("QUANTITY",txtqty.getText());

                productArray.add(productToSend);
                intent.putExtra("PRODUCT_LIST",productArray);
                ArrayList<String> quantityArray = new ArrayList<String>();
                quantityArray.add(txtqty.getText().toString());
                intent.putExtra("QUANTITY_LIST",quantityArray);
                startActivity(intent);
            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt((String) txtqty.getText())==0){
                    Toast.makeText(ProductDetail.this,"Please select quantity of product",Toast.LENGTH_SHORT).show();
                    return;
                }
                productDetail = ProductDetail.this;
                //DashboardMain.notificationCountCart++;
                Intent intent =new Intent(ProductDetail.this,CartListActivity.class);
                intent.putExtra("PRODUCT",productToSend);
                intent.putExtra("QUANTITY",txtqty.getText());
                startActivity(intent);
                finish();
                Toast.makeText(ProductDetail.this, R.string.addtocart, Toast.LENGTH_SHORT).show();

            }
        });

    }

    /*

    private void GetallProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         *
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
                                Log.d("JsonArayyyyyyyyyyyyy", String.valueOf(product));

                                //adding the product to product list
                                productt = new SubCategory(
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
                                );
                            }


                            //creating adapter object and setting it to recyclerview
                            //ProductAdapter adapter = new ProductAdapter(ProductDetail.this, productList);

                            SetProductDetails(productt);

                        } catch (JSONException e) {
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

    private void SetProductDetails(final SubCategory product) {

        try{

            title.setText(product.getName());
            sliderArrayList.add(new Slider(product.getImage()));
            discountedprice.setText(String.valueOf(product.getDiscounted_price()));
            mainprice.setText(String.valueOf(product.getPrice()));
            discription.setText("Manufacturer : "+product.getManufacture());
            detail_madein.setText("Made In : "+product.getMande_in());

        }catch (Exception e){

        }

    }
    /*
    public void buyNow(View view){
        if(Integer.parseInt((String) txtqty.getText()) == 0){
            Toast.makeText(ProductDetail.this,"Quantity should be more than 0",Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, AddressDetailsActivity.class);
        intent.putExtra("PRODUCT",productToSend);
        intent.putExtra("QUANTITY",txtqty.getText());
        startActivity(intent);
    }

    public void addtocart(View view) {
        if(Integer.parseInt((String) txtqty.getText())==0){
            Toast.makeText(this,"Please select quantity of product",Toast.LENGTH_SHORT).show();
        }
        Intent intent =new Intent(ProductDetail.this,CartListActivity.class);
        intent.putExtra("PRODUCT",productToSend);
        intent.putExtra("QUANTITY",txtqty.getText());
        startActivity(intent);
        finish();
        Toast.makeText(this, R.string.addtocart, Toast.LENGTH_SHORT).show();
    }

    public void Addtowishlist(View view) {
        Intent intent =new Intent(ProductDetail.this,WishlistActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Item Added to wishlist", Toast.LENGTH_SHORT).show();
    }

     */


}
