package com.example.mediola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mediola.Notifications.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    private EditText Fname,Lname,Aadhaarno,Pno,Email,Password,Address;
    private Button signup;
    private TextView signin;

    private FirebaseAuth fauth;
    private DatabaseReference dreference;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Fname=findViewById(R.id.UserfnameReg);
        Lname=findViewById(R.id.UserlnameReg);
        Aadhaarno=findViewById(R.id.UseraadharReg);
        Pno=findViewById(R.id.UserphonenoReg);
        Email=findViewById(R.id.UserEmailReg);
        Password=findViewById(R.id.UserPasswordReg);
        Address=findViewById(R.id.Useraddress);
        signup=findViewById(R.id.btnSignUp);
        signin=findViewById(R.id.tvSignIn);
        fauth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fname=Fname.getText().toString().trim();
                final String lname=Lname.getText().toString().trim();
                final String aadhaar=Aadhaarno.getText().toString().trim();
                final String pno=Pno.getText().toString().trim();
                final String email=Email.getText().toString().trim();
                final String password=Password.getText().toString().trim();
                final String address=Address.getText().toString().trim();

                if(fname.isEmpty() || lname.isEmpty() || aadhaar.isEmpty() || pno.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty())
                {
                    Toast.makeText(Registration.this,"Please Enter All the Fields",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(aadhaar.length()!=12)
                {
                    Aadhaarno.setError("Invalid Aadhaarno");
                    return;
                }
                if(pno.length()!=10)
                {
                    Pno.setError("Invalid Phone number");
                    return;
                }
                if(password.length() < 6)
                {
                    Password.setError("Password should be more than 5 characters");
                    return;
                }
                progressDialog.setMessage("This can take a few seconds");
                progressDialog.show();

                fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser firebaseUser = fauth.getCurrentUser();
                            firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Registration.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                            String userid = firebaseUser.getUid();
                            dreference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            Map<String,String> user = new HashMap<>();
                            user.put("id",userid);
                            user.put("fname",fname);
                            user.put("lname",lname);
                            user.put("Phoneno",pno);
                            user.put("aadhaarno",aadhaar);
                            user.put("email",email);
                            user.put("imageURL", "default");
                            user.put("address",address);
                            user.put("status","offline");
                            user.put("type","user");
                            user.put("search",fname.toLowerCase());

                            dreference.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();
                                    updateToken(userid,FirebaseInstanceId.getInstance().getToken());
                                   // Toast.makeText(Registration.this, "User Created.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Registration.this,MainActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Registration.this, "User Creation Error.", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    }
                });

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Registration.this,MainActivity.class));
                finish();
            }
        });






    }
    private void updateToken(String userid,String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(userid).setValue(token1);
    }

}