package com.example.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class doctordashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctordashboard);
    }
    public void chtbotpage(View v)
    {
        Intent in=new Intent(this,chatbot.class);
        startActivity(in);
    }

    public void formfillingpage(View v)
    {
        Intent in=new Intent(this,FormFilling.class);
        startActivity(in);
    }
    public void expericne(View v)
    {
        Intent in=new Intent(this,exploreactivity.class);
        startActivity(in);
    }
    public void logout(View v)
    {
        Intent in =new Intent(doctordashboard.this,LoginActivity.class);
        startActivity(in);
        FirebaseAuth.getInstance().signOut();
    }
    public void makecalll(View v)
    {
        Intent in =new Intent(doctordashboard.this,videocall.class);
        startActivity(in);
    }
}
