package com.example.universitybazaarsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class ClubCreation extends AppCompatActivity
{
    // Declare an instance of FirebaseAuth
    private FirebaseAuth firebaseAuth;

    //actionbar
    //private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_creation);

//        // Actionbar and its title
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Create New Club");
//
//        //enable back button
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);

        Button back;

        back = findViewById(R.id.btn_close);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ClubHomePage.class));
            }
        });

        final EditText clubName, clubDescription, contactInfo;

        clubName = findViewById(R.id.ClubNameInput);
        clubDescription = findViewById(R.id.ClubDescription);
        contactInfo = findViewById(R.id.ContactInfo);

        Button Create;
        Create = (Button) findViewById(R.id.createButton);

        Create.setOnClickListener (new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT);
                Intent downloadIntent = new Intent(getApplicationContext(), ClubHomePage.class);
                startActivity(downloadIntent);
                String cName, description, contactInformation;
                cName = clubName.getText().toString();
                description = clubDescription.getText().toString();
                contactInformation = contactInfo.getText().toString();

                // If name is empty
                if (TextUtils.isEmpty(cName))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter a club name", Toast.LENGTH_SHORT);
                    toast.show();
                    return; //don't proceed further
                }
                // If description is empty
                else if (TextUtils.isEmpty(description))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter a description", Toast.LENGTH_SHORT);
                    toast.show();
                    return; //don't proceed further
                }
                // if name doesn't already exist
                else if (! cName.matches("Chess"))
                {
                    // Give message to user to pick new name
                    Toast toast = Toast.makeText(getApplicationContext(), "Club name already exists", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    // Setup info of club
                    String g_timestamp = ""+System.currentTimeMillis();
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("clubID", ""+g_timestamp);
                    hashMap.put("clubTitle", ""+cName);
                    hashMap.put("clubDescription", ""+description);
                    hashMap.put("ContactInfo", ""+contactInformation);
                    hashMap.put("timestamp", ""+g_timestamp);
                    hashMap.put("createdBy", ""+firebaseAuth.getUid());

                    // Create club
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Clubs");
                    ref.child(g_timestamp).setValue(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    //Setup member info (add current user in club's participants list
                                    HashMap<String, String> hashMap1 = new HashMap<>();
                                    hashMap1.put("uid", firebaseAuth.getUid());
                                    hashMap1.put("role", "creator");
                                    hashMap1.put("timestamp", g_timestamp);

                                    DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Clubs");
                                    ref1.child(g_timestamp).child("Participants").child(firebaseAuth.getUid())
                                            .setValue(hashMap1)
                                            .addOnSuccessListener(new OnSuccessListener<Void>()
                                            {
                                                @Override
                                                public void onSuccess(Void aVoid)
                                                {
                                                    //participant added
                                                    //Created successfully
                                                    Toast toast = Toast.makeText(getApplicationContext(), "Club created successfully", Toast.LENGTH_SHORT);
                                                    toast.show();

                                                    //Go to new club page
                                                    Bundle extras = new Bundle();
                                                    extras.putString("Club ID", g_timestamp);
                                                    extras.putString("Club Name", cName);
                                                    extras.putString("Club Description", description);
                                                    extras.putString("Contact Information", contactInformation);

                                                    Intent downloadIntent = new Intent(getApplicationContext(), Club_UniquePage.class);
                                                    downloadIntent.putExtras(extras);
                                                    startActivity(downloadIntent);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener()
                                            {
                                                @Override
                                                public void onFailure(@NonNull Exception e)
                                                {
                                                    //failed adding participant
                                                    Toast toast = Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT);
                                                    toast.show();
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    //Failed
                                    Toast toast = Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            });
                }
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