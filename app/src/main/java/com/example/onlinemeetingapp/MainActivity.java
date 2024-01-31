package com.example.onlinemeetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Thread.sleep(500);
            Intent intent=new Intent(MainActivity.this,Login.class);
            startActivity(intent);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}