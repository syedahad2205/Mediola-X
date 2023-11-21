package com.example.doctormediola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendRequest extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RequestAdapter requestAdapter;
    private List<Friends> mUsers;
    private TextView txtnorequest;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(FriendRequest.this,Socialcategory.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        recyclerView = findViewById(R.id.reqryc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        txtnorequest=findViewById(R.id.norequest);
        mUsers = new ArrayList<>();

        readUsers();


    }


    private void readUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String id=firebaseUser.getUid();
        Query reference = FirebaseDatabase.getInstance().getReference("Requests").child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUsers.clear();
                if (dataSnapshot.exists()) {
                    txtnorequest.setVisibility(View.GONE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Friends friends = snapshot.getValue(Friends.class);

                        mUsers.add(friends);

                        requestAdapter = new RequestAdapter(getApplicationContext(), mUsers);
                        recyclerView.setAdapter(requestAdapter);
                    }
                }
                else
                {
                    txtnorequest.setVisibility(View.VISIBLE);
                    txtnorequest.setText("You don't have any friend request");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}