package com.example.visudhaajivamapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupTab_Fragment extends Fragment {
    EditText txtemail, mob,pass,confirm;
    Button register;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab,container,false);
        txtemail = root.findViewById(R.id.email2);
        mob = root.findViewById(R.id.mob);
        pass = root.findViewById(R.id.pass2);
        confirm = root.findViewById(R.id.confirm);
        register = root.findViewById(R.id.register);
        progressBar = root.findViewById(R.id.progressbar2);

        mAuth = FirebaseAuth.getInstance();
        Activity activity = getActivity();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtemail.getText().toString().trim();
                String password = pass.getText().toString().trim();
                String confirmpassword = confirm.getText().toString().trim();
                String mobile = mob.getText().toString().trim();


                if(TextUtils.isEmpty(email)){
                    Toast.makeText(activity, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(activity, "Please Enter a Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);


                if(password.equals(confirmpassword)){
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);

                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getContext(),DashboardMain.class));
                                        Toast.makeText(activity, "Authentication Successful", Toast.LENGTH_SHORT).show();


                                    } else {
                                        Toast.makeText(activity, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                        // If sign in fails, display a message to the user.

                                    }

                                    // ...
                                }
                            });
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(activity, "Please check your Password", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });

        return root;
    }
}
