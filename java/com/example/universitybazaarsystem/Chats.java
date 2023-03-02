package com.example.universitybazaarsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chats extends AppCompatActivity {


    String ReceiverName, ReceiverUID, SenderUID;
    CircleImageView profileImage;
    TextView receiverName;
    Button back;
    CardView sendBtn;
    EditText editMessage;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    String senderRoom, receiverRoom;
    RecyclerView messageAdapter;
    ArrayList<MessageModel> messagesArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        //putting name from message to chat
        ReceiverName = getIntent().getStringExtra("name");
        ReceiverUID = getIntent().getStringExtra("uid");
        profileImage = findViewById(R.id .profile_image);
        receiverName = findViewById(R.id.receiver_name);
        receiverName.setText(""+ReceiverName);
        sendBtn = findViewById(R.id.send_btn);
        editMessage = findViewById(R.id.edit_message);
        back = findViewById(R.id.btn_close);

        database = FirebaseDatabase.getInstance("https://universitybazaarsystem-d0619-default-rtdb.firebaseio.com/");
        firebaseAuth = FirebaseAuth.getInstance();
        SenderUID = firebaseAuth.getUid();
        messageAdapter = findViewById(R.id.message_adapter);
        senderRoom = SenderUID+ReceiverUID;
        receiverRoom = ReceiverUID+SenderUID;
        messagesArrayList = new ArrayList<>();

        DatabaseReference reference= database.getReference().child("Sales").child(firebaseAuth.getUid());
        DatabaseReference chatReference= database.getReference().child("Chats").child(senderRoom).child("messages");


        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MessageModel messages = dataSnapshot.getValue(MessageModel.class);
                    messagesArrayList.add(messages);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),Message.class));
                finish();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editMessage.getText().toString();
                if(message.isEmpty()){
                    Toast.makeText(Chats.this,"Please enter valid message",Toast.LENGTH_SHORT).show();
                    return;
                }
                editMessage.setText("");
                Date date = new Date();

                MessageModel messages = new MessageModel(message,SenderUID,date.getTime());

                database = FirebaseDatabase.getInstance();
                database.getReference().child("Chats")
                        .child(senderRoom)
                        .child("messages")
                        .push()
                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        database.getReference().child("Chats")
                                .child(receiverRoom)
                                .child("messages")
                                .push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                });

            }
        });
    }
}