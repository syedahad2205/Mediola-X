package com.example.drivermediola;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PrescriptionAdapter1 extends RecyclerView.Adapter<PrescriptionAdapter1.ViewHolder> {

    private Context mContext;
    private List<Priscription1> priscriptionList;
    private DatabaseReference dreference,reference,dreference2;
    APIService apiService;

    public PrescriptionAdapter1(Context mContext, List<Priscription1> priscriptionList) {
        this.mContext=mContext;
        this.priscriptionList=priscriptionList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.viewmyprescription, parent, false);
        return new PrescriptionAdapter1.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PrescriptionAdapter1.ViewHolder holder, int position) {

        Priscription1 pri=priscriptionList.get(position);
        String docid=pri.getDocid();

        System.out.println("<><><>"+pri.getDocid());

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
