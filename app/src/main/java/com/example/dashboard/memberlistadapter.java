package com.example.dashboard;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class memberlistadapter extends ArrayAdapter<memberlistclass> {
    TextView disease,name1,hospname,sev,latt,longi;
    public memberlistadapter(Activity context, ArrayList<memberlistclass> memberlistclass) {
        super(context, 0, memberlistclass);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView=convertView;
        if(listItemView==null)
        {
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.memberlist,parent,false);

        }
        memberlistclass name=getItem(position);
         disease=listItemView.findViewById(R.id.Disease);
        disease.setText(name.getDisease());
         name1=listItemView.findViewById(R.id.name);
        name1.setText(name.getName());
         hospname=listItemView.findViewById(R.id.hosp);
        hospname.setText(name.getHosp());
         sev=listItemView.findViewById(R.id.severe);
        sev.setText(name.getSevere());
         latt=listItemView.findViewById(R.id.lat);
        latt.setText(name.getLati());
         longi=listItemView.findViewById(R.id.longi);
        longi.setText(name.getLongi());

        return listItemView;
    }
}
