package com.example.doctormediola;

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


    private TextView Fname,Lname,Email,Category,Phoneno,Discription;
    private ImageView dprofile;
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
        Category =findViewById(R.id.txtcategory);
        Phoneno =findViewById(R.id.txtphoneno);
        Discription =findViewById(R.id.txtdiscription);
        dprofile=findViewById(R.id.dprofileimg);
        editprofile=findViewById(R.id.btneditprofile);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                Doctors doc=snapshot.getValue(Doctors.class);

                if (doc.getImageURL().equals("default")){
                    dprofile.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(getApplicationContext()).load(doc.getImageURL()).into(dprofile);
                }
                Fname.setText(doc.getFname());
                Lname.setText(doc.getLname());
                Email.setText(doc.getEmail());
                Category.setText(doc.getCategory());
                Phoneno.setText(doc.getPhoneno());
                Discription.setText(doc.getDiscription());

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