package com.example.universitybazaarsystem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaymentDenied extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_denied);

        // Actionbar and its title
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Payment");

        Button TryAgain, ReturnHome;

        TryAgain = (Button) findViewById(R.id.tryAgainButton);
        ReturnHome = (Button) findViewById(R.id.returnHomeButton);

        // If Try Again button is clicked
        TryAgain.setOnClickListener (new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent downloadIntent = new Intent(getApplicationContext(), Payments.class);
                startActivity(downloadIntent);
            }
        });


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