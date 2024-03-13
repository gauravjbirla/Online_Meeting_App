package com.example.onlinemeetingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.badge.BadgeUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateMeeting extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference reference, dbRef;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    LinearLayout linearLayout;
    FirebaseFirestore firestore ;
    private EditText codebox;
    String email,doc,name,mobile,data;

    private Button button , share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);


        mAuth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference();

        codebox=findViewById(R.id.codeBox);
        button=findViewById(R.id.button1);

        firestore= FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        email=firebaseUser.getEmail();

        URL serverurl;

        try{
            serverurl=new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultoptions= new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverurl)
                    .build();
            JitsiMeet.setDefaultConferenceOptions(defaultoptions);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text= codebox.getText().toString().trim();

                insertData();

                if (text.length()>0)
                {
                    JitsiMeetConferenceOptions options =new JitsiMeetConferenceOptions.Builder()
                            .setRoom(text)
                            .setFeatureFlag("invite.enable", true)
                            .setFeatureFlag("welcomepage.enable", true)
                            .setAudioMuted(false)
                            .setFeatureFlag("participants.enabled",true)
                            .setVideoMuted(false)
                            .build();
                    JitsiMeetActivity.launch(CreateMeeting.this,options);
                }
            }
        });

//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent shareIntent= new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_TEXT,"Enter The Roomcode To Join Meeting : "+codebox);
//                shareIntent.setType("text/plain");
//                startActivity(shareIntent);
//            }
//        });

    }

    private void insertData() {
        String text= codebox.getText().toString().trim();
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
        String strDate = dateFormat.format(date);



        Map<String, Object> user = new HashMap<>();
        user.put("DateTime", strDate);
        firestore.collection(""+email+"meetings")
                .document(""+text)
                .set(user).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreateMeeting.this, "Created Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateMeeting.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                });

    }

//    private void insertData() {
//        String text= codebox.getText().toString().trim();
//
//        dbRef = reference;
//        Toast.makeText(CreateMeeting.this, "Entered the function", Toast.LENGTH_SHORT).show();
//        firestore.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                try {
//                    for (DocumentChange documentChange : value.getDocumentChanges()) {
//                        doc = documentChange.getDocument().getId();
//                        data = data + " " + doc;
//
//                        if(!email.equals(documentChange.getDocument().getData().get("Email").toString())) {
//                            name = documentChange.getDocument().getData().get("Name").toString();
//                            mobile = documentChange.getDocument().getData().get("MobileNo").toString();
//                            final String uniqueKey = dbRef.push().getKey();
//
//
//
//                            UserData UserData = new UserData(name, email, mobile, uniqueKey);
//
//                            dbRef.child("users").child(name).setValue(UserData).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//
//
//                                    Toast.makeText(CreateMeeting.this, "User Added", Toast.LENGTH_SHORT).show();
//                                    finish();
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(CreateMeeting.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                        }
//                    }
//                } catch (Exception e) {
//                    Toast.makeText(CreateMeeting.this, "No user", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//    }

}