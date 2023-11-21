package com.example.mediola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class categoryview extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView txtnodoc;
    private Doctorcategory doctorcategory;
    private List<Doctors> doctors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoryview);
        recyclerView = findViewById(R.id.doctorcategoryryc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctors = new ArrayList<>();
         txtnodoc=findViewById(R.id.nodoc);
        final String category=getIntent() .getStringExtra("category");
        Query reference = FirebaseDatabase.getInstance().getReference("Users").orderByChild("category").equalTo(category);;

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctors.clear();

                if (dataSnapshot.exists()) {
                    txtnodoc.setVisibility(View.GONE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Doctors doc = snapshot.getValue(Doctors.class);


                        doctors.add(doc);


                        doctorcategory = new Doctorcategory(getApplicationContext(), doctors);
                        recyclerView.setAdapter(doctorcategory);
                    }
                }
                else
                {
                    txtnodoc.setVisibility(View.VISIBLE);
                    txtnodoc.setText("Sorry! Doctors Not Available.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}