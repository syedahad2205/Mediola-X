package com.example.doctormediola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Socialmedia extends AppCompatActivity {

    private CardView addpost,viewpost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialmedia);

        addpost=findViewById(R.id.addpost);
        viewpost=findViewById(R.id.viewpost);

        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Socialmedia.this,Addpost.class));
            }
        });

        viewpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Socialmedia.this,ViewPost.class));
            }
        });
    }
}