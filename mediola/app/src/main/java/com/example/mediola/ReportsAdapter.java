package com.example.mediola;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ViewHolder> {
    private Context mContext;
    private List<Upload> uploadList;

    public ReportsAdapter(Context mContext, List<Upload> uploadList) {
        this.mContext=mContext;
        this.uploadList=uploadList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.reportview, parent, false);
        return new ReportsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Upload upload=uploadList.get(position);
        holder.txtreport.setText(upload.getFilename());
        holder.txtreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(upload.getPdfurl());
                Intent intent= new Intent(Intent.ACTION_VIEW,uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtreport;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtreport = itemView.findViewById(R.id.txtreport);
        }
    }
}
