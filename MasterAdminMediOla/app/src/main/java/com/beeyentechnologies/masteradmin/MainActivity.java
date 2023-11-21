package com.syd.masteradmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.syd.aaa.R;

public class MainActivity extends AppCompatActivity {

    Button adddctr,adddriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adddctr=findViewById(R.id.btnregisterdctr);
        adddriver=findViewById(R.id.btnregisterdriver);

        adddctr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Doctorreg.class);
                startActivity(i);
            }
        });
        adddriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Registerdrivers.class);
                startActivity(i);
            }
        });

    }
}