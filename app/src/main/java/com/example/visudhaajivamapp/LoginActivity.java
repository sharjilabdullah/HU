package com.example.visudhaajivamapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.visudhaajivamapp.userprofile.UserDetails;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{

    public static final String LOGIN_URL="https://visudhajivam.in/android/login.php";
    public static final String USER_DETAILS="UserDetails";
    public static final String KEY_EMAIL="user_name";
    public static final String KEY_PASSWORD="password";
    public static final String LOGIN_SUCCESS="success";
    public static final String Key_identity ="identity";
    public static final String SHARED_PREF_NAME="tech";
    public static final String EMAIL_SHARED_PREF="user_name";
    public static final String ID_SHARED_PREF="id";
    public static final String MOBILE_SHARED_PREF="mobile";
    public static final String LOGGEDIN_SHARED_PREF="loggedin";
    public static final String Key_loggedinas = "login";
    private boolean loggedIn=false;
    private UserDetails user;
    String[] types = { "user", "employee", "business"};
    public static String type;

    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton fb, google, twitter;
    float v=0;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    ProgressDialog dialog;

    EditText txtemail,pass;
    ProgressBar progressBar;
    Button login, register;
    ImageView show_pass_butt;
    TextView loginas;
    Spinner spin;

    /*
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(getApplicationContext(),DashboardMain.class);
            startActivity(intent);
        }
    }

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tabLayout = findViewById(R.id.tab_Layout);
        /*
        fb = findViewById(R.id.fab_fb);
        google = findViewById(R.id.fab_google);
        twitter = findViewById(R.id.fab_twitter);

         */

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        /*
        tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));

         */
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#12cad6"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#004d40"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        spin = findViewById(R.id.types_spinner);

        txtemail = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        //type = findViewById(R.id.type);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        show_pass_butt = findViewById(R.id.show_pass_btn);
        progressBar = findViewById(R.id.progressbar);
        loginas = findViewById(R.id.loginas);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");

        txtemail.setTranslationY(800);
        pass.setTranslationY(800);
        //type.setTranslationY(800);
        login.setTranslationY(800);
        register.setTranslationY(800);
        show_pass_butt.setTranslationY(800);
        loginas.setTranslationY(800);
        spin.setTranslationY(800);



        txtemail.setAlpha(v);
        pass.setAlpha(v);
        //type.setAlpha(v);
        login.setAlpha(v);
        register.setAlpha(v);
        show_pass_butt.setAlpha(v);
        loginas.setAlpha(v);
        spin.setAlpha(v);


        txtemail.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        show_pass_butt.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        loginas.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        spin.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        //type.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        register.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(900).start();



        /*

        fb.setTranslationY(300);
        google.setTranslationY(300);
        twitter.setTranslationY(300);
         fb.setAlpha(v);
        google.setAlpha(v);
        twitter.setAlpha(v);
         fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        twitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
         */
        tabLayout.setTranslationY(300);
        tabLayout.setAlpha(v);
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();

        //createRequest();

        /*
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

         */

        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,types);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(LoginActivity.this, register);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(LoginActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                        int id = item.getItemId();
                        if (id == R.id.registerAsUser) {
                            Uri uri = Uri.parse("https://visudhajivam.in/create.php");
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                        if (id == R.id.registerAsEmployee) {
                            Uri uri = Uri.parse("https://visudhajivam.in/emp_create.php");
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                        if (id == R.id.registerAsBusiness) {
                            Uri uri = Uri.parse("https://visudhajivam.in/business_reg.php");
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }

                        return true;
                    }
                });

                popup.show();//show popup

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
        //Toast.makeText(getApplicationContext(),types[position] , Toast.LENGTH_LONG).show();
        type= types[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(getApplicationContext(),"Please select your identity" , Toast.LENGTH_LONG).show();
    }


    // from php
    private void login() {
        
        final String user_name = txtemail.getText().toString().trim();
        final String password = pass.getText().toString().trim();
        final String login = type;

        if(TextUtils.isEmpty(user_name)){
            Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Please Enter a Password", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {

                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);

                            Log.d("showwwwwwwwwww response",response);
                            Log.d("showwwwwwwwwww array", String.valueOf(array));
                            if(array.length()>0){

                                SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                JSONObject json = array.getJSONObject(0);

                                //adding details to UserDetails object
                                addDataToUserDetails(json);

                                //adding  User details to shared preference
                                Gson gson = new Gson();
                                String storedUser = gson.toJson(user);
                                editor.putString(USER_DETAILS,storedUser);

                                editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
                                editor.putString(EMAIL_SHARED_PREF, user_name);
                                editor.putInt(ID_SHARED_PREF,json.getInt("id"));
                                editor.putString(MOBILE_SHARED_PREF,json.getString("phone"));
                                editor.putString(Key_identity, json.getString(("identity")));
                                editor.commit();
                                dialog.dismiss();
                                Log.d("json.getString((\"identity\"))",json.getString(("identity")));
                                Log.i("SharedId"," Shared Id = "+sharedPreferences.getInt("id",0));
                                Intent intent = new Intent(LoginActivity.this, DashboardMain.class);

                                startActivity(intent);
                            }else{
                                Toast.makeText(LoginActivity.this, "User Not Found!, Please Create Account First", Toast.LENGTH_LONG).show();
                            }

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
                prams.put(KEY_EMAIL, user_name);
                prams.put(KEY_PASSWORD, password);
                prams.put(Key_loggedinas,login);

                return prams;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(LOGGEDIN_SHARED_PREF, false);
        if(loggedIn){
            Intent intent = new Intent(LoginActivity.this, DashboardMain.class);
            startActivity(intent);
        }
    }




    /*
    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this,"Login failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(),DashboardMain.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Sorry, Authorization Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

 */

    public void addDataToUserDetails(JSONObject json){
        try{
            user = new UserDetails();
            user.setName(json.getString("name"));
            user.setUser_name(json.getString("user_name"));
            user.setPhone(json.getString("phone"));
            user.setAddress(json.getString("address"));
            user.setAadhar(json.getString("aadhar"));
            user.setDist(json.getString("dist"));
            user.setCountry(json.getString("country"));
            user.setBlood(json.getString("blood"));
            user.setPincode(json.getString("pincode"));
            user.setDob(json.getString("dob"));
            user.setExpiry(json.getString("expiry"));
            user.setPhoto(json.getString("photo"));
            user.setIdentity(json.getString("identity"));
        }
        catch (JSONException exc){
            Log.i("UserException","Error on adding data to UserDetails object");
        }
    }

    public void ShowHidePass(View view) {

        if(view.getId()==R.id.show_pass_btn){

            if(pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                //Show Password
                pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                //Hide Password
                pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }
}
