package com.example.drivermediola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;



public class userloaction extends AppCompatActivity {

    Intent intent;
    private TextView Name,Pno,Address;
    private Button Viewlocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userloaction);
        intent = getIntent();
        Name=findViewById(R.id.txtusername1);
        Pno=findViewById(R.id.txtphoneno1);
        Address =findViewById(R.id.txtuaddress);
        Viewlocation=findViewById(R.id.btnviewlocation);

        final String name = intent.getStringExtra("username");
        final String pno = intent.getStringExtra("Pno");
        final String address = intent.getStringExtra("address");

        Name.setText(name);
        Pno.setText(pno);
        Address.setText("ADDRESS: "+address);

        Viewlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://maps.google.com/maps?daddr="+address;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
                startActivity(intent);
            }
        });

    }
}