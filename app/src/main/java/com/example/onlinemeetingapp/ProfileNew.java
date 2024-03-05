package com.example.onlinemeetingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileNew extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextInputEditText uname, email, mob;
    FirebaseFirestore db;
    Context context;
    String name, usmail, mobile;
    Button logoutbtn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_new);

        uname = (TextInputEditText) findViewById(R.id.updateName);
        email = (TextInputEditText) findViewById(R.id.updateEmail);
        mob = (TextInputEditText) findViewById(R.id.updatePhoneNo);
        logoutbtn=findViewById(R.id.logoutBtn);

        db = FirebaseFirestore.getInstance();
        context = getApplicationContext();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth=FirebaseAuth.getInstance();
                firebaseUser=firebaseAuth.getCurrentUser();
                firebaseAuth.signOut();

                Intent intent= new Intent(ProfileNew.this,Login.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Signed Out Successfully!!",Toast.LENGTH_SHORT).show();
            }
        });

        usmail = firebaseUser.getEmail();
        try {
            DocumentReference documentReference = db.collection("Users").document(usmail);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        name = documentSnapshot.getString("Name");

//                        mobile = documentSnapshot.getString("MobileNo");
                        setEditTextValues(name, usmail);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, "Error occured", Toast.LENGTH_SHORT).show();
        }

    }

    private void setEditTextValues (String name, String uemail){
        uname.setText(name);
        email.setText(uemail);
//        mob.setText(mobile);

    }
}