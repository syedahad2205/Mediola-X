package com.example.doctormediola;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctormediola.Notifications.Client;
import com.example.doctormediola.Notifications.Data;
import com.example.doctormediola.Notifications.MyResponse;
import com.example.doctormediola.Notifications.Sender;
import com.example.doctormediola.Notifications.Token;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Driveradapter extends RecyclerView.Adapter<Driveradapter.ViewHolder> {

    private Context mContext;
    private List<Prescription> prescriptionList;
    private DatabaseReference dreference,reference,dreference2;
    APIService apiService;
    public Driveradapter(Context mContext, List<Prescription> prescriptionList){
        this.prescriptionList = prescriptionList;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public Driveradapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.driverview, parent, false);
        return new Driveradapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Driveradapter.ViewHolder holder, int position) {
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        final Prescription prec=prescriptionList.get(position);
        holder.drivername.setText(prec.getFname()+" "+prec.getLname());
        holder.location.setText(prec.getLocation());
        if (prec.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(prec.getImageURL()).into(holder.profile_image);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String id=prec.getId();
                String userid = prec.getUserid();
                String docid = prec.getDocid();
                String name = prec.getUsername();
                String prestxt=prec.getPrestxt();
                String date=prec.getDate();
                String time=prec.getTime();

                AlertDialog.Builder alertDialog=new AlertDialog.Builder( v.getRootView().getContext());
                alertDialog.setMessage("Are you sure you want to Send Prescription")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dreference2 = FirebaseDatabase.getInstance().getReference("Prescription").child(prec.getId()).child(date).child(time);
                                final Map<String,Object> pre = new HashMap<>();
                                pre.put("driverid",prec.getId());
                                pre.put("docid",prec.getDocid());
                                pre.put("userid",prec.getUserid());
                                pre.put("username",prec.getUsername());
                                pre.put("prestext",prec.getPrestxt());
                                pre.put("time",time);
                                pre.put("date",date);
                                pre.put("confirm","false");
                                dreference2.setValue(pre);

                                String msg="The Prescription of "+prec.getUsername()+" has been received on "+prec.getDate();
                                sendNotifiaction(prec.getId(),msg,prec.getDocid());
                                dreference = FirebaseDatabase.getInstance().getReference("Notification").child(prec.getId()).child(date+time);
                                final Map<String,Object> noti = new HashMap<>();
                                noti.put("sender",prec.getDocid());
                                noti.put("message",msg);
                                noti.put("receiver",prec.getId());

                                dreference.setValue(noti);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"cancled",Toast.LENGTH_SHORT).show();

                    }
                });
                alertDialog.show();



            }
        });

    }

    @Override
    public int getItemCount() {
        return prescriptionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView drivername,location;
        public ImageView profile_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            drivername = itemView.findViewById(R.id.adname);
            profile_image = itemView.findViewById(R.id.adprofile);
            location=itemView.findViewById(R.id.adlocation);
        }
    }

    private void sendNotifiaction(String receiver, final String message,final String docid){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        System.out.println("<><><>"+receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(docid, R.mipmap.ic_launcher, message, "Prescription Notification",
                            receiver);

                    Sender sender = new Sender(data, token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code() == 200){
                                        if (response.body().success != 1){
                                            Toast.makeText(mContext.getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(mContext.getApplicationContext(), "Prescription Send Successfully",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(mContext,myappointment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}

