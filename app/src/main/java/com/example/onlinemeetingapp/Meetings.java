package com.example.onlinemeetingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class Meetings extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    LinearLayout linearLayout;
    FirebaseFirestore firestore ;
    Context context;
    TextView textview;

    CardView cardview;

    int Total_Price;
    String doc,email,DateTime;
    String data="";

    private LinearLayout home, settings, contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        home=findViewById(R.id.nevhome);
        contacts=findViewById(R.id.contacts);

        firestore= FirebaseFirestore.getInstance();
        context = getApplicationContext();
        linearLayout = findViewById(R.id.linearLayout);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        email=firebaseUser.getEmail();


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Meetings.this, HomePage.class));
                finish();
            }
        });
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Meetings.this, Contacts.class));
                finish();
            }
        });

        firestore.collection(""+email+"meetings").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                try {
                    for (DocumentChange documentChange : value.getDocumentChanges()) {
                        doc = documentChange.getDocument().getId();
                        data = data + " " + doc;



                            DateTime = documentChange.getDocument().getData().get("DateTime").toString();

                            addDataToView(doc, DateTime);

                    }
                } catch (Exception e) {
                    Toast.makeText(Meetings.this, "Contacts is Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void addDataToView(String doc, String DateTime) {
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
        layoutparamscardview.setMargins(50, 50, 50, 15);


        cardview.setLayoutParams(layoutparamscardview);
        cardview.setRadius(15);
        cardview.setPadding(25, 25, 25, 25);
        cardview.setMaxCardElevation(30);
        cardview.setMaxCardElevation(6);
        textview = new TextView(getApplicationContext());
        textview.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        textview.setLayoutParams(layoutparams);

        String text = "Meeting Id : \n" + doc + "\nTime: \n" + DateTime;
        SpannableString ss = new SpannableString(text);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ss.setSpan(boldSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textview.setText(text);
        Typeface typeface= ResourcesCompat.getFont(context,R.font.roboto_mono);
        textview.setTypeface(typeface,Typeface.BOLD);

        textview.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        textview.setTextColor(Color.BLACK);
        textview.setPadding(25, 25, 25, 25);
        textview.setGravity(Gravity.CENTER);
        linearLayoutInner.addView(textview);

        //  Button call = new Button(getApplicationContext());

//        linearLayoutInner.addView(call);
//        linearLayoutInner.addView(payment);
        linearLayoutInner.setLayoutParams(layoutparams);

        linearLayoutInner.setOrientation(LinearLayout.VERTICAL);
        cardview.addView(linearLayoutInner);

        linearLayout.addView(cardview);

    }

}