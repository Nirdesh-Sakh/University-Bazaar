package com.example.universitybazaarsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Viewholder> {
    Context message;
    ArrayList<Users> usersArrayList;
    public UserAdapter(Message message, ArrayList<Users> usersArrayList){
        this.message = message;

        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(message).inflate(R.layout.iter_user_row,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Users users = usersArrayList.get(position);
        holder.user_name.setText(users.firstName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(message, Chats.class);
                intent.putExtra("name",users.getFirstName());
                message.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {

        return usersArrayList.size();
    }

        public static class Viewholder extends RecyclerView.ViewHolder{
            //CircleImageView user_profile;
            TextView user_name;

            public Viewholder(@NonNull View itemView) {
                super(itemView);

                //user_profile = itemView.findViewById(R.id.)
                user_name = itemView.findViewById(R.id.user_name);



            }
        }

}
