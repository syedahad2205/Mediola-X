package com.example.mediola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class drivercategory extends AppCompatActivity {

    private CardView MadhavNagar,EndPoint,TigerCircle,EshwarNagar,IndustrialArea,VPNagar,SyndicateCircle,Perampalli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivercategory);

        MadhavNagar=findViewById(R.id.cardMadhavNagar);
        EndPoint=findViewById(R.id.cardendpoint);
        TigerCircle=findViewById(R.id.cardtigercircle);
        EshwarNagar=findViewById(R.id.cardeshwarnagar);
        IndustrialArea=findViewById(R.id.cardindustrial);
        VPNagar=findViewById(R.id.cardvpnagar);
        SyndicateCircle=findViewById(R.id.cardsyndicate);
        Perampalli=findViewById(R.id.cardperampalli);


        MadhavNagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(drivercategory.this,viewdrivercategory.class);
                c.putExtra("location","Madhav Nagar");
                startActivity(c);
            }
        });

        EndPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(drivercategory.this,viewdrivercategory.class);
                c.putExtra("location","End Point");
                startActivity(c);
            }
        });

        TigerCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(drivercategory.this,viewdrivercategory.class);
                c.putExtra("location","Tiger Circle");
                startActivity(c);
            }
        });

        EshwarNagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(drivercategory.this,viewdrivercategory.class);
                c.putExtra("location","Eshwar Nagar");
                startActivity(c);
            }
        });

        IndustrialArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(drivercategory.this,viewdrivercategory.class);
                c.putExtra("location","Industrial Area");
                startActivity(c);
            }
        });

        VPNagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(drivercategory.this,viewdrivercategory.class);
                c.putExtra("location","VP Nagar");
                startActivity(c);
            }
        });

        SyndicateCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(drivercategory.this,viewdrivercategory.class);
                c.putExtra("location","Syndicate Circle");
                startActivity(c);
            }
        });

        Perampalli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c=new Intent(drivercategory.this,viewdrivercategory.class);
                c.putExtra("location","Perampalli");
                startActivity(c);
            }
        });
    }
    }
