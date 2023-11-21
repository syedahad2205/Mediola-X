package com.example.drivermediola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Addpost extends AppCompatActivity {
    private ImageView image;
    private EditText Title,Dis;
    private Button send;
    private static final int PReqCode = 2 ;
    private static final int REQUESCODE = 2 ;
    private Uri pickedImgUri = null;
    private FirebaseAuth fauth;
    private DatabaseReference dreference,reference;
    FirebaseUser fuser;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);

        image=findViewById(R.id.bimg);
        send=findViewById(R.id.bsend);
        Title=findViewById(R.id.txttitle);
        Dis=findViewById(R.id.txtdis);
        fauth = FirebaseAuth.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog = new ProgressDialog(this);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat tf= new SimpleDateFormat("HH:mm:ss");
        final String formattedDate = df.format(date);
        final String  time=tf.format(date);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestForPermission();
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pickedImgUri ==null)
                {
                    Toast.makeText(Addpost.this,"Select Image",Toast.LENGTH_SHORT).show();
                    return;
                }

              final String title=Title.getText().toString();
                final String dis=Dis.getText().toString();


                if(title.isEmpty() || dis.isEmpty())
                {
                    Toast.makeText(Addpost.this,"Login In Failed",Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("This can take a few seconds");
                progressDialog.show();
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Posts");
                final StorageReference imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());
                imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imagelink = uri.toString();
                                reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        Users users=snapshot.getValue(Users.class);
                                        final String name=users.getFname();
                                        final String userimg=users.getImageURL();
                                        System.out.println("<><><>"+name);
                                        dreference = FirebaseDatabase.getInstance().getReference("Posts").push();
                                        final Map<String,Object> blog = new HashMap<>();
                                        blog.put("imagelink",imagelink);
                                        blog.put("date",formattedDate);
                                        blog.put("title",title);
                                        blog.put("dis",dis);
                                        blog.put("time",time);
                                        blog.put("name",name);
                                        blog.put("userimg",userimg);
                                        blog.put("totallike","0");
                                        blog.put("postid","");
                                        blog.put("approved","false");
                                        dreference.setValue(blog).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                String id= dreference.getKey();
                                                progressDialog.dismiss();
                                                dreference.child("postid").setValue(id);
                                                Toast.makeText(Addpost.this, "Post Added Successful", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(Addpost.this,Socialmedia.class));
                                                finish();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                            }
                                        });


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        progressDialog.dismiss();
                                    }
                                });





                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // something goes wrong uploading picture

                                progressDialog.dismiss();

                            }
                        });


                    }
                });



            }
        });






    }
    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(Addpost.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Addpost.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(Addpost.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(Addpost.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else
            // everything goes well : we have permission to access user gallery
            openGallery();

    }

    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image !

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {

            // the user has successfully picked an image
            // we need to save its reference to a Uri variable
            pickedImgUri = data.getData() ;
            image.setImageURI(pickedImgUri);

        }


    }
}