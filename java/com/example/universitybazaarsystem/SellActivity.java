package com.example.universitybazaarsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SellActivity extends AppCompatActivity {
    // This is for image upload:
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private ProgressBar mprogressBar;
    private Uri mImageUri;
    private StorageReference mStorageRef;

    HashMap<String, String> userMap = new HashMap<>();

    // Database connection
    private FirebaseDatabase db = FirebaseDatabase.getInstance("https://universitybazaarsystem-d0619-default-rtdb.firebaseio.com/");
    // Getting a child called users to make more systematic
    private DatabaseReference root = db.getReference().child("Sales");
    private DatabaseReference root1 = db.getReference().child("Images");

    // now root points to the child called users and data will be added there
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // I didnot edit this
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        // This is for image upload
        mButtonChooseImage = findViewById(R.id.btnChooseFile);
        mButtonUpload = findViewById(R.id.btnUpload);
        mStorageRef = FirebaseStorage.getInstance().getReference("Sales");
        // mdatabse ref is root
        mprogressBar = findViewById(R.id.progress_bar);

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

    }

    public void onBtnClick(View view) {
        // This line is for text box:
        // every field has unique id we get that id which is in class form
        // then convert it to string
        EditText sellerNameTxt = findViewById(R.id.edtTxtSellerName);
        String sellerName = sellerNameTxt.getText().toString();
        // SAME APPLIES TO EVERYTHING BELOW
        EditText itemNameTxt = findViewById(R.id.edtTxtItemName);
        String itemName = itemNameTxt.getText().toString();

        EditText itemPriceTxt = findViewById(R.id.ediTxtPrice);
        String itemPrice = itemPriceTxt.getText().toString();

        EditText itemDescriptionTxt = findViewById(R.id.edtTxtDescription);
        String itemDescription = itemDescriptionTxt.getText().toString();

        EditText locationTxt = findViewById(R.id.edtTxtLocation);
        String location = locationTxt.getText().toString();

        // We use hashmap(which has a key and a value) to store the data
        // limit needed: none, we can add as much values as needed
        userMap.put("sellerName", sellerName);
        userMap.put("itemName", itemName);
        userMap.put("itemPrice", itemPrice);
        userMap.put("description", itemDescription);
        userMap.put("location", location);

        // Storing to the database ------v this is to notify the user that the data has
        // been stored
        root.push().setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SellActivity.this, "Item Listed On Buy", Toast.LENGTH_SHORT).show();
            }
        });

        // Now lets go to our next activity....
        startActivity(new Intent(SellActivity.this, ShowActivity.class));
    }

    public void onClickBack(View view) {
        startActivity(new Intent(SellActivity.this, ShowActivity.class));
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef
                    .child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mprogressBar.setProgress(0);
                        }
                    }, 500);
                    Toast.makeText(SellActivity.this, "Upload succesful", Toast.LENGTH_LONG).show();
                    SalesModel model = new SalesModel(
                            taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());

                    String uploadId = root1.push().getKey();

                    root1.child(uploadId).setValue(model);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SellActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    mprogressBar.setProgress((int) progress);
                }
            });
        } else {
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }
}
