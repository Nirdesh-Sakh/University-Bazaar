package com.example.universitybazaarsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewSearchOfPosts extends AppCompatActivity {
    SearchView mySearchView;
    ListView myList;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_of_posts);



        mySearchView=(SearchView)findViewById(R.id.searchView);
        myList=(ListView)findViewById(R.id.myList);

        list=new ArrayList<String>();


        final DatabaseReference mRef= FirebaseDatabase.getInstance("https://universitybazaarsystem-d0619-default-rtdb.firebaseio.com/").getReference().child("Posts");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    String name = (String) childSnapShot.child("post").getValue();
                    list.add(name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });



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
        startActivity(new Intent(ViewSearchOfPosts.this,SearchPage.class));

    }
}