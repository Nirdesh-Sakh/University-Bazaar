package com.example.universitybazaarsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {
    private List<SalesModel> listData;
    private RecyclerView rv;
    private SalesAdapter adapter;
    Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        rv = (RecyclerView) findViewById(R.id.recyclerview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        back = findViewById(R.id.btn_close);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        listData = new ArrayList<>();
        final DatabaseReference nm = FirebaseDatabase.getInstance("https://universitybazaarsystem-d0619-default-rtdb.firebaseio.com/").getReference().child("Sales");
        nm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        SalesModel l = npsnapshot.getValue(SalesModel.class);
                        listData.add(l);
                    }
                    adapter = new SalesAdapter(listData);
                    rv.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void onSellBtnClick(View view) {

        startActivity(new Intent(ShowActivity.this, SellActivity.class));
    }

    public void onBuyNow(View view) {
        startActivity(new Intent(ShowActivity.this, Payments.class));
    }


    public void onClickShowDetails(View view) {

    }
    public void onBtnBuy(View view){
        startActivity(new Intent(ShowActivity.this, MainActivity.class));

    }
}
