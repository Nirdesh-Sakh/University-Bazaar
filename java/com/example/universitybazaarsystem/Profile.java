package com.example.universitybazaarsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    EditText etBio, etMajor, etEmail, etWeb, etname;
    Button button;
    ImageView imageView;
    Uri imageUri;
    UploadTask uploadTask;
    StorageReference storageReference;
   // FirebaseDatabase database = FirebaseDatabase.getInstance("https://universitybazaarsystem-d0619-default-rtdb.firebaseio.com/");
    DatabaseReference databaseReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    Button cancel;
    Button viewPost;

    //Nirdesh update
    FirebaseAuth F_auth;
    FirebaseFirestore F_store;
    String User_id;


    //private static final int Pick_Image = 1;

    AllUserMember member;
    //String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        member = new AllUserMember();
        imageView = findViewById(R.id.iv_cp);
        etBio = findViewById(R.id.et_bio_cp);
        etEmail = findViewById(R.id.et_email_cp);
        etname = findViewById((R.id.et_name_cp));
        etMajor = findViewById(R.id.et_major_cp);
        etWeb=findViewById(R.id.et_website_cp);
        button = findViewById(R.id.btn_cp);
        cancel = findViewById(R.id.btn_cancel);
        viewPost = findViewById(R.id.btn_viewpost);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //currentUserId = user.getUid();
       // documentReference = db.collection("user").document(currentUserId);
       storageReference = FirebaseStorage.getInstance().getReference("Profile Images");
       // databaseReference = database.getReference("All Users");

        //Nirdesh update

        F_auth=FirebaseAuth.getInstance();
        F_store=FirebaseFirestore.getInstance();
        User_id=F_auth.getCurrentUser().getUid();
        DocumentReference documentReference=F_store.collection("Users").document(User_id);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                etname.setText("FULL NAME:        "+value.getString("First name")+" "+value.getString("Last name"));
                etMajor.setText("DATE OF BIRTH:  "+value.getString("Date of Birth"));
                etBio.setText("USERNAME:        "+value.getString("Username"));
                etEmail.setText("EMAIL:           "+value.getString("Email"));
                etWeb.setText("UNIVERSITY ID:   "+value.getString("University ID"));
            }
        });
        StorageReference profileRef=storageReference.child("Users/"+F_auth.getCurrentUser().getUid()+"/profileimage.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageView);
            }
        });
        //Nirdesh

        viewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Home.class));
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        //Nirdesh update
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /*     Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivity(intent);*/
                Intent openGallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery,1000);
            }

        });
        //Nirdesh


    }

    /*  @Override
    public void finish() {
        super.finish();
        //overridePendingTransition(R.anim.slide_down, R.anim.do_nothing);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      /*  try{
            if(requestCode == Pick_Image || resultCode == RESULT_OK || data!=null || data.getData() != null){
                imageUri = data.getData();

                Picasso.get().load(imageUri).into(imageView);
            }
        }catch(Exception e){
            Toast.makeText(this, "Error"+e,Toast.LENGTH_SHORT).show();*/
        if(requestCode==1000)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                Uri imageUri=data.getData();

               // imageView.setImageURI(imageUri);

                uploadimage(imageUri);
            }

        }
    }

    private void uploadimage(Uri imageUri)
    {
        final StorageReference file_ref=storageReference.child("Users/"+F_auth.getCurrentUser().getUid()+"/profileimage.jpg");
        file_ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Profile.this, "Image successfully uploaded", Toast.LENGTH_SHORT).show();
                file_ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(imageView);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "Failed to upload Image", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*private String getFileExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }*/



    /*private void uploadData(){
        String name = etname.getText().toString();
        String bio = etBio.getText().toString();
        String web = etWeb.getText().toString();
        String email = etEmail.getText().toString();
        String major = etMajor.getText().toString();

        if(!TextUtils.isEmpty(name)||!TextUtils.isEmpty(bio)||!TextUtils.isEmpty(email)||!TextUtils.isEmpty(web)||!TextUtils.isEmpty(major)){
            final StorageReference reference = storageReference.child(System.currentTimeMillis()+"."+getFileExt(imageUri));
            uploadTask = reference.putFile(imageUri);

            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw  task.getException();

                    }
                    return reference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();

                        Map<String,String> profile = new HashMap<>();
                        profile.put("name",name);
                        profile.put("major",major);
                        profile.put("url",downloadUri.toString());
                        profile.put("email",email);
                        profile.put("web",web);
                        profile.put("bio",bio);
                        profile.put("privacy","Public");

                        member.setName(name);
                        member.setMajor(major);
                        member.setUid(currentUserId);
                        member.setUrl(downloadUri.toString());

                        databaseReference.child(currentUserId).setValue(member);
                        documentReference.set(profile)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Profile.this,"profile created",Toast.LENGTH_SHORT).show();
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(Profile.this,"profile created",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Profile.this,FragmentHome.class);
                                                startActivity(intent);
                                            }
                                        },2000);
                                    }
                                });
                    }
                }
            });
        }
    }*/
}