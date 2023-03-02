package com.example.universitybazaarsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {
    EditText F_name, L_name, E_mail, User_name, Pass_word, Security_ques,UID,Date_of_birth;
    Button Register_submit,Register_cancel;
    String User_ID;
    FirebaseAuth F_auth;
    FirebaseFirestore F_store;
    Spinner SecuritySpinner;
    private FirebaseDatabase db = FirebaseDatabase.getInstance("https://universitybazaarsystem-d0619-default-rtdb.firebaseio.com/");
    // Getting a child called users to make more systematic
    private DatabaseReference root = db.getReference().child("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        F_name=findViewById(R.id.FirstName);
        L_name=findViewById(R.id.LastName);
        UID=findViewById(R.id.Dob);
        Date_of_birth=findViewById(R.id.DateofBirth);
        E_mail=findViewById(R.id.Email);
        User_name=findViewById(R.id.UserName);
        Pass_word=findViewById(R.id.Password);
        Security_ques=findViewById(R.id.SecurityAnswer);
        SecuritySpinner=(Spinner)findViewById(R.id.Security);
        Register_submit=findViewById(R.id.Register);
        Register_cancel=findViewById(R.id.RCancel);
        F_auth=FirebaseAuth.getInstance();
        F_store=FirebaseFirestore.getInstance();

        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(Registration.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.SecurityQuestions));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SecuritySpinner.setAdapter(myAdapter);


        Register_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname,lname,email,password,university,username,securityanswer,dateofbirth;

                fname=F_name.getText().toString();
                lname=L_name.getText().toString();
                university=UID.getText().toString();
                dateofbirth=Date_of_birth.getText().toString();
                username=User_name.getText().toString();
                email=E_mail.getText().toString();
                password=Pass_word.getText().toString();
                securityanswer=Security_ques.getText().toString();

                 if(fname.isEmpty())
                {
                    F_name.setError("First name field is empty!!");
                    return;
                }

                if(lname.isEmpty())
                {
                    L_name.setError("Last name field is empty!!");
                    return;
                }

                if(university.isEmpty())
                {
                    UID.setError("University ID Field field is empty!!");
                    return;
                }

                if(dateofbirth.isEmpty())
                {
                    Date_of_birth.setError("Date of birth field is empty!!");
                    return;
                }

                if(username.isEmpty())
                {
                    User_name.setError("User name field is empty!!");
                    return;
                }

                if(password.isEmpty())
                {
                    Pass_word.setError("Password field is empty!!");
                    return;
                }

                if(email.isEmpty())
                {
                    E_mail.setError("Email field is empty!!");
                    return;
                }

                if(securityanswer.isEmpty())
                {
                    Security_ques.setError("Security answer field is empty!!");
                    return;
                }
                Pattern A = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher B = A.matcher(username);
                if(B.find()||username.length()<8)
                {
                    User_name.setError("Username should be more than or equal to 8 characters and shouldn't contain wild characters!!");
                    return;
                }

                if((password.equals(password.toLowerCase()))||password.length()<8||!(password.contains("1")||password.contains("2")||password.contains("3")||password.contains("4")||password.contains("5")||password.contains("6")||password.contains("7")||password.contains("8")||password.contains("9")||password.contains("0")))
                {
                    Pass_word.setError("Password must include:8 characters or more,at least one number and one Capital letter!!");
                    return;
                }

               F_auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                   @Override
                   public void onSuccess(AuthResult authResult) {
                       Toast.makeText(Registration.this,"Successfully registered user",Toast.LENGTH_SHORT).show();
                       User_ID=F_auth.getCurrentUser().getUid();
                       DocumentReference DR=F_store.collection("Users").document(User_ID);
                       Map<String,Object> User=new HashMap<>();
                       User.put("First name",fname);
                       User.put("Last name",lname);
                       User.put("University ID",university);
                       User.put("Date of Birth",dateofbirth);
                       User.put("Username",username);
                       User.put("Email",email);
                       User.put("Security Answer",securityanswer);

                       root.push().setValue(User).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               Toast.makeText(Registration.this, "RealTime Success", Toast.LENGTH_SHORT).show();
                           }
                       });
                       //Now inserting into cloud database
                       DR.set(User).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                               Toast.makeText(Registration.this, "Saved data successfully", Toast.LENGTH_SHORT).show();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(Registration.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       });
                       startActivity(new Intent(getApplicationContext(),Login.class));
                       finish();
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Registration.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                   }
               });

            }
        });

        Register_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

    }
}
