package com.example.mediola;

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

public class profile1 extends AppCompatActivity {


    private TextView Fname,Lname,Email,Aadhaar,Phoneno,Address;
    private ImageView uprofile;
    private Button editprofile;
    DatabaseReference reference;
    FirebaseUser fuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);
        Fname =findViewById(R.id.txtfname);
        Lname =findViewById(R.id.txtlname);
        Email =findViewById(R.id.txtEmail);
        Aadhaar =findViewById(R.id.txtaadhar);
        Phoneno =findViewById(R.id.txtphoneno);
        uprofile=findViewById(R.id.userprofile);
        Address=findViewById(R.id.txtadress);
        editprofile=findViewById(R.id.btneditprofile);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Users users=snapshot.getValue(Users.class);
                if (users.getImageURL().equals("default")){
                    uprofile.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(getApplicationContext()).load(users.getImageURL()).into(uprofile);
                }
                Fname.setText(users.getFname());
                Lname.setText(users.getLname());
                Email.setText(users.getEmail());
                Aadhaar.setText(users.getAadhaarno());
                Phoneno.setText(users.getPhoneno());
                Address.setText(users.getAddress());

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