package com.example.doctormediola;

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

import java.util.HashMap;

public class Chatimg1 extends AppCompatActivity {

    private ImageView image;
    private Button send;
    private static final int PReqCode = 2 ;
    private static final int REQUESCODE = 2 ;
    private Uri pickedImgUri = null;
    private FirebaseAuth fauth;
    Intent intent;
    String userid;
    FirebaseUser fuser;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatimg1);
        progressDialog = new ProgressDialog(this);
        image=findViewById(R.id.bimg);
        send=findViewById(R.id.bsend);
        fauth = FirebaseAuth.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        intent = getIntent();
        userid = intent.getStringExtra("userid");
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestForPermission();
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pickedImgUri ==null)
                {
                    Toast.makeText(Chatimg1.this,"Select Image",Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("This can take a few seconds");
                progressDialog.show();
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("ChatImage");
                final StorageReference imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());
                imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imagelink = uri.toString();


                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("sender", fuser.getUid());
                                hashMap.put("receiver",userid);
                                hashMap.put("message"," ");
                                hashMap.put("imagelink",imagelink);
                                hashMap.put("isseen", false);

                                reference.child("Chats1").push().setValue(hashMap);

                                final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist1")
                                        .child(fuser.getUid())
                                        .child(userid);

                                chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (!dataSnapshot.exists()){
                                            chatRef.child("id").setValue(userid);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                final DatabaseReference chatRefReceiver = FirebaseDatabase.getInstance().getReference("Chatlist1")
                                        .child(userid)
                                        .child(fuser.getUid());
                                chatRefReceiver.child("id").setValue(fuser.getUid());

                                progressDialog.dismiss();
                                Intent intent = new Intent(Chatimg1.this,MessageActivity1.class);
                                intent.putExtra("userid", userid);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();


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


        if (ContextCompat.checkSelfPermission(Chatimg1.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Chatimg1.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(Chatimg1.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(Chatimg1.this,
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