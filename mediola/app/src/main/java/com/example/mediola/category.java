package com.example.mediola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class category extends AppCompatActivity {

    private CardView Physicians,Pediatricians,Surgeon,Cardiologist,Dentist,Dermatologist,Gynecologist,Entspecial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Physicians=findViewById(R.id.cardphysicians);
        Pediatricians=findViewById(R.id.cardPediatricians);
        Surgeon=findViewById(R.id.cardsurgeon);
        Cardiologist=findViewById(R.id.cardcardiologist);
        Dentist=findViewById(R.id.carddentist);
        Dermatologist=findViewById(R.id.carddermatologist);
        Gynecologist=findViewById(R.id.cardgynecologist);
        Entspecial=findViewById(R.id.cardentspecial);

        Physicians.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(category.this,categoryview.class);
                c.putExtra("category","General Physicians");
                startActivity(c);
            }
        });

        Pediatricians.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(category.this,categoryview.class);
                c.putExtra("category","Pediatricians");
                startActivity(c);
            }
        });

        Surgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(category.this,categoryview.class);
                c.putExtra("category","General Surgeon");
                startActivity(c);
            }
        });

        Cardiologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(category.this,categoryview.class);
                c.putExtra("category","Cardiologist");
                startActivity(c);
            }
        });

        Dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(category.this,categoryview.class);
                c.putExtra("category","Dentist");
                startActivity(c);
            }
        });

        Dermatologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(category.this,categoryview.class);
                c.putExtra("category","Dermatologists");
                startActivity(c);
            }
        });

        Gynecologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(category.this,categoryview.class);
                c.putExtra("category","Gynecologist");
                startActivity(c);
            }
        });
        Entspecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(category.this,categoryview.class);
                c.putExtra("category","ENT Specialist");
                startActivity(c);
            }
        });
    }
}