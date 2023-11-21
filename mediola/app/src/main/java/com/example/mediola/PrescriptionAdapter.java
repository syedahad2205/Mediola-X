package com.example.mediola;

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

import com.example.mediola.Notifications.Client;
import com.example.mediola.Notifications.Data;
import com.example.mediola.Notifications.MyResponse;
import com.example.mediola.Notifications.Sender;
import com.example.mediola.Notifications.Token;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.ViewHolder> {

    private Context mContext;
    private List<Priscription> priscriptionList;
    private DatabaseReference dreference,reference,dreference2;
    APIService apiService;

    public PrescriptionAdapter(Context mContext, List<Priscription> priscriptionList) {
        this.mContext=mContext;
        this.priscriptionList=priscriptionList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.viewmyprescription, parent, false);
        return new PrescriptionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PrescriptionAdapter.ViewHolder holder, int position) {

        Priscription pri=priscriptionList.get(position);
        String docid=pri.getDocid();

        dreference = FirebaseDatabase.getInstance().getReference("Users").child(docid);
        dreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                Doctors doctors=snapshot.getValue(Doctors.class);

                holder.txtdocname.setText("Doctor Name:"+doctors.getFname()+" "+doctors.getLname());


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        String uid=pri.getUserid();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                Users users=snapshot.getValue(Users.class);

                holder.name.setText("NAME: "+users.getFname()+" "+users.getLname());
                holder.txtpris.setText("PRESCRIPITON :\n"+pri.getPrestext());
                holder.txtpno.setText("PHONE NO:"+users.getPhoneno());

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,sendprescription.class);
                intent.putExtra("userid",pri.getUserid());
                intent.putExtra("doctorid",pri.getDocid());
                intent.putExtra("name",pri.getUsername());
                intent.putExtra("pretxt",pri.getPrestext());
                intent.putExtra("pno",holder.txtpno.getText().toString());

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return priscriptionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name, txtpris, txtpno,txtdocname;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txtuname);
            txtpris = itemView.findViewById(R.id.txtpriscription);
            txtpno = itemView.findViewById(R.id.txtupno);
            txtdocname=itemView.findViewById(R.id.txtdocname);
        }

    }

}
