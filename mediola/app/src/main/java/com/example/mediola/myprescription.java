package com.example.mediola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class myprescription extends AppCompatActivity {

private RecyclerView recyclerView;
private PrescriptionAdapter prescriptionAdapter;
private List<Priscription> priscriptionList;
DatabaseReference reference;
FirebaseUser fuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprescription);

        recyclerView = findViewById(R.id.mypresryc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        priscriptionList = new ArrayList<>();

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        final String id2= fuser.getUid();

        reference = FirebaseDatabase.getInstance().getReference("Prescription").child(id2);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                priscriptionList.clear();

                if(dataSnapshot.getValue() !=null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Priscription priscription = snapshot.getValue(Priscription.class);


                        priscriptionList.add(priscription);
                        prescriptionAdapter = new PrescriptionAdapter(getApplicationContext(), priscriptionList);
                        recyclerView.setAdapter(prescriptionAdapter);
                    }
                }
                else
                {
                    recyclerView.setAdapter(null);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}