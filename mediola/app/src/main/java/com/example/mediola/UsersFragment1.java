package com.example.mediola;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment1 extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter2 userAdapter2;
    private List<Friends> mUsers;
    FirebaseUser fuser;
    private TextView txtnofriend;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users1, container, false);


        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList<>();
        txtnofriend=view.findViewById(R.id.nofriend);
        readUsers();


        return view;
    }


    private void readUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Query reference = FirebaseDatabase.getInstance().getReference("Friends").child(firebaseUser.getUid()).orderByChild("friend").equalTo("true");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                    mUsers.clear();
                if (dataSnapshot.exists()) {
                    txtnofriend.setVisibility(View.GONE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Friends user = snapshot.getValue(Friends.class);
                        mUsers.add(user);


                    }

                    userAdapter2 = new UserAdapter2(getContext(), mUsers, false);
                    recyclerView.setAdapter(userAdapter2);
                }
                else
                {
                    txtnofriend.setVisibility(View.VISIBLE);
                    txtnofriend.setText("You don't have any friends.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
