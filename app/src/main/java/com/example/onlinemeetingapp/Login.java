package com.example.onlinemeetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    TextInputEditText mail,pass;
    TextView gotoregister;
    RelativeLayout login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    gotoregister=findViewById(R.id.gotoRegister);
    login=findViewById(R.id.btnLogin);
    mail=findViewById(R.id.mail);
    pass=findViewById(R.id.pass);

    String email=mail.getText().toString().trim();
    String password=pass.getText().toString().trim();

    gotoregister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Login.this,Register.class));
        }
    });
    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    });
    }

}