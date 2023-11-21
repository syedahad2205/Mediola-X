package com.example.mediola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewPost extends AppCompatActivity {

    private RecyclerView recyclerView;

    private PostAdapter postAdapter;
    private List<Post> postList;
    private List<Post> postList1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        recyclerView = findViewById(R.id.postrecyc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postList = new ArrayList<>();
        postList1 = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);

                    postList.add(post);
                   /* postAdapter = new PostAdapter(getApplicationContext(),postList);
                    recyclerView.setAdapter(postAdapter);*/
                }
                postList1.clear();
                for(int i=postList.size()-1;i>=0;i--)
                {
                    String date, imagelink, name, postid, time, userimg, totallike, title, dis,approved;

                    approved= postList.get(i).getApproved();
                    imagelink= postList.get(i).getImagelink();
                    date=postList.get(i).getDate();
                    dis= postList.get(i).getDis();
                    name=postList.get(i).getName();
                    postid=postList.get(i).getPostid();
                    time=postList.get(i).getTime();
                    title= postList.get(i).getTitle();
                    totallike= postList.get(i).getTotallike();
                    userimg=postList.get(i).getUserimg();


                    Post post1=new Post(date, imagelink, name, postid, time, userimg, totallike, title, dis,approved);


                     postList1.add(post1);
                    postAdapter = new PostAdapter(getApplicationContext(),postList1);
                    recyclerView.setAdapter(postAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}