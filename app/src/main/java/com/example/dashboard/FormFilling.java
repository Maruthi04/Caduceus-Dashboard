package com.example.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cunoraz.gifview.library.GifView;
import com.google.firebase.auth.FirebaseAuth;
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

public class FormFilling extends AppCompatActivity {
ImageButton b1,b2;
Button b3;
EditText e1,e2,e3,e4,e5,e6,e7;
    ArrayList<memberlistclass> arr;
String name,hospname,disease,severe,longi,lati,height,weight,age,bloodgroup;
DatabaseReference reff;
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_form_filling);

                b1=findViewById(R.id.getloc);
                reff= FirebaseDatabase.getInstance().getReference().child("Member");
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //this is the locaiton code
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
                        locationTrack = new LocationTrack(FormFilling.this);


                        if (locationTrack.canGetLocation()) {


                            double longitude = locationTrack.getLongitude();
                            double latitude = locationTrack.getLatitude();
                            longi=String.valueOf(longitude);
                            lati=String.valueOf(latitude);

//                            //Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
//                            Geocoder geocoder = new Geocoder(FormFilling.this, Locale.getDefault());
//                            List<Address> addresses = null;
//                            try {
//                                addresses = geocoder.getFromLocation(latitude, longitude, 1);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            String cityName = addresses.get(0).getAddressLine(0);
//                            String stateName = addresses.get(0).getAddressLine(1);
//                            String countryName = addresses.get(0).getAddressLine(2);
//                            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude)+"city="+cityName+
//                                    "State name="+stateName+"country name"+countryName, Toast.LENGTH_SHORT).show();

                        } else {

                            locationTrack.showSettingsAlert();
                        }
                    }
                });
                b2=findViewById(R.id.button2);
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RadioGroup radioGroup;
                        RadioButton genderradioButton;
                        radioGroup=(RadioGroup)findViewById(R.id.radiogroup);
                        e1=findViewById(R.id.name);
                       e2=findViewById(R.id.hospitalname);
                       e3=findViewById(R.id.disease);
                        e4=findViewById(R.id.height);
                        e5=findViewById(R.id.weight);
                        e6=findViewById(R.id.age);
                        e7=findViewById(R.id.bloodgroup);

                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        genderradioButton = (RadioButton) findViewById(selectedId);
                        if(selectedId==-1){
                            Toast.makeText(FormFilling.this,"Nothing selected", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            severe=genderradioButton.getText().toString();
                            //Toast.makeText(FormFilling.this,genderradioButton.getText(), Toast.LENGTH_SHORT).show();
                        }
                        name=e1.getText().toString();
                        hospname=e2.getText().toString();
                        disease=e3.getText().toString();
                        height=e4.getText().toString();
                        weight=e5.getText().toString();
                        age=e6.getText().toString();
                        bloodgroup=e7.getText().toString();

                        if(lati.equals("0.0")){
                            lati="10.05591238";
                            longi="76.35466571";
                        }
                        Member member=new Member();
                        member.setName(name);
                        member.setHosp(hospname);
                        member.setDisease(disease);
                        member.setSevere(severe);
                        member.setLati(lati);
                        member.setLongi(longi);
                        member.setHeight(height);
                        member.setWeight(weight);
                        member.setAge(age);
                        member.setBloodgroup(bloodgroup);
                        reff.child(disease).setValue(member);

                        Toast.makeText(FormFilling.this,"data inserted"+name+" "+hospname+" "+disease+" "+severe+" "
                        +longi+" "+lati+" "+height+" "+weight+" "+age+" "+bloodgroup+" ",Toast.LENGTH_LONG).show();
                    }
                });
b3=findViewById(R.id.button3);
b3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        DatabaseReference ref1;
        ref1=FirebaseDatabase.getInstance().getReference("Member");
        arr=new ArrayList<memberlistclass>();
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuffer buffer=new StringBuffer();
                    for(DataSnapshot membersnapshot : dataSnapshot.getChildren()){
                        String name=membersnapshot.child("name").getValue().toString();
                        String hosp=membersnapshot.child("hosp").getValue().toString();
                        String disease=membersnapshot.child("disease").getValue().toString();
                        String severe=membersnapshot.child("severe").getValue().toString();
                        String lati=membersnapshot.child("lati").getValue().toString();
                        String longi=membersnapshot.child("longi").getValue().toString();
                        buffer.append(name+hosp+disease+severe+lati+longi+"\n");
                        arr.add(new memberlistclass(name,hosp,disease,severe,lati,longi));


                    }
                memberlistadapter ada=new memberlistadapter(FormFilling.this,arr);
                ListView listView=findViewById(R.id.listview1);
                listView.setAdapter(ada);
                    Toast.makeText(FormFilling.this,buffer.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
});

    }
    public void logout(View v)
    {
        Intent in =new Intent(FormFilling.this,LoginActivity.class);
        startActivity(in);
        FirebaseAuth.getInstance().signOut();
    }
    public void backbutton(View v)
    {
        onBackPressed();
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
