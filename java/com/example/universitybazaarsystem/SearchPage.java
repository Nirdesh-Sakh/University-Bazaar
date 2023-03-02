package com.example.universitybazaarsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

public class SearchPage extends AppCompatActivity {
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        back = findViewById(R.id.btn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });


    }
    public void onClickSales(View view)
    {
        startActivity(new Intent(SearchPage.this,ViewSearchOfBuy.class));
    }
    public void onClickClubs(View view)
    {
        startActivity(new Intent(SearchPage.this,ViewSearchOfClub.class));
    }
    public void onClickPost(View view)
    {
        startActivity(new Intent(SearchPage.this,ViewSearchOfPosts.class));
    }

}