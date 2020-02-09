package com.example.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class videocall extends AppCompatActivity {
EditText e1,e2;
String s,d;
Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videocall);
        e1=findViewById(R.id.source);
        e2=findViewById(R.id.dest);
        s=e1.getText().toString();
        d=e2.getText().toString();
        b1=findViewById(R.id.callbutton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(videocall.this,maincall.class);
//                i.putExtra("user",s);
//                i.putExtra("peer",d);
//                startActivity(i);
            }
        });
    }
}
