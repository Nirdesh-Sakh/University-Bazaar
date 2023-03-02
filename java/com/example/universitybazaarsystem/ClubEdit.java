package com.example.universitybazaarsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ClubEdit extends AppCompatActivity
{
    private ActionBar actionBar;
    private String clubID;
    private EditText clubNameET, clubDescET, contactInfoET;
    private String clubName;
    private String description;
    private String contactInfo;

    // Declare an instance of FirebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_edit);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Club");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // get the Intent from previous activity
        Intent downloadIntent = getIntent();

        //get the attached bundle
        Bundle extras = downloadIntent.getExtras();

        //extracting stored data
        clubID = extras.getString("Club ID");

        //final EditText clubNameET, clubDescET, contactInfoET;

        clubNameET = findViewById(R.id.ClubNameInput);
        clubDescET = findViewById(R.id.ClubDescription);
        contactInfoET = findViewById(R.id.ContactInfo);

        //set group info
        //extracting stored data
        clubName = extras.getString("Club Name");
        description = extras.getString("Club Description");
        contactInfo = extras.getString("Contact Information");

        //Set the text
        clubNameET.setText(clubName);
        clubDescET.setText(description);
        contactInfoET.setText(contactInfo);

        /*TextView cName = (TextView)findViewById(R.id.ClubName);
        TextView cDescription = (TextView)findViewById(R.id.desc);
        TextView Contact = (TextView)findViewById(R.id.contacts);

        // get the Intent from previous activity
        Intent downloadIntent = getIntent();

        //get the attached bundle
        Bundle extras = downloadIntent.getExtras();

        //extracting stored data
        clubID = extras.getString("Club ID");
        clubName = extras.getString("Club Name");
        description = extras.getString("Club Description");
        contactInfo = extras.getString("Contact Information");

        //Set the text of the TextView
        cName.setText(clubName);
        cDescription.setText(description);
        Contact.setText(contactInfo); */

        Button Submit;
        Submit = (Button) findViewById(R.id.submitButton);

        firebaseAuth = FirebaseAuth.getInstance();

        Submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startUpdatingClub();
            }
        });

    }

    private void startUpdatingClub()
    {
        //input data
        String clubTitle = clubNameET.getText().toString().trim();
        String clubDes = clubDescET.getText().toString().trim();
        String contact = contactInfoET.getText().toString().trim();

        //validate data
        if (TextUtils.isEmpty(clubTitle))
        {
            Toast.makeText(this, "Club name is required...", Toast.LENGTH_SHORT).show();
            return;
        }

        //update club
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("clubTitle", clubTitle);
        hashMap.put("clubDescription", clubDes);
        hashMap.put("ContactInfo", contact);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Clubs");
        ref.child(clubID).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //club updated successfully
                Toast.makeText(ClubEdit.this, "Club was successfully updated", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //update failed
                Toast.makeText(ClubEdit.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}