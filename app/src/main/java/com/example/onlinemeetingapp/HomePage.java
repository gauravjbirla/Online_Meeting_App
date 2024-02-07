package com.example.onlinemeetingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomePage extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    LinearLayout meetings , contacts , settings;
    TextView name, email;
    FirebaseFirestore db ;
    String username, useremail;
    CardView profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    profile=findViewById(R.id.profilebtn);

        name=(TextView) findViewById(R.id.username);
        email=(TextView) findViewById(R.id.emailid);
        meetings=findViewById(R.id.meetings);
        contacts=findViewById(R.id.contacts);
        settings=findViewById(R.id.settings);

        //Bottom Navigation
        meetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Meetings.class));
            }
        });
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Contacts.class));
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, Settings.class));
            }
        });



        // User Authentication
        db= FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        useremail=firebaseUser.getEmail();
        try {
            DocumentReference documentReference = db.collection("Users").document(useremail);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        username = documentSnapshot.getString("Name");

                        useremail = documentSnapshot.getString("Email");
                        setTextViewValues(username,useremail);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
        catch (Exception e){
            Toast.makeText(HomePage.this, "Error occured", Toast.LENGTH_SHORT).show();
        }
    profile.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(HomePage.this, Profile.class));
        }
    });
    }
    private void setTextViewValues(String username, String useremail) {
        name.setText(username);
        email.setText(useremail);
    }
}