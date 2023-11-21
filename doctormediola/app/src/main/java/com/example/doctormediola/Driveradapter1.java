package com.example.doctormediola;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Driveradapter1 extends RecyclerView.Adapter<Driveradapter1.ViewHolder> {

    private Context mContext;
    private List<Drivers> drivers;

    public Driveradapter1(Context mContext,  List<Drivers> drivers){
        this.drivers = drivers;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public Driveradapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.driverview1, parent, false);
        return new Driveradapter1.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Driveradapter1.ViewHolder holder, int position) {

        final Drivers driver=drivers.get(position);
        holder.drivername.setText(driver.getFname()+" "+driver.getLname());
        holder.location.setText(driver.getLocation());
        if (driver.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(driver.getImageURL()).into(holder.profile_image);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+driver.getPhoneno()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return drivers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView drivername,location;
        public ImageView profile_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            drivername = itemView.findViewById(R.id.adname1);
            profile_image = itemView.findViewById(R.id.adprofile1);
            location=itemView.findViewById(R.id.adlocation1);
        }
    }
}
