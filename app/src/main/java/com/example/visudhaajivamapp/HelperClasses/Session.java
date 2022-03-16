package com.example.visudhaajivamapp.HelperClasses;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    public static final String PREFER_NAME = "eKart";
    final int PRIVATE_MODE = 0;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;


    public Session(Context context) {
        try {
            this._context = context;
            pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
            editor = pref.edit();
        } catch (Exception e) {

        }
    }


    public boolean getGrid(String id) {
        return pref.getBoolean(id, false);
    }
}