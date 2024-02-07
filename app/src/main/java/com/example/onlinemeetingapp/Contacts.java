package com.example.onlinemeetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Contacts extends AppCompatActivity {


    private LinearLayout meetings , home , settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

      meetings=findViewById(R.id.meetings);
      home=findViewById(R.id.nevhome);
      settings=findViewById(R.id.settings);




      //Bottom Navigation
        meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Contacts.this, Meetings.class));
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Contacts.this, HomePage.class));
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Contacts.this,Settings.class));
            }
        });
    }
}