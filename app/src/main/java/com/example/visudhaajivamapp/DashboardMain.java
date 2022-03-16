package com.example.visudhaajivamapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.visudhaajivamapp.DoctorConsultation.DoctorCategory;
import com.example.visudhaajivamapp.HelperClasses.CategoryHelperClass;
import com.example.visudhaajivamapp.HelperClasses.FeaturedAdapter;
import com.example.visudhaajivamapp.HelperClasses.FeaturedHelperClass;
import com.example.visudhaajivamapp.adapter.CategoryAdapter;
import com.example.visudhaajivamapp.adapter.DoctorCatAdapter;
import com.example.visudhaajivamapp.adapter.ProductCatAdapter;
import com.example.visudhaajivamapp.addedbysam.CartListActivity;
import com.example.visudhaajivamapp.addedbysam.ProductCategory;
import com.example.visudhaajivamapp.addedbysam.ProductDetail;
import com.example.visudhaajivamapp.dk.SearchActivity;
import com.example.visudhaajivamapp.models.Category;
import com.example.visudhaajivamapp.models.DoctorsCat;
import com.example.visudhaajivamapp.userprofile.Userprofile;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;

import static com.example.visudhaajivamapp.LoginActivity.LOGGEDIN_SHARED_PREF;
import static com.example.visudhaajivamapp.LoginActivity.SHARED_PREF_NAME;

