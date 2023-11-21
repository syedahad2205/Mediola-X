package com.example.mediola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class home extends AppCompatActivity {

    private CardView Category,Profile,chat,social,viewdrivers,notification,mypres,medical;
    private LinearLayout cate;

    @Override
    public void onBackPressed() {

        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Category=findViewById(R.id.cardbooking);
        notification=findViewById(R.id.cardnotification);
        Profile=findViewById(R.id.cardprofile);
        chat=findViewById(R.id.cardchat);
        social=findViewById(R.id.cardsocial);
        viewdrivers=findViewById(R.id.cardviewdrivers);
        mypres=findViewById(R.id.cardmypres);
        medical=findViewById(R.id.cardmedicalrecord);
        cate=findViewById(R.id.bookap);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1473E6")));
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setIcon(R.drawable.ac);


        Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(home.this,category.class));
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(home.this,profile1.class));
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home.this,chatmain.class));
            }
        });


        social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(home.this,Socialcategory.class));
            }
        });

        viewdrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home.this,drivercategory.class));
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(home.this,notifications.class));
            }
        });

        mypres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this,myprescription.class));
            }
        });
        medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this,Addreports.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case  R.id.logout:
                FirebaseAuth.getInstance().signOut();
                // change this code beacuse your app will crash
                startActivity(new Intent(home.this,MainActivity .class));
                finish();
                return true;

        }

        return false;
    }
}