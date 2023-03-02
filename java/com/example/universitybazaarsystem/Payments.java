package com.example.universitybazaarsystem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Payments extends AppCompatActivity
{
    // Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        // Actionbar and its title
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Payment");
//
//        //enable back button
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);

        final EditText FName_value, LName_value, card_num, exp_month, exp_year, cvv;
        Button Cancel, Submit;

        FName_value = (EditText) findViewById(R.id.FNameInput);
        LName_value = (EditText) findViewById(R.id.LNameInput);
        card_num = (EditText) findViewById(R.id.cardInput);
        exp_month = (EditText) findViewById(R.id.ExpMonthInput);
        exp_year= (EditText) findViewById(R.id.ExpYearInput);
        cvv = (EditText) findViewById(R.id.CVVinput);
        Submit = (Button) findViewById(R.id.submitButton);
        Cancel = (Button) findViewById(R.id.cancelButton);

        // Initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();

        Submit.setOnClickListener (new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String FirstName, LastName, Card, Month, Year, CVV;
                FirstName = FName_value.getText().toString();
                LastName = LName_value.getText().toString();
                Card = card_num.getText().toString();
                Month = exp_month.getText().toString();
                Year =  exp_year.getText().toString();
                CVV = cvv.getText().toString();

                int expYear;
                expYear = Integer.parseInt(Year);

                if ((FirstName.matches("John") && LastName.matches("Smith") && Card.matches("1234-1234-1234-1234")
                        && Month.matches("06") && Year.matches("2025") && CVV.matches("123")) |
                        ((!FirstName.isEmpty()) && (!LastName.isEmpty()) && Card.length()==19
                                && Month.length()==2 && Year.length()==4 && CVV.length()==3 && expYear>2020))
                {
                    //successful payment
                    Toast toast = Toast.makeText(getApplicationContext(), "Successful Payment", Toast.LENGTH_SHORT);
                    toast.show();

                    //go to payment confirmation page
                    Intent downloadIntent = new Intent(getApplicationContext(), PaymentConfirmation.class);
                    //downloadIntent.putExtra("username", Username);
                    startActivity(downloadIntent);

                }
                else if ((FirstName.isEmpty()) | (LastName.isEmpty()) | (Card.isEmpty()) | (Month.isEmpty()) | (Year.isEmpty())
                        | (CVV.isEmpty()))
                {
                    // ask user to fill out all of the fields
                    Toast toast = Toast.makeText(getApplicationContext(), "Please fill in a valid value for all required fields", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    //unsuccessful payment
                    Toast toast = Toast.makeText(getApplicationContext(), "Payment Failed", Toast.LENGTH_SHORT);
                    toast.show();

                    //go to payment denied page
                    Intent downloadIntent = new Intent(getApplicationContext(), PaymentDenied.class);
                    startActivity(downloadIntent);
                }
            }
        });


        Cancel.setOnClickListener (new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent downloadIntent = new Intent(getApplicationContext(), Sales.class);
                startActivity(downloadIntent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed(); //go previous activity
        return super.onSupportNavigateUp();
    }
}