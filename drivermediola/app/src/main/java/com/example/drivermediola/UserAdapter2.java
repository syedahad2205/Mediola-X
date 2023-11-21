package com.example.drivermediola;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

public class UserAdapter2 extends RecyclerView.Adapter<UserAdapter2.ViewHolder> {

    private Context mContext;
    private List<Friends> mUsers;
    private boolean ischat;
    FirebaseUser fuser;
    String theLastMessage;

    public UserAdapter2(Context mContext, List<Friends> mUsers, boolean ischat){
        this.mUsers = mUsers;
        this.mContext = mContext;
        this.ischat = ischat;
    }

    @NonNull
    @Override
    public UserAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item2, parent, false);
        return new UserAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter2.ViewHolder holder, int position) {


        final Friends user = mUsers.get(position);
        String reciver=user.getReceiver();
        String sender=user.getSender();
        String friend=user.getFriend();

        String result =" ";
        fuser = FirebaseAuth.getInstance().getCurrentUser();



        if(fuser.getUid().equals(sender))
        {
            result=reciver;
        }
        else if (fuser.getUid().equals(reciver))
        {
            result=sender;
        }

        System.out.println("<><><>"+result);

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Users").child(result);
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Users users=snapshot.getValue(Users.class);
                holder.username.setText(users.getFname());
                if (users.getImageURL().equals("default")) {
                    holder.profile_image.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(mContext).load(users.getImageURL()).into(holder.profile_image);
                }

                if (ischat) {
                    lastMessage(users.getId(), holder.last_msg);
                } else {
                    holder.last_msg.setVisibility(View.GONE);
                }

                if (ischat) {
                    if (users.getStatus().equals("online")) {
                        holder.img_on.setVisibility(View.VISIBLE);
                        holder.img_off.setVisibility(View.GONE);
                    } else {
                        holder.img_on.setVisibility(View.GONE);
                        holder.img_off.setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.img_on.setVisibility(View.GONE);
                    holder.img_off.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result1=" ";
                if(fuser.getUid().equals(sender))
                {
                    result1=reciver;
                }
                else if (fuser.getUid().equals(reciver))
                {
                    result1=sender;
                }
                Intent intent = new Intent(mContext, MessageActivity1.class);
                intent.putExtra("userid",result1);
                mContext.startActivity(intent);
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
        private ImageView img_on;
        private ImageView img_off;
        private TextView last_msg;

        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
            last_msg = itemView.findViewById(R.id.last_msg);
        }
    }

    //check for last message
    private void lastMessage(final String userid, final TextView last_msg){
        theLastMessage = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (firebaseUser != null && chat != null) {
                        if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                                chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())) {
                            theLastMessage = chat.getMessage();
                        }
                    }
                }

                switch (theLastMessage){
                    case  "default":
                        last_msg.setText("No Message");
                        break;

                    default:
                        last_msg.setText(theLastMessage);
                        break;
                }

                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
