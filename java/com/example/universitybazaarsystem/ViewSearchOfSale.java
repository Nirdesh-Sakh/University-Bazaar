package com.example.universitybazaarsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class ViewSearchOfSale extends AppCompatActivity {
    SearchView mySearchView;
    ListView myList;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_of_sale);


        mySearchView = (SearchView) findViewById(R.id.searchView);
        myList=(ListView)findViewById(R.id.myList);

        list=new ArrayList<String>();

        list.add("ItemToShow1");
        list.add("ItemToShow2");
        list.add("ItemToShow3");
        list.add("ItemToShow4");
        list.add("ItemToShow5");
        list.add("ItemToShow6");
        list.add("ItemToShow7");

        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        myList.setAdapter(adapter);

        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    public void onClickBack(View view)
    {
        startActivity(new Intent(ViewSearchOfSale.this,SearchPage.class));

    }
}