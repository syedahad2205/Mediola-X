package com.example.mediola;

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
import com.example.mediola.Notifications.Client;
import com.example.mediola.Notifications.Data;
import com.example.mediola.Notifications.MyResponse;
import com.example.mediola.Notifications.Sender;
import com.example.mediola.Notifications.Token;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Driveradapter1  extends RecyclerView.Adapter<Driveradapter1.ViewHolder> {
    private Context mContext;
    private List<Prescription1> prescription1List;
    private DatabaseReference dreference,reference,dreference2;
    APIService apiService;

    public Driveradapter1(Context mContext, List<Prescription1> prescription1List){
        this.prescription1List = prescription1List;
        this.mContext = mContext;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.driverview1, parent, false);
        return new Driveradapter1.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Driveradapter1.ViewHolder holder, int position) {
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        final Prescription1 prec=prescription1List.get(position);
        holder.drivername.setText(prec.getFname()+" "+prec.getLname());
        /*holder.location.setText(prec.getLocation());
        if (prec.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(prec.getImageURL()).into(holder.profile_image);
        }*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String id=prec.getId();
                String userid = prec.getUserid();
                String docid = prec.getDocid();
                String name = prec.getName();
                String prestxt=prec.getPrestxt();
                String pno=prec.getPno();
                String date=prec.getDate();
                String time=prec.getTime();

                AlertDialog.Builder alertDialog=new AlertDialog.Builder( v.getRootView().getContext());
                alertDialog.setMessage("Are you sure you want to Send Prescription")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dreference2 = FirebaseDatabase.getInstance().getReference("Prescription1").child(prec.getId()).child(date).child(time);
                                final Map<String,Object> pre = new HashMap<>();
                                pre.put("driverid",prec.getId());
                                pre.put("docid",prec.getDocid());
                                pre.put("userid",prec.getUserid());
                                pre.put("username",prec.getName());
                                pre.put("prestext",prec.getPrestxt());
                                pre.put("location",prec.getLocation());
                                pre.put("time",time);
                                pre.put("date",date);
                                pre.put("pno",prec.getPno());
                                pre.put("confirm","pending");
                                dreference2.setValue(pre);

                                String msg="The Prescription of "+prec.getName()+" has been received on "+prec.getDate();
                                sendNotifiaction(prec.getId(),msg,prec.getUserid());
                                dreference = FirebaseDatabase.getInstance().getReference("Notification").child(prec.getId()).child(date+time);
                                final Map<String,Object> noti = new HashMap<>();
                                noti.put("sender",prec.getUserid());
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
        return prescription1List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView drivername,location;
        public ImageView profile_image;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            drivername = itemView.findViewById(R.id.adname1);
            profile_image = itemView.findViewById(R.id.adprofile1);
            location=itemView.findViewById(R.id.adlocation1);
        }
    }

    private void sendNotifiaction(String receiver, final String message,final String userid){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        System.out.println("<><><>"+receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(userid, R.mipmap.ic_launcher, message, "Prescription Notification",
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
        Intent intent = new Intent(mContext,myprescription.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
