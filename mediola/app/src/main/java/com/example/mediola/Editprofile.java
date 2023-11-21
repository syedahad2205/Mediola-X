package com.example.mediola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Editprofile extends AppCompatActivity {
    private TextView Fname,Lname,Aadhaar,Phoneno,Address;
    private ImageView uprofile;
    private Button save;
    FirebaseAuth fauth;
    DatabaseReference reference;
    FirebaseUser fuser;
    static int REQUESCODE = 1 ;
    static int PReqCode = 1 ;
    private String imguri,imageURL;
    Uri pickedImgUri ;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        Fname =findViewById(R.id.etfname);
        Lname =findViewById(R.id.etlname);
        Aadhaar =findViewById(R.id.etaadhar);
        Phoneno =findViewById(R.id.etphoneno);
        uprofile=findViewById(R.id.userprofile1);
        Address=findViewById(R.id.etadress);
        save=findViewById(R.id.btnsave);
        progressDialog = new ProgressDialog(this);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        fauth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Users users=snapshot.getValue(Users.class);
                if (users.getImageURL().equals("default")){
                    uprofile.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(getApplicationContext()).load(users.getImageURL()).into(uprofile);
                }
                imguri=users.getImageURL();
                Fname.setText(users.getFname());
                Lname.setText(users.getLname());
                Aadhaar.setText(users.getAadhaarno());
                Phoneno.setText(users.getPhoneno());
                Address.setText(users.getAddress());

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        uprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION .SDK_INT >=22)
                {
                    checkAndRequestForPermission();

                }
                else
                {
                    openGallery();
                }

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressDialog.setMessage("This can take a few seconds");
                progressDialog.show();

               String fname= Fname.getText().toString().trim();
                String lname= Lname.getText().toString().trim();
                String aadhar= Aadhaar.getText().toString().trim();
                String pno= Phoneno.getText().toString().trim();
                String address= Address.getText().toString().trim();

                String userID = fauth.getCurrentUser().getUid();

                if(pickedImgUri ==null )
                {
                    imageURL=imguri;
                    System.out.println("<><><>"+imageURL);
                }
                else {
                    StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
                    final StorageReference imageFilepath = mStorage.child(pickedImgUri.getLastPathSegment());
                    imageFilepath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // image uploaded succesfully
                            // now we can get our image url

                            imageFilepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // uri contain user image url
                                    final String imglnk = uri.toString();

                                    imageURL=imglnk;
                                    System.out.println("<><><>"+imageURL);
                                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child(userID);
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("imageURL",imageURL);
                                    reference1.updateChildren(user);

                                }
                            });


                        }
                    });
                }
                reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
                Map<String,Object> user = new HashMap<>();
                user.put("fname",fname);
                user.put("lname", lname);
                user.put("Phoneno", pno);
                user.put("aadhaarno",aadhar);
                user.put("address",address);
                user.put("search",fname.toLowerCase());
                reference.updateChildren(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(Editprofile.this,"Update Successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),profile1.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Editprofile.this,"Update Unsuccessful",Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });
    }
    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image !

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }
    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(Editprofile.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Editprofile.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(Editprofile.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(Editprofile.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PReqCode);
            }

        }
        else
            openGallery();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {

            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            pickedImgUri = data.getData() ;
            uprofile.setImageURI(pickedImgUri);


        }


    }
}