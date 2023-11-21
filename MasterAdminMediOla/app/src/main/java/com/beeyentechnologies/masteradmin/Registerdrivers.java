package com.syd.masteradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.syd.masteradmin.Notifications.Token;
import com.syd.aaa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class Registerdrivers extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText Fname,Lname,Pno,Email,Password;
    private Button Register;
    private FirebaseAuth fauth;
    private DatabaseReference dreference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerdrivers);

        Fname=findViewById(R.id.adfname);
        Lname=findViewById(R.id.adlname);
        Pno=findViewById(R.id.adphoneno);
        Email=findViewById(R.id.adEmailReg);
        Password=findViewById(R.id.adPasswordReg);
        Register=findViewById(R.id.adregister);
        fauth = FirebaseAuth.getInstance();
        final Spinner spinner = (Spinner) findViewById(R.id.location_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Adding ambulance Deriver");

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fname=Fname.getText().toString().trim();
                final String lname=Lname.getText().toString().trim();
                final String pno=Pno.getText().toString().trim();
                final String email=Email.getText().toString().trim();
                final String password=Password.getText().toString().trim();
                final String location=spinner.getSelectedItem() .toString();

                if(fname.isEmpty() || lname.isEmpty()  || pno.isEmpty() || email.isEmpty() || password.isEmpty() || location.isEmpty())
                {
                    Toast.makeText(Registerdrivers.this,"Please Enter All the Fields",Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(Registerdrivers.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                            String userid = firebaseUser.getUid();
                            updateToken(userid, FirebaseInstanceId.getInstance().getToken());
                            dreference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            Map<String,String> user = new HashMap<>();
                            user.put("id",userid);
                            user.put("fname",fname);
                            user.put("lname",lname);
                            user.put("phoneno",pno);
                            user.put("email",email);
                            user.put("imageURL", "default");
                            user.put("location",location);
                            user.put("type","driver");
                            user.put("status","offline");
                            user.put("search",fname.toLowerCase());
                            dreference.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Registerdrivers.this, "Ambulance Driver Registration Successful", Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(Registerdrivers.this,home.class));
//                                    finish();
                                    progressDialog.dismiss();
                                    Intent i = new Intent(Registerdrivers.this,MainActivity.class);
                                    startActivity(i);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Registerdrivers.this, "Driver Registration Error", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });
                        }

                    }
                });
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void updateToken(String userid,String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(userid).setValue(token1);
    }
}