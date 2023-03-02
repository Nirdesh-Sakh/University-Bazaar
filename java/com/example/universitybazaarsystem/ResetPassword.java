package com.example.universitybazaarsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity {

    EditText Reset_p1,Reset_p2;
    Button Reset_submit,Reset_cancel;
    FirebaseUser User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Reset_p1=findViewById(R.id.ResetP1);
        Reset_p2=findViewById(R.id.ResetP2);
        Reset_submit=findViewById(R.id.ResetSubmit);
        Reset_cancel=findViewById(R.id.ResetCancel);
        User=FirebaseAuth.getInstance().getCurrentUser();

        Reset_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String P1=Reset_p1.getText().toString();
                String P2=Reset_p2.getText().toString();

                if(P1.isEmpty())
                {
                    Reset_p1.setError("Password field is empty!");
                    return;
                }
                if(P2.isEmpty())
                {
                    Reset_p2.setError("Password field is empty!");
                    return;
                }
                if((P1.equals(P1.toLowerCase()))||P1.length()<8||!(P1.contains("1")||P1.contains("2")||P1.contains("3")||P1.contains("4")||P1.contains("5")||P1.contains("6")||P1.contains("7")||P1.contains("8")||P1.contains("9")||P1.contains("0")))
                {
                    Reset_p1.setError("Password must include:8 characters or more,at least one number and one Capital letter!!");
                    return;
                }
                if(!(P1.equals(P2)))
                {
                    Reset_p2.setError("Password field does not match");
                    return;
                }
                User.updatePassword(P1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ResetPassword.this, "Password is updated!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ResetPassword.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Reset_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}