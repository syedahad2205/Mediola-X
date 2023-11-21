package com.example.mediola;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context mContext;
    private List<Notify> notify;
    public NotificationAdapter(Context mContext, List<Notify> notify) {
        this.mContext=mContext;
        this.notify=notify;

    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.viewnotification, parent, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull NotificationAdapter.ViewHolder holder, int position) {

        final Notify notify1= notify.get(position);

        holder.txtmessage.setText(notify1.getMessage());
    }

    @Override
    public int getItemCount() {
        return notify.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtmessage;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            txtmessage=itemView.findViewById(R.id.txtmessage);
        }
    }
}
