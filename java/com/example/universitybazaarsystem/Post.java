package com.example.universitybazaarsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Post extends AppCompatActivity {

    private FirebaseDatabase db = FirebaseDatabase.getInstance("https://universitybazaarsystem-d0619-default-rtdb.firebaseio.com/");
    private DatabaseReference root = db.getReference().child("Posts");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_post);
        Button cancel = findViewById(R.id.btn_cancel);
        Button post = findViewById(R.id.btn_post);
        EditText postContent = findViewById(R.id.create_post);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = postContent.getText().toString();
                HashMap<String, String>  postText = new HashMap<>();

                postText.put("post",content);
                //root.push().setValue(postContent);
                root.push().setValue(postText).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(Post.this,"Success",Toast.LENGTH_SHORT).show();
                    }
                });


                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


//        EditText edit = (EditText) findViewById(R.id.create_post);
//        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        keyboard.showSoftInput(edit, 0);
//        edit.requestFocus();
//        keyboard.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_down, R.anim.do_nothing);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

    }
    @Override
    public void finish() {
        super.finish();


//        overridePendingTransition(R.anim.slide_down, R.anim.do_nothing);
    }
}