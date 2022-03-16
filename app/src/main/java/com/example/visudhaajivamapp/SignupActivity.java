package com.example.visudhaajivamapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignupActivity extends AppCompatActivity {
    EditText user_name,mobnum,signinas;
    EditText user_email;
    EditText user_pass;
    Button btn_sign;
    TextView btn_login;
    private static final String REGISTER_URL="http://rahulkr.esy.es/UserRegistration/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        user_name = (EditText) findViewById(R.id.id_name);
        user_email = (EditText) findViewById(R.id.id_email);
        user_pass = (EditText) findViewById(R.id.id_pass);
        mobnum=(EditText)findViewById(R.id.id_mob);
        signinas=(EditText)findViewById(R.id.signinas);

        btn_sign = (Button) findViewById(R.id.register);
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        btn_login=(TextView)findViewById(R.id.id_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intentLogin);
            }
        });
    }

    private void registerUser() {
        String username = user_name.getText().toString().trim().toLowerCase();
        String email = user_email.getText().toString().trim().toLowerCase();
        String password = user_pass.getText().toString().trim().toLowerCase();
        String signin = signinas.getText().toString().trim().toLowerCase();

        register(username, password, email,signin);
    }

    private void register(String username, String password, String email, String signin){
        String urlSuffix = "?username=" + username + "&password=" + password + "&email=" + email;
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignupActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),"Registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {
                String s = params[0];
                BufferedReader bufferReader=null;
                try {
                    URL url=new URL(REGISTER_URL+s);
                    HttpURLConnection con=(HttpURLConnection)url.openConnection();
                    bufferReader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String result;
                    result=bufferReader.readLine();
                    return  result;

                }catch (Exception e){
                    return null;
                }
            }

        }
        RegisterUser ur=new RegisterUser();
        ur.execute(urlSuffix);
    }
}