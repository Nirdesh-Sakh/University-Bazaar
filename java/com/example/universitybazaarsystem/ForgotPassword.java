package com.example.universitybazaarsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassword extends AppCompatActivity {
    EditText F_email,F_answer;
    Button F_cancel, F_submit;
    AlertDialog.Builder Email_dialog;
    LayoutInflater inflater;
    Spinner SecuritySpinner;
    FirebaseAuth F_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        F_answer=findViewById(R.id.FSecurityAnswer);
        F_submit=findViewById(R.id.FSubmit);
        F_cancel=findViewById(R.id.FCancel);
        Email_dialog=new AlertDialog.Builder(this);
        SecuritySpinner=(Spinner)findViewById(R.id.Fspinner);
        inflater=this.getLayoutInflater();
        F_auth=FirebaseAuth.getInstance();

        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(ForgotPassword.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.FSecurityQuestions));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SecuritySpinner.setAdapter(myAdapter);

        F_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        F_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer=F_answer.getText().toString();


                if(answer.isEmpty())
                {
                    F_answer.setError("Answer field is empty!!");
                    return;
                }

                View view=inflater.inflate(R.layout.reset_pop,null);


                Email_dialog.setTitle("Reset your forgotten password?")
                        .setMessage("Enter your email to get the password reset link.")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText email=view.findViewById(R.id.PopUpEmail);
                                if(email.getText().toString().isEmpty())
                                {
                                    email.setError("This is a required field!");
                                    return;
                                }
                                F_auth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ForgotPassword.this, "Reset email sent successfully!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),Login.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ForgotPassword.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),Registration.class));
                                    }
                                });
                            }
                        }).setNegativeButton("Cancel",null)
                        .setView(view)
                        .create().show();

            }
        });
    }
}