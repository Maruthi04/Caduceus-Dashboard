package com.example.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class exploreactivity extends AppCompatActivity {
Button b1;
        ImageButton b2;
String disease,place,lati,longi;
    DatabaseReference reff, reff1;
    int i, c=0;
    String[] x;
    int[] arr;
EditText e1,e2;
    LineChart lineChart;
    LineData lineData;
    LineDataSet lineDataSet;
    ArrayList lineEntries;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exploreactivity);
        b1=findViewById(R.id.b11);
        b2=findViewById(R.id.getloc);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this is the locaiton code
                c=1;
                Boolean isFlashAvailable = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                permissions.add(ACCESS_FINE_LOCATION);
                permissions.add(ACCESS_COARSE_LOCATION);
                permissionsToRequest = findUnAskedPermissions(permissions);
                //get the permissions we have asked for before but are not granted..
                //we will store this in a global list to access later.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


                    if (permissionsToRequest.size() > 0)
                        requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                }
                locationTrack = new LocationTrack(exploreactivity.this);


                if (locationTrack.canGetLocation()) {


                    double longitude = locationTrack.getLongitude();
                    double latitude = locationTrack.getLatitude();
                    longi=String.valueOf(longitude);
                    lati=String.valueOf(latitude);

                    //Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
                    Geocoder geocoder = new Geocoder(exploreactivity.this, Locale.getDefault());
                    List<Address> addresses = null;

                    if(latitude!=0.0) {
                        try {
                            addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    String cityName = addresses.get(0).getAddressLine(0);
                    String stateName = addresses.get(0).getAddressLine(1);
                    String countryName = addresses.get(0).getAddressLine(2);
                        Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude)+"city="+cityName+
                                "State name="+stateName+"country name"+countryName, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        try {
                            addresses = geocoder.getFromLocation(10.05591238, 76.35466571, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String cityName = addresses.get(0).getAddressLine(0);
                        String stateName = addresses.get(0).getAddressLine(1);
                        String countryName = addresses.get(0).getAddressLine(2);
                        Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude)+"city="+cityName+
                                "State name="+stateName+"country name"+countryName, Toast.LENGTH_SHORT).show();

                    }
                } else {

                    locationTrack.showSettingsAlert();
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                e1=findViewById(R.id.diseasee);
                e2=findViewById(R.id.place);
                disease=e1.getText().toString();
                if(c==0) {
                    place = e2.getText().toString();
                }
                else{
                    place="kochi";
                }
                Intent intent = new Intent(exploreactivity.this, DisplayActivity.class);
                intent.putExtra("disease", disease);
                intent.putExtra("place", place);
                startActivity(intent);
//                reff = FirebaseDatabase.getInstance().getReference().child("predictive_analysis");
//                i = 1;
//                c = 1;
//                x = new String[20];
//                reff = FirebaseDatabase.getInstance().getReference().child("predictive_analysis");
//                reff.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot membersnapshot : dataSnapshot.getChildren()) {
//                            if (membersnapshot.child("disease").getValue().toString().equals(disease) && membersnapshot.child("place").getValue().toString().equals(place)) {
//                                x = membersnapshot.child("days").getValue().toString().split(",");
//
//                            }
//                        }
//                        LineChart lineChart;
//                        LineData lineData;
//                        LineDataSet lineDataSet;
//                        ArrayList lineEntries;
//                        lineEntries = new ArrayList<>();
//                        for(int j=1;j<x.length-1;j++)
//                        {
//
//                            if(!x[j].equals("[null")) {
//
//
//                                Toast.makeText(exploreactivity.this, String.valueOf(x[j]), Toast.LENGTH_LONG).show();
//                                lineEntries.add(new Entry(j, Integer.parseInt(x[j].trim())));
//
//
//                            }
//                        }
//                        lineChart = findViewById(R.id.lineChart);
//                        lineDataSet = new LineDataSet(lineEntries, "");
//                        lineData = new LineData(lineDataSet);
//                        lineChart.setData(lineData);
//                        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//                        lineDataSet.setValueTextColor(Color.BLACK);
//                        lineDataSet.setValueTextSize(18f);
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
            }
        });
    }

    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }

        return result;
    }
    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }
    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

}
