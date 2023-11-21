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
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class Doctorcategory extends RecyclerView.Adapter<Doctorcategory.ViewHolder>{

    private Context mContext;
    private List<Doctors> doctors;
    FirebaseUser fuser;

    public Doctorcategory(Context mContext,  List<Doctors> doctors){
        this.doctors = doctors;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.doctorcategoryview, parent, false);
        return new Doctorcategory.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Doctors doc=doctors.get(position);
        holder.docname.setText(doc.getFname()+" "+doc.getLname());
        holder.special.setText(doc.getCategory());
        if (doc.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(doc.getImageURL()).into(holder.profile_image);
        }



    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView docname,special;
        public ImageView profile_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            docname = itemView.findViewById(R.id.docname);
            profile_image = itemView.findViewById(R.id.dprofile);
            special=itemView.findViewById(R.id.specialization);
        }
    }
}
