package com.example.universitybazaarsystem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyClubListFragment extends Fragment
{
    private RecyclerView clubsRV;

    private FirebaseAuth firebaseAuth;

    private ArrayList<MyClubList> clubLists;
    private AdapterMyClubList adapterMyClubList;

    public MyClubListFragment()
    {
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_club_list, container, false);
        clubsRV = view.findViewById(R.id.clubsRV);

        firebaseAuth = FirebaseAuth.getInstance();

        loadClubList();

        return view;
    }

    private void loadClubList()
    {
        clubLists = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Clubs");
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                clubLists.size();
                for (DataSnapshot ds: snapshot.getChildren())
                {
                    //if current user's uid exists in participants list then show that group
                    if (ds.child("Participants").child(firebaseAuth.getUid()).exists())
                    {
                        MyClubList model = ds.getValue(MyClubList.class);
                        clubLists.add(model);
                    }
                }
                adapterMyClubList = new AdapterMyClubList(getActivity(), clubLists);
                clubsRV.setAdapter(adapterMyClubList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

