package com.example.visudhaajivamapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTab_Fragment extends Fragment {
    TextInputLayout txtemail;
    TextInputLayout pass;
    TextInputLayout type;
    ProgressBar progressBar;
    Button login;
    private FirebaseAuth mAuth;
    float v = 0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab,container,false);


        txtemail = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.pass);
        type = root.findViewById(R.id.type);
        login = root.findViewById(R.id.login);
        progressBar = root.findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();
        Activity activity = getActivity();

        txtemail.setTranslationY(800);
        pass.setTranslationY(800);
        type.setTranslationY(800);
        login.setTranslationY(800);


        txtemail.setAlpha(v);
        pass.setAlpha(v);
        type.setAlpha(v);
        login.setAlpha(v);


        txtemail.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        type.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtemail.getEditText().getText().toString().trim();
                String password = pass.getEditText().getText().toString().trim();


                if(TextUtils.isEmpty(email)){
                    Toast.makeText(activity, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(activity, "Please Enter a Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(getContext(),Dashboard.class));
                                    Toast.makeText(activity, "Login Successful!", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(activity, "User Not Found!, Please Create Account First", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

        return root;


    }
}
