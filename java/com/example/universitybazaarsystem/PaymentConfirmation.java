package com.example.universitybazaarsystem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PaymentConfirmation extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);

        // Actionbar and its title
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Confirmation");

        Button ReturnHome;

        ReturnHome = (Button) findViewById(R.id.closeButton);

        //If return home button is clicked
        ReturnHome.setOnClickListener (new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent downloadIntent = new Intent(getApplicationContext(), Sales.class);
                startActivity(downloadIntent);
            }
        });

    }
}