package com.example.mediola;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> postList1;
    private Context mContext;
    public FirebaseUser fuser;
    public DatabaseReference dreference,dreference1,dreference2;
    public String like;


    public PostAdapter(Context mContext, List<Post> postList1){
        this.postList1 = postList1;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.postdisplay, parent, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        String imgapp="https://firebasestorage.googleapis.com/v0/b/mediola-116c1.appspot.com/o/approve.png?alt=media&token=13120b4c-f0f0-4163-8844-7ca79d209c33";

        final Post users=postList1.get(position);
        if(users.getApproved().equals("true"))
            Glide.with(mContext).load(imgapp).override(40, 40).into(holder.approveimg);

        holder.title.setText(users.getTitle());
        holder.dis.setText(users.getDis());
        holder.username.setText(users.getName());
        holder.totallike.setText(users.getTotallike());
        if (users.getUserimg().equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(users.getUserimg()).into(holder.profile_image);
        }
        Glide.with(mContext).load(users.getImagelink()).into(holder.postimg);
        holder.commentimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, addcomment.class);
                intent.putExtra("uname",users.getName());
                intent.putExtra("userimg",users.getUserimg());
                intent.putExtra("postimg",users.getImagelink());
                intent.putExtra("postid",users.getPostid());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });

        holder.likeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dreference = FirebaseDatabase.getInstance().getReference("Likes").child(users.getPostid()).child(fuser.getUid());
                dreference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Likes likess=snapshot.getValue(Likes.class);

                        if(likess != null)
                        {
                            like=likess.getLiked();
                        }

                        else
                        {
                            like="false";
                        }

                        System.out.println("<><><>"+like);

                        if(like.equals("true"))
                        {

                        }
                        else
                        {
                            dreference = FirebaseDatabase.getInstance().getReference("Likes").child(users.getPostid()).child(fuser.getUid());
                            final Map<String,Object> like1 = new HashMap<>();
                            like1.put("liked","true");
                            like1.put("postid",users.getPostid());
                            dreference.setValue(like1);

                            dreference1 = FirebaseDatabase.getInstance().getReference("Posts").child(users.getPostid());
                            dreference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Post post=snapshot.getValue(Post.class);
                                    final String totallike1=post.getTotallike();
                                    System.out.println("<><><>t"+totallike1);
                                    int  like2=Integer.parseInt(totallike1)+1;
                                    String like3 = String.valueOf(like2);
                                    dreference2 = FirebaseDatabase.getInstance().getReference("Posts").child(users.getPostid());
                                    dreference2.child("totallike").setValue(like3);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return postList1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username,totallike,title,dis;
        public ImageView profile_image,postimg,commentimg,likeimg,approveimg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.pusername);
            totallike = itemView.findViewById(R.id.totallike);
            profile_image = itemView.findViewById(R.id.puserprofileimg);
            postimg = itemView.findViewById(R.id.postimg);
            commentimg = itemView.findViewById(R.id.commentimg);
            likeimg = itemView.findViewById(R.id.imglike);
            title = itemView.findViewById(R.id.title1);
            dis  = itemView.findViewById(R.id.discription);
            approveimg  = itemView.findViewById(R.id.approveimg);

        }
    }
}
