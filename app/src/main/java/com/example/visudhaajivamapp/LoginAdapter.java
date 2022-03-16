package com.example.visudhaajivamapp;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;
    public LoginAdapter(FragmentManager fm, Context context, int totalTabs){
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    public Fragment getItem(int position){
        switch (position){
            case 0:
                LoginTab_Fragment loginTab_fragment = new LoginTab_Fragment();
                return loginTab_fragment;
            case 1:
                SignupTab_Fragment signupTab_fragment= new SignupTab_Fragment();
                return signupTab_fragment;
            default:
                return null;
        }
    }
}
