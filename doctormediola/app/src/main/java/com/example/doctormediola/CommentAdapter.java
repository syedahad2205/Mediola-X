package com.example.doctormediola;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>  {

    private List<Comments> commentsList;
    private Context mContext;

    public CommentAdapter(Context mContext, List<Comments> commentsList){
        this.commentsList = commentsList;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.viewcomment, parent, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        final Comments com=commentsList.get(position);
        holder.uname.setText(com.getName());
        if (com.getUserimg().equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(com.getUserimg()).into(holder.profile_image);
        }

        holder.comment.setText(com.getComment());


    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView uname,comment;
        public ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            uname=itemView.findViewById(R.id.comuname);
            profile_image=itemView.findViewById(R.id.comprofile_image);
            comment=itemView.findViewById(R.id.comcomment);

        }
    }
}
