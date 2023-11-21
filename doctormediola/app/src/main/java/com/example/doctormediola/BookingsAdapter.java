package com.example.doctormediola;


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

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> {

    private Context mContext;
    private List<Bookings> bookings;
    private DatabaseReference dreference,reference,dreference2;
    APIService apiService;

    public BookingsAdapter(Context mContext, List<Bookings> bookings) {

        this.mContext=mContext;
        this.bookings=bookings;

    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.viewappointment, parent, false);
        return new BookingsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull BookingsAdapter.ViewHolder holder, int position) {

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        final Bookings book = bookings.get(position);
        holder.name.setText("Name: "+book.getName());
        holder.time.setText("Time: "+book.getTime());
        holder.reason.setText("Reason: "+book.getReason());
        holder.txtconfirm.setText("Confirm: "+book.getConfirm());

        String result=book.getConfirm();

        if(result.equals("confirmed"))
        {
            holder.confirm.setEnabled(false);
            holder.cancel.setEnabled(false);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,sendprescription.class);
                    intent.putExtra("userid",book.getUserid());
                    intent.putExtra("doctorid",book.getDocid());
                    intent.putExtra("name",book.getName());
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
                    alertDialog.setMessage("Are you sure you want to confirm appointment")
                            .setCancelable(false)
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dreference = FirebaseDatabase.getInstance().getReference("Bookings").child(book.getDocid()).child(book.getDate()).child(book.getTime());

                                    HashMap<String,Object> data = new HashMap<>();
                                    data.put("confirm", "confirmed");
                                    dreference.updateChildren(data);

                                    reference = FirebaseDatabase.getInstance().getReference("Users").child(book.getDocid());
                                    reference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Doctors doc=snapshot.getValue(Doctors.class);

                                            String msg="your Appointment on "+book.getDate()+" between "+book.getTime()+" Successfull";
                                            sendNotifiaction(book.getUserid(), doc.getFname(),msg,doc.getId());
                                            String sid=book.getTime()+book.getDate();
                                            dreference2 = FirebaseDatabase.getInstance().getReference("Notification").child(book.getUserid()).child(sid);
                                            final Map<String,Object> noti = new HashMap<>();
                                            noti.put("sender",doc.getFname());
                                            noti.put("docid",doc.getId());
                                            noti.put("message",msg);
                                            noti.put("receiver",book.getUserid());

                                            dreference2.setValue(noti);




                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


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
                    builder.setMessage("Are you sure you want to cancel appointment")
                            .setCancelable(false)
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    reference=FirebaseDatabase.getInstance().getReference("Bookings").child(book.getDocid()).child(book.getDate()).child(book.getTime());
                                    reference.removeValue();
                                    reference = FirebaseDatabase.getInstance().getReference("Users").child(book.getDocid());
                                    reference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Doctors doc=snapshot.getValue(Doctors.class);

                                            String msg="your Appoint on "+book.getDate()+" between "+book.getTime()+" Cancelled";
                                            sendNotifiaction(book.getUserid(), doc.getFname(),msg,doc.getId());

                                            String sid=book.getTime()+book.getDate();
                                            dreference2 = FirebaseDatabase.getInstance().getReference("Notification").child(book.getUserid()).child(sid);
                                            final Map<String,Object> noti = new HashMap<>();
                                            noti.put("sender",doc.getFname());
                                            noti.put("docid",doc.getId());
                                            noti.put("message",msg);
                                            noti.put("receiver",book.getUserid());

                                            dreference2.setValue(noti);


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
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
        return bookings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name,time,reason,txtconfirm;
        public Button confirm,cancel;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.txtbname);
            time=itemView.findViewById(R.id.txtbtime);
            reason=itemView.findViewById(R.id.txtbreason);
            txtconfirm=itemView.findViewById(R.id.txtbconfirm);
            confirm=itemView.findViewById(R.id.btnconfirm);
            cancel=itemView.findViewById(R.id.btncancel);
        }
    }

    private void sendNotifiaction(String receiver, final String username, final String message,final String docid){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(docid, R.mipmap.ic_launcher, username+": "+message, "Booking Notification",
                            receiver);

                    Sender sender = new Sender(data, token.getToken());
                    System.out.println("<><><>"+receiver);
                    System.out.println("<><><>"+token.getToken());
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
