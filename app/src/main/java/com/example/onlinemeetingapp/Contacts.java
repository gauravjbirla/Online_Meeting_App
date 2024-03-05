package com.example.onlinemeetingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Contacts extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    LinearLayout linearLayout;
    FirebaseFirestore firestore ;
    Context context;
    TextView textview;

    CardView cardview;

    int Total_Price;
    String doc,email,name,mobile,rider_email,rider_name,rider_mob,pickuplocation,upi_id,dt;
    String data="";
    private LinearLayout meetings , home , settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        if (ContextCompat.checkSelfPermission(Contacts.this , android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Contacts.this , new String[]{Manifest.permission.CALL_PHONE}, 100);
        }
      meetings=findViewById(R.id.meetings);
      home=findViewById(R.id.nevhome);

        firestore= FirebaseFirestore.getInstance();
        context = getApplicationContext();
        linearLayout = findViewById(R.id.linearLayout);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        email=firebaseUser.getEmail();

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


        firestore.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                try {
                    for (DocumentChange documentChange : value.getDocumentChanges()) {
                        doc = documentChange.getDocument().getId();
                        data = data + " " + doc;

                        if(!email.equals(documentChange.getDocument().getData().get("Email").toString())) {
                            name = documentChange.getDocument().getData().get("Name").toString();
                            mobile = documentChange.getDocument().getData().get("MobileNo").toString();
                            rider_email = documentChange.getDocument().getData().get("Email").toString();

                            addDataToView(name, mobile, rider_email);
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(Contacts.this, "You don't have any rides", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void addDataToView(String name, String mobile, String emailView) {
        cardview = new CardView(getApplicationContext());
        LinearLayout linearLayoutInner = new LinearLayout(getApplicationContext());

        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        LinearLayout.LayoutParams layoutparamstextview = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        layoutparams.setMargins(10, 15, 10, 15);
        layoutparamstextview.setMargins(10, 15, 10, 15);
        //linearLayoutInner.setBackground(getDrawable(R.drawable.cardview_bg));
        cardview.setCardBackgroundColor(getColor(R.color.amp_transparent));
//        cardview.setCardBackgroundColor(Color.parseColor("#F8F8FF"));

        LinearLayout.LayoutParams layoutparamscardview = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutparamscardview.setMargins(15, 15, 15, 15);


        cardview.setLayoutParams(layoutparamscardview);
        cardview.setRadius(15);
        cardview.setPadding(25, 25, 25, 25);
        cardview.setMaxCardElevation(30);
        cardview.setMaxCardElevation(6);
        textview = new TextView(getApplicationContext());
        textview.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        textview.setLayoutParams(layoutparams);

        String text = "Name: " + name + "\nEmail: " + emailView + "\nMobile No:" + mobile;
        textview.setText(text);
        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        textview.setTextColor(Color.BLACK);
        textview.setPadding(25, 25, 25, 25);
        textview.setGravity(Gravity.CENTER);
        linearLayoutInner.addView(textview);

        //  Button call = new Button(getApplicationContext());
        MaterialButton call = new MaterialButton(this);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url="tel:"+mobile.toString();

                try {

                    //        Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse(url));
                    startActivity(intent);
                }
                catch (Exception e){

                    Toast.makeText(context, "An Error Occurred Please Check Entered Number", Toast.LENGTH_SHORT).show();
                }

            }
        });

        call.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        delete.setLayoutParams(layoutparams);
        call.setText("CALL");
        call.setLayoutParams(layoutparams);
        call.setHapticFeedbackEnabled(true);
        call.setIcon(ContextCompat.getDrawable(this,R.drawable.baseline_call_24));

        call.setBackgroundColor(getColor(R.color.dark_green));
        call.setLetterSpacing(0.2f);
        call.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

        MaterialButton payment = new MaterialButton(this);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String FROM=email;
                String TO=emailView;

                Intent send_mail=new Intent(Intent.ACTION_SEND);
                send_mail.putExtra(Intent.EXTRA_EMAIL,new String[]{TO});

                send_mail.setType("message/rfc822");
                startActivity(Intent.createChooser(send_mail,"Choose an Email Client"));
            }
        });
        payment.setLayoutParams(layoutparams);
        payment.setText("EMAIL");
        payment.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        payment.setLayoutParams(layoutparams);
        payment.setHapticFeedbackEnabled(true);
        payment.setIcon(ContextCompat.getDrawable(this,R.drawable.baseline_email_24));

        payment.setBackgroundColor(getColor(R.color.dark_red));
        payment.setLetterSpacing(0.2f);
        LinearLayout linearbtn = new LinearLayout(getApplicationContext());

        linearbtn.addView(call);
        linearbtn.addView(payment);

//        linearLayoutInner.addView(call);
//        linearLayoutInner.addView(payment);
        linearLayoutInner.setLayoutParams(layoutparams);
        linearbtn.setLayoutParams(layoutparamstextview);
        linearLayoutInner.addView(linearbtn);
        linearLayoutInner.setOrientation(LinearLayout.VERTICAL);
        cardview.addView(linearLayoutInner);

        linearLayout.addView(cardview);

    }

}