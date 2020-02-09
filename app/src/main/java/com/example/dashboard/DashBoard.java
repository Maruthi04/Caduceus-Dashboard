package com.example.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class DashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
    }
    public void chtbotpage(View v)
    {
        Intent in=new Intent(this,chatbot.class);
        startActivity(in);
    }
    public void expericne(View v)
    {
        Intent in=new Intent(this,exploreactivity.class);
        startActivity(in);
    }
    public void logout(View v)
    {
        Intent in =new Intent(DashBoard.this,LoginActivity.class);
        startActivity(in);
        FirebaseAuth.getInstance().signOut();
    }
}
