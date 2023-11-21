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

public class viewdrivercategory extends AppCompatActivity {

    private RecyclerView recyclerView;

    private Driveradapter drivercategory;
    private List<Drivers> drivers;
    private TextView txtnodriver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdrivercategory);

        recyclerView = findViewById(R.id.driverryc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        drivers = new ArrayList<>();
        txtnodriver=findViewById(R.id.nodriver);
        final String location=getIntent() .getStringExtra("location");
        System.out.println("<><><>"+location);
        Query reference = FirebaseDatabase.getInstance().getReference("Users").orderByChild("location").equalTo(location);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                drivers.clear();

                if (dataSnapshot.exists()) {
                    txtnodriver.setVisibility(View.GONE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Drivers driver = snapshot.getValue(Drivers.class);


                        drivers.add(driver);


                        drivercategory = new Driveradapter(getApplicationContext(), drivers);
                        recyclerView.setAdapter(drivercategory);
                    }
                }
                else
                {
                    txtnodriver.setVisibility(View.VISIBLE);
                    txtnodriver.setText("No driver Available");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}