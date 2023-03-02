package com.example.universitybazaarsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText E_mail, Pass_word;
    Button Log_in_button,New_user,Forgot_password;
    ProgressBar Progress_bar;
    FirebaseAuth F_auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        E_mail = findViewById(R.id.LUserName);
        Pass_word = findViewById(R.id.LPassword);
        Log_in_button = findViewById(R.id.LogIn);
        New_user = findViewById(R.id.LNewUser);
        Forgot_password=findViewById(R.id.LForgotPassword);
        F_auth=FirebaseAuth.getInstance();

       Log_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = E_mail.getText().toString();
                String password = Pass_word.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    E_mail.setError("Email field is empty!!");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Pass_word.setError("Password field is empty!!");
                    return;
                }

                F_auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        New_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Registration.class));

            }
        });

        Forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = E_mail.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    E_mail.setError("Enter your valid user email first!!");
                    return;
                }
                if(!(email.contains("@")&&email.contains(".")))
                {
                    E_mail.setError("The email does not exist!!  Enter a valid email!");
                    return;
                }

                startActivity(new Intent(getApplicationContext(),ForgotPassword.class));
            }
        });

    }



}