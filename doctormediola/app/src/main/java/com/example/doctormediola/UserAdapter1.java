package com.example.doctormediola;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

public class UserAdapter1 extends RecyclerView.Adapter<UserAdapter1.ViewHolder> {

    private Context mContext;
    private List<Users> mUsers;
    private FirebaseAuth fauth;
    private DatabaseReference dreference,dreference2,dreference3,dreference4;

    public UserAdapter1(Context mContext, List<Users> mUsers){
        this.mUsers = mUsers;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public UserAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item1, parent, false);
        return new UserAdapter1.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter1.ViewHolder holder, int position) {
        fauth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fauth.getCurrentUser();
        String sender = firebaseUser.getUid();
        final Users user = mUsers.get(position);
        String reciver=user.getId();

        holder.username.setText(user.getFname()+" "+user.getLname());
        if (user.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(user.getImageURL()).into(holder.profile_image);
        }
        dreference4 = FirebaseDatabase.getInstance().getReference("Friends").child(sender).child(reciver);
        dreference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Friends friends=snapshot.getValue(Friends.class);

                if (friends!= null) {
                    if (friends.getFriend().equals("true") ) {
                        holder.addfriend.setText("FRIENDS");
                        holder.addfriend.setEnabled(false);
                    }
                    else if (friends.getFriend().equals("false")) {
                        holder.addfriend.setText("REQUEST SENT");
                        holder.addfriend.setEnabled(false);
                    }
                    else if (friends.getFriend().equals("received")) {
                        holder.addfriend.setText("REQUEST RECEIVED");
                        holder.addfriend.setEnabled(false);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        holder.addfriend.setOnClickListener(new View.OnClickListener() {
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
        });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_image;
        public Button addfriend;

        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            addfriend = itemView.findViewById(R.id.btnaddfriend);
        }
    }


}