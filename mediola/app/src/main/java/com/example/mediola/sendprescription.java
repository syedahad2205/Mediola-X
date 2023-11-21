package com.example.mediola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class sendprescription extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Intent intent;
    private DatePickerDialog datePickerDialog;
    private Button dateButton,picklocation;
    private TextView locationtxt;
    private final static int PLACE_PICKER_REQUEST = 1;
    static int PReqCode = 1 ;
    APIService apiService;
    private RecyclerView recyclerView;
    private Driveradapter1 drivercategory1;
    private List<Drivers> drivers;
    private List<Prescription1> prescription1List;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendprescription);
        initDatePicker();
        locationtxt = findViewById(R.id.txtlocation);
        picklocation = findViewById(R.id.btnselectloaction);
        dateButton = findViewById(R.id.datePickerButton1);
        final Spinner spinner = (Spinner) findViewById(R.id.loc_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        intent = getIntent();
        recyclerView = findViewById(R.id.driverviewryc1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        drivers = new ArrayList<>();
        prescription1List=new ArrayList<>();
        dateButton.setText(getTodaysDate());
        picklocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION .SDK_INT >=22)
                {
                    hasLocationPermission();

                }
                else
                {
                    openPlacePicker();
                }


            }
        });


    }

    private void hasLocationPermission () {
        //Check if the user has not granted permission...
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Prompt the user to grant permission...
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, PReqCode);

        }
        //Return true if the permission is already granted...
        else
            openPlacePicker();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Check the users response...
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //Start location services
            openPlacePicker();
        }
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
    private void openPlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

            //Enable Wifi
            // wifiManager.setWifiEnabled(true);

        } catch (GooglePlayServicesRepairableException e) {
            Log.d("Exception",e.getMessage());

            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.d("Exception",e.getMessage());

            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case PLACE_PICKER_REQUEST:
                    Place place = PlacePicker.getPlace(sendprescription.this, data);

                    double latitude = place.getLatLng().latitude;
                    double longitude = place.getLatLng().longitude;
                    getCompleteAddressString(latitude,longitude);

            }
        }
    }

    private void getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                // Log.w("My Current loction address", strReturnedAddress.toString());
            } else {
                /// Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Log.w("My Current loction address", "Canont get Address!");
        }
        locationtxt.setText(strAdd);
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

            }

        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
       // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        final String userid = intent.getStringExtra("userid");
        final String docid = intent.getStringExtra("doctorid");
        final String name = intent.getStringExtra("name");
        final String prestxt=intent.getStringExtra("pretxt") ;
        final String date=dateButton.getText().toString();
        final String location=locationtxt.getText().toString();
        String selectedItem = parent.getItemAtPosition(position).toString();
        Calendar c = Calendar.getInstance();
        Date time1= c.getTime();
        String time=String.valueOf(time1);
//start
        Query reference =  FirebaseDatabase.getInstance().getReference("Users").orderByChild("type").equalTo("driver");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                prescription1List.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   Drivers driver = snapshot.getValue(Drivers.class);
                    String fname=driver.getFname();
                    String id=driver.getId();
                    String imageURL=driver.getImageURL();
                    String lname =driver.getLname();

                    String pno=driver.getPhoneno();


                    Prescription1 prescription=new Prescription1(fname,id,imageURL,lname,location,docid,userid,name,prestxt,pno,date,time);

                    if (driver.getLocation().equals(selectedItem)) {

                        prescription1List.add(prescription);
                    }
                    drivercategory1 = new Driveradapter1(getApplicationContext(), prescription1List);
                    recyclerView.setAdapter(drivercategory1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//end
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}