public class DashboardMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int REQUEST_CODE = 1;
    RecyclerView categoryrecycler;
    RecyclerView.Adapter adapter;
    RecyclerView departmentRecycler, medrecycler;
    RecyclerView aboutRecycler;
    ImageButton category_buttons;
    private static final String URL_Category = "https://visudhajivam.in/android/category.php";
    ArrayList<Category>CategoryListmed;
    TextView seall,medseeall;
    private ShimmerFrameLayout mShimmerViewContainer;
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
    public static final String SHARED_PREF_NAME = "tech";
    //RecyclerView recyclerView;
    ArrayList<DoctorsCat> CategoryList;
    private static String JSON_URL = "https://visudhajivam.in/android/doct_category.php";
    //@BindView(R.id.cart_button)
    public static int notificationCountCart = 0;

    ImageButton btnCart;
    SearchView searchView;
    ListView listview;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView locationDetails;
    private long pressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_main);
        locationDetails = findViewById(R.id.Location_detail);
        //mShimmerViewContainer = findViewById(R.id.mShimmerViewContainer);
        categoryrecycler = findViewById(R.id.category_card);

        seall=findViewById(R.id.seeall);
        medseeall=findViewById(R.id.seeallmed);
        categoryrecycler();
        departmentRecycler = findViewById(R.id.department_recycler);
        departmentRecycler();
        aboutRecycler = findViewById(R.id.about_recycler);
        aboutRecycler();
        medrecycler = findViewById(R.id.med_recycler);
        medrecycler();

        /*btnCart=findViewById(R.id.cart_button);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CartInsideActivity.class));
            }
        });

         */
        //Navigation bar
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Setting up Current Location.
        checkAndRequestPermission();


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


        new MaterialIntroView.Builder(this)
                .enableDotAnimation(false)
                .enableIcon(true)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(500)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText("Hi There! This is your current Location")
                .setShape(ShapeType.RECTANGLE)
                .setTarget(locationDetails)
                .setUsageId("intro_card_location") //THIS SHOULD BE UNIQUE ID
                .show();


        seall.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =new Intent(DashboardMain.this,DoctorCategory.class);
               startActivity(intent);
           }
       });

       medseeall.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =new Intent(DashboardMain.this,ProductCategory.class);
               startActivity(intent);
           }
       });

    }

    private void medrecycler() {
        //mShimmerViewContainer.setVisibility(View.VISIBLE);
        //mShimmerViewContainer.startShimmer();
        medrecycler.setHasFixedSize(true);
        medrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        CategoryListmed = new ArrayList<>();
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
                                CategoryListmed.add(new Category(
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
                            ProductCatAdapter adapter = new ProductCatAdapter(DashboardMain.this, CategoryListmed,R.layout.category_list);
                            medrecycler.setAdapter(adapter);
                            //ProductSubCAdapter adapter1 = new ProductSubCAdapter(ProductCategory.this,prod)
                            //nestedScrollView.setVisibility(View.VISIBLE);
                            //mShimmerViewContainer.stopShimmer();
                            //mShimmerViewContainer.setVisibility(View.GONE);
                            medrecycler.setVisibility(View.VISIBLE);
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

    private void searchItem(String toString) {


    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
            finish();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cart_button) {
            Intent refresh = new Intent(DashboardMain.this, CartListActivity.class);
            refresh.putExtra("FromCartIcon", "FromCartIcon");
            startActivity(refresh);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    private void categoryrecycler() {
        categoryrecycler.setHasFixedSize(true);
        categoryrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<CategoryHelperClass> categorylocations = new ArrayList<>();
        categorylocations.add(new CategoryHelperClass(R.drawable.doctor_appointment, "Consult Doctor"));
        categorylocations.add(new CategoryHelperClass(R.drawable.medicines_icon, "Medicines"));
        //categorylocations.add(new CategoryHelperClass(R.drawable.lab_test, "Lab Tests"));
        //categorylocations.add(new CategoryHelperClass(R.drawable.diagnosis_icon, "Diagnosis"));
        //categorylocations.add(new CategoryHelperClass(R.drawable.hospital, "Nearby Hospital"));

        adapter = new CategoryAdapter(categorylocations);
        categoryrecycler.setAdapter(adapter);
    }


    private void departmentRecycler() {
        departmentRecycler.setHasFixedSize(true);
        departmentRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();
        CategoryList = new ArrayList<>();
        /*
        featuredLocations.add(new FeaturedHelperClass(R.drawable.heart, "Cardiology", "Cardiology is the study and treatment of disorders of the heart and the blood vessels."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.eye_examination, "Eye Care", " Dry, itchy eyes and xanthelasma (small collections of fat on the eyelids) can occur with cirrhosis. "));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.child, "Pediatric", "Pediatrics is the branch of medicine that involves the medical care of infants, children, and adolescents."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.hepatology, "Hepatology", "Hepatalogist is a doctor who diagnoses and treats diseases associated with the gallbladder, pancreas and liver."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.neuron, "Neurology", "Neurology is the branch of medicine concerned with the study and treatment of disorders of the nervous system."));
         */

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(String response) {
                        Log.d("JsonArayyyyyyyyyyyyyofcategory", response);
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(URLDecoder.decode(response, "UTF-8"));

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
                            DoctorCatAdapter adapter = new DoctorCatAdapter(DashboardMain.this, CategoryList, R.layout.doctor_category_lyt);
                            departmentRecycler.setAdapter(adapter);
                            departmentRecycler.setVisibility(View.VISIBLE);
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




    private void aboutRecycler() {
        aboutRecycler.setHasFixedSize(true);
        aboutRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.vision, "Our Vision", "To revolutionise the Healthcare sector through this healthtech platform so that each and every citizen of India can get the best medical needs at there allowing budget."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.mission, "Our Mission", "VISUDH AJIVAM PVT LTD is on a mission to take hand in hand with public and help them across their medical needs with making the user friendly digital platform without any Hassel at right time."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.trophy, "Our Values", "VISUDH AJIVAM PVT LTD is one of the best digital platform for health care services integrated with pharmacies, doctors, labtests, diagnosis centres India world class facilities to individuals across the country."));

        adapter = new FeaturedAdapter(featuredLocations);
        aboutRecycler.setAdapter(adapter);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.cart_button:
                //startActivity(new Intent(getApplicationContext(),CartInsideActivity.class));
                break;
            case R.id.profile:
                startActivity(new Intent(getApplicationContext(), Userprofile.class));
                  break;

            case R.id.nav_logout:
             AlertDialog.Builder builder=new AlertDialog.Builder(DashboardMain.this); //Home is name of the activity
                builder.setMessage("Do you want to LogOut?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        SharedPreferences SM = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor edit = SM.edit();
                        edit.putBoolean(LOGGEDIN_SHARED_PREF, false);
                        edit.commit();

                        Intent intent = new Intent(DashboardMain.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert=builder.create();
                alert.show();

                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void searchf(View view) {
        Intent intent =new   Intent(DashboardMain.this, SearchActivity.class);
        new SearchActivity(intent);
        Toast.makeText(this, "Searchbar is working", Toast.LENGTH_SHORT).show();
        finish();

    }

    //******************************************************************************************************************************************************************

    private void checkAndRequestPermission(){
        //if their is no permission to access location.
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //requesting for location permission.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(DashboardMain.this,"Location Permission not granted, Hence default address is set",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            checkAndSetUpLocation();
        }
    }

    private void checkAndSetUpLocation(){
        if(isInternetAvailable()){
            if(isLocationEnable()){
                setUpLocationListener();
            }
            else{
                showLocationNotEnableDialog();
            }
        }
        else{
            showInternetNotEnabledDialog();
        }
    }

    private Boolean isInternetAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo!=null && activeNetworkInfo.isConnected()){
            return true;
        }
        return false;
    }

    private void showInternetNotEnabledDialog(){
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle("Internet Required!!")
                .setMessage("Enable Internet Connection to get your address")
                .setCancelable(false)
                .setPositiveButton("TRY AGAIN",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkAndSetUpLocation();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alert.show();
    }

    private Boolean isLocationEnable(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
    }


    private void showLocationNotEnableDialog(){
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle("Location Required!!")
                .setMessage("Enable Location to get your address")
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        //checkAndSetUpLocation();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alert.show();
    }

    @SuppressLint("MissingPermission")
    public void setUpLocationListener(){
        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location location = null;
        for(int i=providers.size()-1;i>=0;i--){
            location = locationManager.getLastKnownLocation(providers.get(i));
            if(location!=null){
                break;
            }
        }
        if(location!=null){
            setUpLocation(location);
        }
        else{
            Toast.makeText(DashboardMain.this,"Location not found",Toast.LENGTH_LONG).show();
        }
    }

    private void setUpLocation(Location location){
        try{
            Geocoder geocoder = new Geocoder(DashboardMain.this,Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Address address = addressList.get(0);
            locationDetails.setText(address.getLocality()+", "+address.getAdminArea());
        }
        catch (IOException exc){
            Toast.makeText(DashboardMain.this,"Location not set",Toast.LENGTH_SHORT).show();
        }

    }
}
