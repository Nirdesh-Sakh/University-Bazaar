package com.example.universitybazaarsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterMyClubList extends RecyclerView.Adapter<AdapterMyClubList.HolderMyClubList>
{
    private Context context;
    private ArrayList<MyClubList> myClubLists;

    public AdapterMyClubList(Context context, ArrayList<MyClubList> myClubLists)
    {
        this.context = context;
        this.myClubLists = myClubLists;
    }

    @NonNull
    @Override
    public HolderMyClubList onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.activity_my_clubs, parent, false);
        return new HolderMyClubList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMyClubList holder, int position)
    {
        //get data
        MyClubList model = myClubLists.get(position);
        String clubId = model.getClubID();
        String clubName = model.getClubTitle();
        String description = model.getClubDescription();
        String contactInformation = model.getContactInfo();

        //set data
        holder.ClubName.setText(clubName);

        //handle club click
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Go to the club page
                Bundle extras = new Bundle();
                extras.putString("Club Name", clubName);
                extras.putString("Club Description", description);
                extras.putString("Contact Information", contactInformation);

                Intent downloadIntent = new Intent(context, Club_UniquePage.class);
                downloadIntent.putExtras(extras);
                context.startActivity(downloadIntent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return myClubLists.size();
    }

    //view holder class
    class HolderMyClubList extends RecyclerView.ViewHolder
    {
        //UI views
        private TextView ClubName;

        public HolderMyClubList(@NonNull View itemView)
        {
            super(itemView);
            ClubName = itemView.findViewById(R.id.ClubName);
        }
    }
}
