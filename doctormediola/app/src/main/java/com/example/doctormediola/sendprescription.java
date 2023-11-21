package com.example.doctormediola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctormediola.Notifications.Client;
import com.example.doctormediola.Notifications.Data;
import com.example.doctormediola.Notifications.MyResponse;
import com.example.doctormediola.Notifications.Sender;
import com.example.doctormediola.Notifications.Token;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sendprescription extends AppCompatActivity {

    private EditText pres;
    Intent intent;
    private DatePickerDialog datePickerDialog;
    private Button dateButton,sendpres,chat;
    APIService apiService;
    private ReportsAdapter reportsAdapter;
    private List<Upload> uploadList;
    private RecyclerView recyclerView;
    private TextView txtnoreport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendprescription);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton1);

        intent = getIntent();
        pres=findViewById(R.id.etpreacription);
        sendpres=findViewById(R.id.btnsendpre);
        chat=findViewById(R.id.btnchat);
        txtnoreport=findViewById(R.id.noreport);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        final String userid = intent.getStringExtra("userid");

        recyclerView = findViewById(R.id.reportryc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        uploadList = new ArrayList<>();
        dateButton.setText(getTodaysDate());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Reports").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uploadList.clear();
                if (dataSnapshot.exists()) {
                    txtnoreport.setVisibility(View.GONE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Upload upload = snapshot.getValue(Upload.class);

                        uploadList.add(upload);
                        reportsAdapter = new ReportsAdapter(getApplicationContext(), uploadList);
                        recyclerView.setAdapter(reportsAdapter);
                    }
                }
                else
                {
                    txtnoreport.setVisibility(View.VISIBLE);
                    txtnoreport.setText("No Reports Available");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                intent.putExtra("userid", userid);
                startActivity(intent);

            }
        });

        sendpres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String docid = intent.getStringExtra("doctorid");
                final String name = intent.getStringExtra("name");
                final String prestxt=pres.getText().toString();
                final String date=dateButton.getText().toString();
                Calendar c = Calendar.getInstance();
                Date time1= c.getTime();
                String time=String.valueOf(time1);


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Prescription").child(userid).child(date+time);


                final Map<String,Object> pre = new HashMap<>();
                pre.put("userid",userid);
                pre.put("docid",docid);
                pre.put("username",name);
                pre.put("prestext",prestxt);
                pre.put("time",time);
                pre.put("date",date);
                reference.setValue(pre);

                String msg="The Prescription has been received on "+date;
                sendNotifiaction(userid,msg,docid);
                DatabaseReference dreference = FirebaseDatabase.getInstance().getReference("Notification").child(userid).child(date+time);
                final Map<String,Object> noti = new HashMap<>();
                noti.put("sender",docid);
                noti.put("message",msg);
                noti.put("receiver",userid);

                dreference.setValue(noti);


            }
        });

    }

    private void sendNotifiaction(String receiver, final String message,final String docid) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(docid, R.mipmap.ic_launcher, message, "Prescription Notification",
                            receiver);

                    Sender sender = new Sender(data, token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200) {
                                        if (response.body().success != 1) {
                                            Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toast.makeText(getApplicationContext(), "Prescription Send Successfully",Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(),myappointment.class));
        finish();

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
                final String prestxt=pres.getText().toString();
                if(prestxt.isEmpty())
                {
                    pres.setError("Enter the prescription Details");
                    return;
                }

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
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
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