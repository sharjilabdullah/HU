package com.example.visudhaajivamapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class IntroductoryActivity extends AppCompatActivity {

    ImageView logo, appName, splashImg;
    LottieAnimationView lottieAnimationView;
    private static final int NUM_PAGES =3;
    private ViewPager viewPager;
    private ScreenslidePagerAdapter pagerAdapter;
    Animation anim;
    private static int SPLASH_TIME_OUT = 3800;
    SharedPreferences msharedpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        logo = findViewById(R.id.logo);
        splashImg = findViewById(R.id.img);
        lottieAnimationView = findViewById(R.id.lottie);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenslidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        anim = AnimationUtils.loadAnimation(this,R.anim.on_b_animation);
        viewPager.startAnimation(anim);

        splashImg.animate().translationY(-3600).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(3400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(3400).setDuration(1000).setStartDelay(4000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                msharedpref = getSharedPreferences("SharedPref",MODE_PRIVATE);
                boolean isFirstTime = msharedpref.getBoolean("FirstTime",true);

                if(isFirstTime){
                    SharedPreferences.Editor editor = msharedpref.edit();
                    editor.putBoolean("FirstTime", false);
                    editor.commit();
                }
                else{
                    Intent intent = new Intent(IntroductoryActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_TIME_OUT );

    }
    private class ScreenslidePagerAdapter extends FragmentStatePagerAdapter{

        public ScreenslidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                OnBoardingFragment1 tab1 = new OnBoardingFragment1();
                return tab1;
                case 1:
                    OnBoardingFragment2 tab2 = new OnBoardingFragment2();
                    return tab2;
                case 2:
                    OnBoardingFragment3 tab3 = new OnBoardingFragment3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}