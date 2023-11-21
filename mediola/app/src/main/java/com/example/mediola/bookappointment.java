package com.example.mediola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.whygraphics.multilineradiogroup.MultiLineRadioGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class bookappointment extends AppCompatActivity {


    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private CardView card9,card930,card10,card11,card1130,card12,card2,card230,card3,card330,card4,card430;
    private LinearLayout linear9,linear930,linear10,linear11,linear1130,linear12,linear2,linear230,linear3,linear330,linear4,linear430;
    private TextView txt9,txt930,txt10,txt11,txt1130,txt12,txt2,txt230,txt3,txt330,txt4,txt430,txtname,txtdis;
    private ImageView docimg;
    private EditText etreason;
    DatabaseReference reference,reference2;
    FirebaseUser fuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookappointment);

        card9=findViewById(R.id.card9);
        card930=findViewById(R.id.card930);
        card10=findViewById(R.id.card10);
        card11=findViewById(R.id.card11);
        card1130=findViewById(R.id.card1130);
        card12=findViewById(R.id.card12);
        card2=findViewById(R.id.card2);
        card230=findViewById(R.id.card230);
        card3=findViewById(R.id.card3);
        card330=findViewById(R.id.card330);
        card4=findViewById(R.id.card4);
        card430=findViewById(R.id.card430);

        linear9=findViewById(R.id.liner9);
        linear930=findViewById(R.id.liner930);
        linear10=findViewById(R.id.liner10);
        linear11=findViewById(R.id.liner11);
        linear1130=findViewById(R.id.liner1130);
        linear12=findViewById(R.id.liner12);
        linear2=findViewById(R.id.liner2);
        linear230=findViewById(R.id.liner230);
        linear3=findViewById(R.id.liner3);
        linear330=findViewById(R.id.liner330);
        linear4=findViewById(R.id.liner4);
        linear430=findViewById(R.id.liner430);

        txt9=findViewById(R.id.txt9);
        txt930=findViewById(R.id.txt930);
        txt10=findViewById(R.id.txt10);
        txt11=findViewById(R.id.txt11);
        txt1130=findViewById(R.id.txt1130);
        txt12=findViewById(R.id.txt12);
        txt2=findViewById(R.id.txt2);
        txt230=findViewById(R.id.txt230);
        txt3=findViewById(R.id.txt3);
        txt330=findViewById(R.id.txt330);
        txt4=findViewById(R.id.txt4);
        txt430=findViewById(R.id.txt430);

        txtname=findViewById(R.id.bdocname);
        txtdis=findViewById(R.id.bdocdiscription);

        docimg=findViewById(R.id.dimg);
        etreason=findViewById(R.id.etreason);


        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        final String id= getIntent().getStringExtra("id");

        reference = FirebaseDatabase.getInstance().getReference("Users").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Doctors doc=snapshot.getValue(Doctors.class);

                txtname.setText("NAME: "+doc.getFname()+" "+doc.getLname());
                txtdis.setText("DISCRIPTION: "+doc.getDiscription());
                if (doc.getImageURL().equals("default")){
                    docimg.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(getApplicationContext()).load(doc.getImageURL()).into(docimg);
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        card9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String btime=txt9.getText().toString();
                final String reason=etreason.getText().toString();

                if(reason.isEmpty())
                {
                    etreason.setError("Enter the reason");
                    return;
                }

                AlertDialog.Builder builder=new AlertDialog.Builder( v.getContext());
                builder.setMessage("Are you sure you want book appointment")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                uploaddata(btime,reason);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"cancled",Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

                etreason.getText().clear();


            }
        });

        card930.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String btime=txt930.getText().toString();
                final String reason=etreason.getText().toString();

                if(reason.isEmpty())
                {
                    etreason.setError("Enter the reason");
                    return;
                }

                AlertDialog.Builder builder=new AlertDialog.Builder( v.getContext());
                builder.setMessage("Are you sure you want book appointment")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                uploaddata(btime,reason);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"cancled",Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

                etreason.getText().clear();

            }
        });
        card10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String btime=txt10.getText().toString();
                final String reason=etreason.getText().toString();

                if(reason.isEmpty())
                {
                    etreason.setError("Enter the reason");
                    return;
                }

                AlertDialog.Builder builder=new AlertDialog.Builder( v.getContext());
                builder.setMessage("Are you sure you want book appointment")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                uploaddata(btime,reason);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"cancled",Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                etreason.getText().clear();
            }
        });
        card11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String btime=txt11.getText().toString();
                final String reason=etreason.getText().toString();

                if(reason.isEmpty())
                {
                    etreason.setError("Enter the reason");
                    return;
                }

                AlertDialog.Builder builder=new AlertDialog.Builder( v.getContext());
                builder.setMessage("Are you sure you want book appointment")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                uploaddata(btime,reason);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"cancled",Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                etreason.getText().clear();

            }
        });

        card1130.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String btime=txt1130.getText().toString();
                final String reason=etreason.getText().toString();

                if(reason.isEmpty())
                {
                    etreason.setError("Enter the reason");
                    return;
                }

                AlertDialog.Builder builder=new AlertDialog.Builder( v.getContext());
                builder.setMessage("Are you sure you want book appointment")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                uploaddata(btime,reason);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"cancled",Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                etreason.getText().clear();

            }
        });
        card12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String btime=txt12.getText().toString();
                final String reason=etreason.getText().toString();

                if(reason.isEmpty())
                {
                    etreason.setError("Enter the reason");
                    return;
                }

                AlertDialog.Builder builder=new AlertDialog.Builder( v.getContext());
                builder.setMessage("Are you sure you want book appointment")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                uploaddata(btime,reason);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"cancled",Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                etreason.getText().clear();

            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String btime=txt2.getText().toString();
                final String reason=etreason.getText().toString();

                if(reason.isEmpty())
                {
                    etreason.setError("Enter the reason");
                    return;
                }

                AlertDialog.Builder builder=new AlertDialog.Builder( v.getContext());
                builder.setMessage("Are you sure you want book appointment")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                uploaddata(btime,reason);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"cancled",Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                etreason.getText().clear();

            }
        });

        card230.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String btime=txt230.getText().toString();
                final String reason=etreason.getText().toString();

                if(reason.isEmpty())
                {
                    etreason.setError("Enter the reason");
                    return;
                }

                AlertDialog.Builder builder=new AlertDialog.Builder( v.getContext());
                builder.setMessage("Are you sure you want book appointment")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                uploaddata(btime,reason);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"cancled",Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                etreason.getText().clear();

            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String btime=txt3.getText().toString();
                final String reason=etreason.getText().toString();

                if(reason.isEmpty())
                {
                    etreason.setError("Enter the reason");
                    return;
                }

                AlertDialog.Builder builder=new AlertDialog.Builder( v.getContext());
                builder.setMessage("Are you sure you want book appointment")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                uploaddata(btime,reason);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"cancled",Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                etreason.getText().clear();

            }
        });

        card330.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String btime=txt330.getText().toString();
                final String reason=etreason.getText().toString();

                if(reason.isEmpty())
                {
                    etreason.setError("Enter the reason");
                    return;
                }

                AlertDialog.Builder builder=new AlertDialog.Builder( v.getContext());
                builder.setMessage("Are you sure you want book appointment")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                uploaddata(btime,reason);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"cancled",Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                etreason.getText().clear();

            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String btime=txt4.getText().toString();
                final String reason=etreason.getText().toString();

                if(reason.isEmpty())
                {
                    etreason.setError("Enter the reason");
                    return;
                }
                AlertDialog.Builder builder=new AlertDialog.Builder( v.getContext());
                builder.setMessage("Are you sure you want book appointment")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                uploaddata(btime,reason);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"cancled",Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                etreason.getText().clear();

            }
        });

        card430.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String btime=txt430.getText().toString();
                final String reason=etreason.getText().toString();

                if(reason.isEmpty())
                {
                    etreason.setError("Enter the reason");
                    return;
                }

                AlertDialog.Builder builder=new AlertDialog.Builder( v.getContext());
                builder.setMessage("Are you sure you want book appointment")
                        .setCancelable(false)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                uploaddata(btime,reason);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(),"cancled",Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                etreason.getText().clear();

            }
        });




    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                final String date2=dateButton.getText().toString();
                final String id2= getIntent().getStringExtra("id");


                String[] bookingtime={"9:00-9:30","9:30-10:00","10:00-10:30","11:00-11:30","11:30-12:00",
                        "12:00-12:30","2:00-2:30","2:30-3:00","3:00-3:30","3:30-4:00","4:00-4:30","4:30-5:00"};

                int i;
                for( i=0;i<bookingtime.length;i++) {
                    reference = FirebaseDatabase.getInstance().getReference("Bookings").child(id2).child(date2).child(bookingtime[i]);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            Bookings booking = snapshot.getValue(Bookings.class);
                            if (booking != null) {
                                final String btime = booking.getTime();

                                System.out.println("<><><><>" + btime);
                                final String b9 = txt9.getText().toString();
                                final String b930 = txt930.getText().toString();
                                final String b10 = txt10.getText().toString();
                                final String b11 = txt11.getText().toString();
                                final String b1130 = txt1130.getText().toString();
                                final String b12 = txt12.getText().toString();
                                final String b2 = txt2.getText().toString();
                                final String b230 = txt230.getText().toString();
                                final String b3 = txt3.getText().toString();
                                final String b330 = txt330.getText().toString();
                                final String b4 = txt4.getText().toString();
                                final String b430 = txt430.getText().toString();

                                if (b9.equals(btime)) {
                                    card9.setEnabled(false);
                                    linear9.setBackgroundResource(R.drawable.bluebg);
                                }
                                if (b930.equals(btime)) {
                                    card930.setEnabled(false);
                                    linear930.setBackgroundResource(R.drawable.bluebg);
                                }

                                if (b10.equals(btime)) {
                                    card10.setEnabled(false);
                                    linear10.setBackgroundResource(R.drawable.bluebg);
                                }

                                if (b11.equals(btime)) {
                                    card11.setEnabled(false);
                                    linear11.setBackgroundResource(R.drawable.bluebg);
                                }

                                if (b1130.equals(btime)) {
                                    card1130.setEnabled(false);
                                    linear1130.setBackgroundResource(R.drawable.bluebg);
                                }

                                if (b12.equals(btime)) {
                                    card12.setEnabled(false);
                                    linear12.setBackgroundResource(R.drawable.bluebg);
                                }

                                if (b2.equals(btime)) {
                                    card2.setEnabled(false);
                                    linear2.setBackgroundResource(R.drawable.bluebg);
                                }

                                if (b230.equals(btime)) {
                                    card230.setEnabled(false);
                                    linear230.setBackgroundResource(R.drawable.bluebg);
                                }

                                if (b3.equals(btime)) {
                                    card3.setEnabled(false);
                                    linear3.setBackgroundResource(R.drawable.bluebg);
                                }

                                if (b330.equals(btime)) {
                                    card330.setEnabled(false);
                                    linear330.setBackgroundResource(R.drawable.bluebg);
                                }

                                if (b4.equals(btime)) {
                                    card4.setEnabled(false);
                                    linear4.setBackgroundResource(R.drawable.bluebg);
                                }

                                if (b430.equals(btime)) {
                                    card430.setEnabled(false);
                                    linear430.setBackgroundResource(R.drawable.bluebg);
                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    card9.setEnabled(true);
                    linear9.setBackgroundResource(R.drawable.blackbg);
                    card930.setEnabled(true);
                    linear930.setBackgroundResource(R.drawable.blackbg);
                    card10.setEnabled(true);
                    linear10.setBackgroundResource(R.drawable.blackbg);
                    card11.setEnabled(true);
                    linear11.setBackgroundResource(R.drawable.blackbg);
                    card1130.setEnabled(true);
                    linear1130.setBackgroundResource(R.drawable.blackbg);
                    card12.setEnabled(true);
                    linear12.setBackgroundResource(R.drawable.blackbg);
                    card2.setEnabled(true);
                    linear2.setBackgroundResource(R.drawable.blackbg);
                    card230.setEnabled(true);
                    linear230.setBackgroundResource(R.drawable.blackbg);
                    card3.setEnabled(true);
                    linear3.setBackgroundResource(R.drawable.blackbg);
                    card330.setEnabled(true);
                    linear330.setBackgroundResource(R.drawable.blackbg);
                    card4.setEnabled(true);
                    linear4.setBackgroundResource(R.drawable.blackbg);
                    card430.setEnabled(true);
                    linear430.setBackgroundResource(R.drawable.blackbg);

                }

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }


    private void uploaddata(String booktime,String reason)
    {

        final String id1= getIntent().getStringExtra("id");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        final String userid=fuser.getUid();
        final String date1=dateButton.getText().toString();
        reference2 = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Users users=snapshot.getValue(Users.class);

                final  String name=users.getFname()+" "+users.getLname();
                final  String imglink=users.getImageURL();

                reference = FirebaseDatabase.getInstance().getReference("Bookings").child(id1).child(date1).child(booktime);
                Map<String,String> user = new HashMap<>();
                user.put("userid",userid);
                user.put("date",date1);
                user.put("time",booktime);
                user.put("docid",id1);
                user.put("reason",reason);
                user.put("name",name);
                user.put("imglink",imglink);
                user.put("confirm","pending");


                reference.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(bookappointment.this, "Appointment Booked Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}