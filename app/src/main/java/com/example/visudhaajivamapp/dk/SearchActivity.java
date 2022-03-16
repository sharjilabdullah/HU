package com.example.visudhaajivamapp.dk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.visudhaajivamapp.R;

import java.util.ArrayList;

import static com.example.visudhaajivamapp.R.id.listview;

public class SearchActivity extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    private Object ListView;

    public SearchActivity(Intent intent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //searchView = findViewById(R.layout.activity_search);
        //searchView = findViewById(R.id.search);
        ListView = findViewById(R.id.listview);
        list = new ArrayList<String>();
        list.add("Tab");
        list.add("Med");
        list.add("Ayurvedic");
        list.add("Multivitamins");
        list.add("Vegetables");
        list.add("Green Vegetables");
        list.add("Fruits");
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}