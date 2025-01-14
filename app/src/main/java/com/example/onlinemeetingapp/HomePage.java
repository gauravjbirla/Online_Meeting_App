package com.example.onlinemeetingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URL;

public class HomePage extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private LinearLayout meetings , contacts , settings , crtbtn , jnbtn , schedule;
    TextView name, email;
    FirebaseFirestore db ;
    String username, useremail;
    CardView profile;
    RelativeLayout topPanel;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    profile=findViewById(R.id.profilebtn);

        name=(TextView) findViewById(R.id.username);
        email=(TextView) findViewById(R.id.emailid);
        meetings=findViewById(R.id.meetings);
        contacts=findViewById(R.id.contacts);
        crtbtn=findViewById(R.id.crtbtn);
        jnbtn=findViewById(R.id.jnbtn);
        topPanel=findViewById(R.id.topPanel);
        schedule=findViewById(R.id.schedule);



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

        jnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,CreateMeeting.class));
            }
        });
        crtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(HomePage.this, JitsiWebView.class));
                Uri uri= Uri.parse("https://meet.jit.si/");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });

        topPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, ProfileNew.class));
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,ScheduleMeet.class));
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
            startActivity(new Intent(HomePage.this, ProfileNew.class));
        }
    });
    }
    private void setTextViewValues(String username, String useremail) {
        name.setText(username);
        email.setText(useremail);
    }
}