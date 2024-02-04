package com.example.onlinemeetingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends Activity {
    String email_regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextInputEditText name,email,mobile_no,pass;
    String NAME,EMAIL,MOBILE_NO,Password;
    TextView gotologin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    gotologin=findViewById(R.id.lnkLogin);
    
        name=(TextInputEditText) findViewById(R.id.name);
        email=(TextInputEditText) findViewById(R.id.mail);
        mobile_no=(TextInputEditText) findViewById(R.id.mob);
        pass=(TextInputEditText) findViewById(R.id.pass);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    gotologin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Register.this,Login.class));
        }
    });
    }

    public void reg(View view){
        NAME=name.getText().toString();
        EMAIL=email.getText().toString();
        MOBILE_NO=mobile_no.getText().toString();
        Password=pass.getText().toString();

        Map<String, Object> user = new HashMap<>();
        user.put("Name", NAME);
        user.put("Email", EMAIL);
        user.put("MobileNo", MOBILE_NO);
        user.put("Password", Password);

        if (!EMAIL.matches(email_regex)) {
            email.setError("Enter correct Email");
        }
        else if(!(MOBILE_NO.length()==10)){
            mobile_no.setError("Enter Correct Mobile Number");
        }
        else if (Password.length() < 6) {
            pass.setError("Password Length must be greater than 5");
        }
        else{
            db.collection("Users")
                    .document(""+EMAIL)
                    .set(user).
                    addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Register.this, "Created Account Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Register.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
            firebaseAuth.createUserWithEmailAndPassword(EMAIL, Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Register.this,Login.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Register.this, "Can't Register!An Error Occurred", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
