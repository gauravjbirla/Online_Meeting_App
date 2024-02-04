package com.example.onlinemeetingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Register extends Activity {

    TextView gotologin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    gotologin=findViewById(R.id.lnkLogin);

    gotologin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Register.this,Login.class));
        }
    });
    }

    public void Register(View view) {
    }
}
