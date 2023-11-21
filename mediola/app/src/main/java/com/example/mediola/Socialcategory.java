package com.example.mediola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Socialcategory extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Socialcategory.this,home.class));
    }
    private CardView posts,chats,addfriend,requests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialcategory);

        posts=findViewById(R.id.cardpost);
        chats=findViewById(R.id.cardschats);
        addfriend=findViewById(R.id.cardaddfriend);
        requests=findViewById(R.id.cardfrequest);

        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Socialcategory.this,Socialmedia.class));
            }
        });

        chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              startActivity(new Intent(Socialcategory.this,chatmain1.class));
            }
        });

        addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Socialcategory.this,Addfriends.class));
            }
        });

        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Socialcategory.this,FriendRequest.class));
            }
        });

    }
}