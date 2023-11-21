package com.example.doctormediola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addcomment extends AppCompatActivity {

    private EditText commenttxt;
    private ImageButton btn_send;
    private ImageView Postimg;
    private DatabaseReference dreference,reference;
    FirebaseUser fuser;
    RecyclerView recyclerView;
    CommentAdapter commentAdapter;
    List<Comments> commentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcomment);

        commenttxt=findViewById(R.id.text_comment);
        btn_send=findViewById(R.id.cbtn_send);
        Postimg =findViewById(R.id.cpostimg);
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = findViewById(R.id.commentryc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentsList = new ArrayList<>();

        String postimglink=getIntent().getStringExtra("postimg");
        final String postid=getIntent().getStringExtra("postid");

        Glide.with(this).load(postimglink).into(Postimg);


        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Comments").child(postid);

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Comments com = snapshot.getValue(Comments.class);



                    commentsList.add(com);


                    commentAdapter = new CommentAdapter(getApplicationContext(), commentsList);
                    recyclerView.setAdapter(commentAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Date date = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat tf= new SimpleDateFormat("HH:mm:ss");
                final String formattedDate = df.format(date);
                final String  time=tf.format(date);

                final String com=commenttxt.getText().toString().trim();

                if(com.isEmpty())
                {
                    Toast.makeText(addcomment.this,"Enter the comment",Toast.LENGTH_SHORT).show();
                    return;
                }


                reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Users users=snapshot.getValue(Users.class);
                        final String name=users.getFname();
                        final String userimg=users.getImageURL();

                        dreference = FirebaseDatabase.getInstance().getReference("Comments").child(postid).child(formattedDate+"+"+time);
                        final Map<String,Object> comment = new HashMap<>();
                        comment.put("postid",postid);
                        comment.put("name",name);
                        comment.put("userimg",userimg);
                        comment.put("comment",com);
                        comment.put("time",time);
                        comment.put("date",formattedDate);

                        dreference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(addcomment.this, "Comment Added Successful", Toast.LENGTH_SHORT).show();


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





            }
        });


    }
}