package com.example.visudhaajivamapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.visudhaajivamapp.HelperClasses.FeaturedAdapter;
import com.example.visudhaajivamapp.HelperClasses.FeaturedHelperClass;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    RecyclerView cardRecycler;
    RecyclerView departmentRecycler;
    RecyclerView.Adapter adapter;
    ImageButton logout;
    TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        cardRecycler = findViewById(R.id.card_recycler);
        cardRecycler();
        departmentRecycler = findViewById(R.id.department_recycler);
        departmentRecycler();
        name = findViewById(R.id.name);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);


        if(signInAccount==null){
            name.setText("User");

        }
        if(signInAccount!=null){
            name.setText(signInAccount.getDisplayName());
        }

    }

    private void departmentRecycler() {
        departmentRecycler.setHasFixedSize(true);
        departmentRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.heart, "Cardiology", "Cardiology is the study and treatment of disorders of the heart and the blood vessels."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.eye_examination, "Eye Care", " Dry, itchy eyes and xanthelasma—small collections of fat on the eyelids—can occur with cirrhosis. "));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.child, "Pediatric", "Pediatrics is the branch of medicine that involves the medical care of infants, children, and adolescents."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.hepatology, "Hepatology", "Hepatologist. This is a doctor who diagnoses and treats diseases associated with the gallbladder, pancreas and liver."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.neuron, "Neurology", "Neurology is the branch of medicine concerned with the study and treatment of disorders of the nervous system."));

        adapter = new FeaturedAdapter(featuredLocations);
        departmentRecycler.setAdapter(adapter);

    }

    private void cardRecycler() {

        cardRecycler.setHasFixedSize(true);
        cardRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.diagnosis_icon, "Lab Test", "We are expert in the diagnosis and treatment, Set up an appointment!"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.date, "Book Appointment", "We have doctors with speciality in different fields to make the service wide and safest. Set up an appointment now!"));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.pills, "Order Medicine", "Visudhajivam is India's Largest Online medical store where you can shop for quality online and offline medicines, wellness products."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.customer_service, "Customer Support", "We have 24*7 excellent customer support, Call or Mail us."));

        adapter = new FeaturedAdapter(featuredLocations);
        cardRecycler.setAdapter(adapter);

    }
}