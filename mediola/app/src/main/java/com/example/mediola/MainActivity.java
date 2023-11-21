package com.example.mediola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText Email,Password;
    private TextView fpass,register,verifyemail;
    private Button login;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Email=findViewById(R.id.EmailLog);
        Password=findViewById(R.id.PasswordLog);
        fpass=findViewById(R.id.tvForgotPass);
        register=findViewById(R.id.tvSignIn);
        login=findViewById(R.id.btnlogin);
        verifyemail=findViewById(R.id.txtverifymail);
        fAuth = FirebaseAuth.getInstance();
        verifyemail.setVisibility(View.INVISIBLE);
        FirebaseUser user = fAuth.getCurrentUser();

        if(user != null){

            if(!user.isEmailVerified()){
                verifyemail.setVisibility(View.VISIBLE);
                verifyemail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {

                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(v.getContext(), "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                              //  Log.d("tag", "onFailure: Email not sent " + e.getMessage());
                            }
                        });
                    }
                });
            }
            else {
                startActivity(new Intent(MainActivity.this, home.class));
                finish();
            }
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Registration.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email=Email.getText().toString().trim();
                final String password=Password.getText().toString().trim();

                if(email .isEmpty() )
                {
                    Email .setError("Enter The Email");
                    return ;
                }
                if(password.isEmpty())
                {
                    Password .setError("Enter The Password");
                    return ;
                }

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            String id = fAuth.getCurrentUser().getUid();
                            Query reference = FirebaseDatabase.getInstance().getReference("Users").child(id);
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    Users users=snapshot.getValue(Users.class);

                                    if (users.getType().equals("user")) {
                                        FirebaseUser user1 = fAuth.getCurrentUser();
                                        if(!user1.isEmailVerified()){
                                            verifyemail.setVisibility(View.VISIBLE);
                                            verifyemail.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(final View v) {

                                                    user1.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(v.getContext(), "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            //  Log.d("tag", "onFailure: Email not sent " + e.getMessage());
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                        else {
                                            Toast.makeText(MainActivity.this, "Login In Successfull", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(MainActivity.this, home.class));
                                            finish();
                                        }

                                    } else {
                                        FirebaseAuth.getInstance().signOut();
                                        Toast.makeText(MainActivity.this, "You are not a user", Toast.LENGTH_SHORT).show();
                                    }

                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } else {
                            Toast.makeText(MainActivity.this, "Login In Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });

        fpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Forgotpassword.class));
            }
        });




    }
}