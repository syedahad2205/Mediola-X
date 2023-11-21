package com.example.doctormediola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.doctormediola.Notifications.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class home extends AppCompatActivity {

    private CardView Vappoint,Profile,chat,social,viewdrivers,notification;
    private LinearLayout cate;
    FirebaseUser fuser;
    @Override
    public void onBackPressed() {

        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Vappoint=findViewById(R.id.cardviewappointment);
        Profile=findViewById(R.id.cardprofile);
        chat=findViewById(R.id.cardchat);
        social=findViewById(R.id.cardsocial);
        notification=findViewById(R.id.cardnotification);
        viewdrivers=findViewById(R.id.cardviewdrivers);
        cate=findViewById(R.id.bookap);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1473E6")));
        getSupportActionBar().setTitle("");
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        Vappoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(home.this,myappointment.class));
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
        updateToken(FirebaseInstanceId.getInstance().getToken());
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
    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(fuser.getUid()).setValue(token1);
    }
}