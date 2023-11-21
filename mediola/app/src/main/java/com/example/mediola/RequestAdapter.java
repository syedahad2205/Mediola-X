package com.example.mediola;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private Context mContext;
    private List<Friends> mUsers;
    private FirebaseAuth fauth;
    private DatabaseReference dreference,dreference2,dreference4,reference;

    public RequestAdapter(Context mContext, List<Friends> mUsers){
        this.mUsers = mUsers;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.request_view, parent, false);
        return new RequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.ViewHolder holder, int position) {
        fauth = FirebaseAuth.getInstance();
        FirebaseUser fuser = fauth.getCurrentUser();
        final Friends user = mUsers.get(position);
        String reciver=user.getReceiver();
        String sender=user.getSender();
        String friend=user.getFriend();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(sender);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Users users=snapshot.getValue(Users.class);

                holder.username.setText(users.getFname()+" "+users.getLname());
                if (users.getImageURL().equals("default")){
                    holder.profile_image.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(mContext).load(users.getImageURL()).into(holder.profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dreference = FirebaseDatabase.getInstance().getReference("Friends").child(sender).child(reciver);
                Map<String,String> user = new HashMap<>();
                user.put("sender",sender);
                user.put("receiver",reciver);
                user.put("friend","true");
                dreference.setValue(user);

                dreference2 = FirebaseDatabase.getInstance().getReference("Friends").child(reciver).child(sender);
                Map<String,String> user1 = new HashMap<>();
                user1.put("sender",sender);
                user1.put("receiver",reciver);
                user1.put("friend","true");
                dreference2.setValue(user1);

                Task<Void> dreference3 = FirebaseDatabase.getInstance().getReference("Requests").child(reciver).child(sender).removeValue();
                Intent intent = new Intent(mContext, FriendRequest.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Task<Void> dreference3 = FirebaseDatabase.getInstance().getReference("Requests").child(reciver).child(sender).removeValue();
                Task<Void> dreference5 = FirebaseDatabase.getInstance().getReference("Friends").child(sender).child(reciver).removeValue();
                Task<Void> dreference6 = FirebaseDatabase.getInstance().getReference("Friends").child(reciver).child(sender).removeValue();
                Intent intent = new Intent(mContext, FriendRequest.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

       /* holder.addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dreference = FirebaseDatabase.getInstance().getReference("Friends").child(sender).child(reciver);
                Map<String,String> user = new HashMap<>();
                user.put("sender",sender);
                user.put("receiver",reciver);
                user.put("friend","false");
                dreference.setValue(user);

                dreference2 = FirebaseDatabase.getInstance().getReference("Friends").child(reciver).child(sender);
                Map<String,String> user1 = new HashMap<>();
                user1.put("sender",sender);
                user1.put("receiver",reciver);
                user1.put("friend","received");
                dreference2.setValue(user1);

                dreference3 = FirebaseDatabase.getInstance().getReference("Requests").child(reciver).child(sender);
                Map<String,String> user2 = new HashMap<>();
                user2.put("sender",sender);
                user2.put("receiver",reciver);
                user2.put("friend","false");
                dreference3.setValue(user2);


            }
        });*/

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_image;
        public Button accept,reject;

        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            accept = itemView.findViewById(R.id.btnaccept);
            reject = itemView.findViewById(R.id.btnreject);

        }
    }

}
