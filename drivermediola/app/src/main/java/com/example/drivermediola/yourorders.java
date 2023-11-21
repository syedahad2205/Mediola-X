package com.example.drivermediola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class yourorders extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private RecyclerView recyclerView;
    private PrescriptionAdapter prescriptionAdapter;
    private List<Priscription> priscriptionList;
    DatabaseReference reference;
    private TextView txtnoapp;
    FirebaseUser fuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourorders);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton1);
        recyclerView = findViewById(R.id.viewyourorders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        priscriptionList = new ArrayList<>();
        txtnoapp=findViewById(R.id.noapp);
        dateButton.setText(getTodaysDate());
        txtnoapp.setVisibility(View.VISIBLE);
        txtnoapp.setText("You don't have any delivery on this day.");
    }



    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                final String date2=dateButton.getText().toString();
                fuser = FirebaseAuth.getInstance().getCurrentUser();
                final String id2= fuser.getUid();


                reference = FirebaseDatabase.getInstance().getReference("Prescription1").child(id2).child(date2);

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        priscriptionList.clear();

                        if (dataSnapshot.exists()) {
                            txtnoapp.setVisibility(View.GONE);
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
                            txtnoapp.setVisibility(View.VISIBLE);
                            txtnoapp.setText("You don't have any delivery on this day.");
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }


}