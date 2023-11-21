package com.example.drivermediola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Driver;

public class profile1 extends AppCompatActivity {


    private TextView Fname,Lname,Email,Location,Phoneno;
    private ImageView uprofile;
    DatabaseReference reference;
    private Button editprofile;
    FirebaseUser fuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);
        Fname =findViewById(R.id.txtfname);
        Lname =findViewById(R.id.txtlname);
        Email =findViewById(R.id.txtEmail);
        Location =findViewById(R.id.txtlocation);
        Phoneno =findViewById(R.id.txtphoneno);
        uprofile=findViewById(R.id.uprofile);
        editprofile=findViewById(R.id.btneditprofile);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Drivers users=snapshot.getValue(Drivers.class);

                if (users.getImageURL().equals("default")){
                    uprofile.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(getApplicationContext()).load(users.getImageURL()).into(uprofile);
                }
                Fname.setText(users.getFname());
                Lname.setText(users.getLname());
                Email.setText(users.getEmail());
                Location.setText(users.getLocation());
                Phoneno.setText(users.getPhoneno());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(profile1.this,Editprofile.class));
                finish();
            }
        });
    }
}