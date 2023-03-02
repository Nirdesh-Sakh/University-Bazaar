package com.example.universitybazaarsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Club_UniquePage extends AppCompatActivity
{
    // Declare an instance of FirebaseAuth
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private String clubID;
    private String clubName;
    private String description;
    private String contactInfo;
    private String myClubRole;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club__unique_page);

        // Actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Clubs");

        TextView cName = (TextView)findViewById(R.id.ClubName);
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
        Contact.setText(contactInfo);

        //firebaseAuth = FirebaseAuth.getInstance();
        //loadMyClubRole();

    }

    private String loadMyClubRole()
    {
        //create string to store result
        final String[] result = new String[1];

        firebaseAuth = FirebaseAuth.getInstance();

        //determine if person is creator of club or a participant
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Clubs");
        ref.child(clubID).child("Participants").orderByChild("uid")
                .equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        for (DataSnapshot ds: snapshot.getChildren())
                        {
                            myClubRole = ""+ds.child("role").getValue();

                            if (myClubRole.equals("creator"))
                            {
                                result[0] = "Creator";
                            }
                            else
                            {
                                result[0] = "Member";
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {

                    }
                });
        return result[0];
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.club_menu, menu);

        String user = loadMyClubRole();

        //if user is creator, hide Leave option
        if (user.equals("Creator")) {
            menu.findItem(R.id.leaveOption).setVisible(false);
        }

        //if user is member only, hide update, delete, and createAd options
        if (user.equals("Member")) {
            menu.findItem(R.id.updateOption).setVisible(false);
            menu.findItem(R.id.deleteOption).setVisible(false);
            menu.findItem(R.id.createAd).setVisible(false);
        }

        return true;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        firebaseAuth = FirebaseAuth.getInstance();
        //loadMyClubRole();
        String dialogTitle;
        String dialogDescription;
        String positiveButtonTitle;

        switch(item.getItemId())
        {
            case R.id.updateOption:
                //Toast.makeText(this, "Update Option selected", Toast.LENGTH_SHORT).show();
                //Go to new club page
                Bundle extras = new Bundle();
                extras.putString("Club ID", clubID);
                extras.putString("Club Name", clubName);
                extras.putString("Club Description", description);
                extras.putString("Contact Information", contactInfo);

                Intent downloadIntent = new Intent(getApplicationContext(), ClubEdit.class);
                downloadIntent.putExtras(extras);
                startActivity(downloadIntent);
                return true;
            case R.id.deleteOption:
                //Toast.makeText(this, "Delete Option selected", Toast.LENGTH_SHORT).show();
                dialogTitle="Delete Club";
                dialogDescription="Are you sure you want to delete club permanently?";
                positiveButtonTitle="DELETE";

                AlertDialog.Builder builder = new AlertDialog.Builder(Club_UniquePage.this);
                builder.setTitle(dialogTitle).setMessage(dialogDescription)
                        .setPositiveButton(positiveButtonTitle, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteClub();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                return true;
            case R.id.leaveOption:
                //Toast.makeText(this, "Leave Option selected", Toast.LENGTH_SHORT).show();
                dialogTitle="Leave Club";
                dialogDescription="Are you sure you want to leave club?";
                positiveButtonTitle="LEAVE";

                AlertDialog.Builder builder2 = new AlertDialog.Builder(Club_UniquePage.this);
                builder2.setTitle(dialogTitle).setMessage(dialogDescription)
                        .setPositiveButton(positiveButtonTitle, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                leaveClub();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                return true;
            case R.id.createAd:
                Toast.makeText(this, "Create Ad Option selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void leaveClub()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Clubs");
        ref.child(clubID).child("Participants").child(firebaseAuth.getUid())
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        //club left successfully
                        Toast .makeText(Club_UniquePage.this, "Club left successfully...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Club_UniquePage.this, ClubHomePage.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        //failed to leave club
                        Toast.makeText(Club_UniquePage.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteClub()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Clubs");
        ref.child(clubID).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //club deleted successfully
                        Toast .makeText(Club_UniquePage.this, "Club was deleted successfully...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Club_UniquePage.this, ClubHomePage.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed to delete club
                        Toast.makeText(Club_UniquePage.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}