package com.example.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {
    DatabaseReference reff, reff1;
    int i, c;
    String[] x;
    int[] arr;
    LineChart lineChart;
    LineData lineData;
    LineDataSet lineDataSet;
    ArrayList lineEntries;
    String disease,place;

    public DisplayActivity() {
        this.x = new String[20];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display);
        reff = FirebaseDatabase.getInstance().getReference().child("predictive_analysis");
        i = 1;
        c = 1;
        x = new String[20];
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if(bd != null)
        {
            disease = (String) bd.get("disease");
            place=(String)bd.get("place");
        }
        reff = FirebaseDatabase.getInstance().getReference().child("predictive_analysis");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot membersnapshot : dataSnapshot.getChildren()) {
                    if (membersnapshot.child("disease").getValue().toString().equals(disease) && membersnapshot.child("place").getValue().toString().equals(place)) {
                        x = membersnapshot.child("days").getValue().toString().split(",");

                    }
                }
                LineChart lineChart;
                LineData lineData;
                LineDataSet lineDataSet;
                ArrayList lineEntries;
                lineEntries = new ArrayList<>();
                for(int j=1;j<x.length-1;j++)
                {

                    if(!x[j].equals("[null")) {


                            Toast.makeText(DisplayActivity.this, String.valueOf(x[j]), Toast.LENGTH_LONG).show();
                            lineEntries.add(new Entry(j, Integer.parseInt(x[j].trim())));


                    }
                }
                lineChart = findViewById(R.id.lineChart);
                lineDataSet = new LineDataSet(lineEntries, "");
                lineData = new LineData(lineDataSet);
                lineChart.setData(lineData);
                lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                lineDataSet.setValueTextColor(Color.BLACK);
                lineDataSet.setValueTextSize(18f);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

//    private void dothefloowing(ArrayList lineEntries) {
//        //lineChart = findViewById(R.id.lineChart);
//        lineDataSet = new LineDataSet(lineEntries, "");
//        lineData = new LineData(lineDataSet);
//        lineChart.setData(lineData);
//        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//        lineDataSet.setValueTextColor(Color.BLACK);
//        lineDataSet.setValueTextSize(18f);
//    }
//private class AsyncTaskExample extends AsyncTask<String, String, String[]> {
//    ProgressDialog p;
//        @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        p = new ProgressDialog(DisplayActivity.this);
//        p.setMessage("Please wait...It is downloading");
//        p.setIndeterminate(false);
//        p.setCancelable(false);
//        p.show();
//    }
//    @Override
//    protected String[] doInBackground(String... strings) {
//        try {
//            reff= FirebaseDatabase.getInstance().getReference().child("predictive_analysis");
//            reff.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for(DataSnapshot membersnapshot : dataSnapshot.getChildren()){
//                        if(membersnapshot.child("disease").getValue().toString().equals("malaria") && membersnapshot.child("place").getValue().toString().equals("adoor")) {
//                            x= membersnapshot.child("days").getValue().toString().split(",");
//
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return x;
//    }
//    @Override
//    protected void onPostExecute(String[] x) {
//        super.onPostExecute(x);
//        if(x.length!=0) {
//            p.hide();
//            Toast.makeText(DisplayActivity.this,String.valueOf(x[1]),Toast.LENGTH_LONG).show();
//        }else {
//            p.show();
//        }
//    }
//}
//}
