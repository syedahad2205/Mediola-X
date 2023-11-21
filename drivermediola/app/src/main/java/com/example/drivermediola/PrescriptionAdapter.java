package com.example.drivermediola;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drivermediola.Notifications.Client;
import com.example.drivermediola.Notifications.Data;
import com.example.drivermediola.Notifications.MyResponse;
import com.example.drivermediola.Notifications.Sender;
import com.example.drivermediola.Notifications.Token;

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

public class PrescriptionAdapter  extends RecyclerView.Adapter<PrescriptionAdapter.ViewHolder> {

    private Context mContext;
    private List<Priscription> priscriptionList;
    private DatabaseReference dreference,reference,dreference2,dreference3;
    APIService apiService;

    public PrescriptionAdapter(Context mContext, List<Priscription> priscriptionList) {
        this.mContext=mContext;
        this.priscriptionList=priscriptionList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.vieworders, parent, false);
        return new PrescriptionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PrescriptionAdapter.ViewHolder holder, int position) {

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        Priscription pri=priscriptionList.get(position);
        String result=pri.getConfirm();
        String uid=pri.getUserid();
        holder.txtadress.setText("ADDRESS: "+pri.getLocation());
        reference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                Users users=snapshot.getValue(Users.class);

                holder.name.setText("NAME: "+users.getFname()+" "+users.getLname());
                holder.txtpris.setText("PRESCRIPITON :\n"+pri.getPrestext());
                holder.txtconfirm.setText("CONFIRM:"+pri.getConfirm());
                holder.txtpno.setText("PHONE NO:"+users.getPhoneno());

            }



            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        if(result.equals("confirmed"))
        {
            holder.confirm.setEnabled(false);
            holder.cancel.setEnabled(false);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mContext, userloaction.class);
                    intent.putExtra("username",holder.name.getText().toString());
                    intent.putExtra("Pno",holder.txtpno.getText().toString());
                    intent.putExtra("address",pri.getLocation());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        }

        else
        {
            holder.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialog=new AlertDialog.Builder( v.getRootView().getContext());
                    alertDialog.setMessage("Are you sure you want to confirm prescription order")
                            .setCancelable(false)
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dreference = FirebaseDatabase.getInstance().getReference("Prescription1").child(pri.getDriverid()).child(pri.getDate()).child(pri.getTime());

                                    HashMap<String,Object> data = new HashMap<>();
                                    data.put("confirm", "confirmed");
                                    dreference.updateChildren(data);
                                    String msg="Your Prescription delivery request has been confirmed ";
                                    sendNotifiaction(pri.getUserid(),msg,pri.getDriverid());
                                    String sid=pri.getTime();
                                    dreference2 = FirebaseDatabase.getInstance().getReference("Notification").child(pri.userid).child(sid);
                                    final Map<String,Object> noti = new HashMap<>();
                                    noti.put("sender",pri.getDriverid());
                                    noti.put("message",msg);
                                    noti.put("receiver",pri.getUserid());

                                    dreference2.setValue(noti);


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

            holder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder=new AlertDialog.Builder( v.getRootView().getContext());
                    builder.setMessage("Are you sure you want to cancel prescription order")
                            .setCancelable(false)
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dreference3= FirebaseDatabase.getInstance().getReference("Prescription1").child(pri.getDriverid()).child(pri.getDate()).child(pri.getTime());
                                    dreference3.removeValue();
                                    String msg="Your Prescription delivery request has been cancelled ";
                                    sendNotifiaction(pri.getUserid(),msg,pri.getDriverid());
                                    String sid=pri.getTime();
                                    dreference2 = FirebaseDatabase.getInstance().getReference("Notification").child(pri.getUserid()).child(sid);
                                    final Map<String,Object> noti = new HashMap<>();
                                    noti.put("sender",pri.getDriverid());
                                    noti.put("message",msg);
                                    noti.put("receiver",pri.getDocid());

                                    dreference2.setValue(noti);

                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(v.getContext(),"cancled",Toast.LENGTH_SHORT).show();

                        }
                    });
                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return priscriptionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name,txtpris,txtadress,txtconfirm,txtpno;
        public Button confirm,cancel;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.txtuname);
            txtpris=itemView.findViewById(R.id.txtpriscription);
            txtadress=itemView.findViewById(R.id.txtaddress);
            txtconfirm=itemView.findViewById(R.id.txtbconfirm);
            txtpno=itemView.findViewById(R.id.txtupno);
            confirm=itemView.findViewById(R.id.btnconfirm);
            cancel=itemView.findViewById(R.id.btncancel);
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
    }
}
