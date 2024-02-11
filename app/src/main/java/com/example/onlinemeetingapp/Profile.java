package com.example.onlinemeetingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends Activity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView uname, email,mob;
    FirebaseFirestore db ;
    Context context;
    String name, uemail,mobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        uname = (TextView) findViewById(R.id.emailid);
        email = (TextView) findViewById(R.id.name);
//        mob = (TextView) findViewById(R.id.updatePhoneNo);

        db= FirebaseFirestore.getInstance();
        context = getApplicationContext();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        uemail=firebaseUser.getEmail();
        try {
            DocumentReference documentReference = db.collection("Users").document(uemail);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        name = documentSnapshot.getString("Name");

//                        mobile = documentSnapshot.getString("MobileNo");
                        setEditTextValues(name,uemail);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
        catch (Exception e){
            Toast.makeText(context, "Error occured", Toast.LENGTH_SHORT).show();
        }
    }

    private void setEditTextValues(String name, String uemail) {
        uname.setText(uemail);
        email.setText(name);
//        mob.setText(mobile);

    }

    public void signOut(View view) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseAuth.signOut();

        Intent intent=new Intent(Profile.this, Login.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Signed Out Successfully!!",Toast.LENGTH_SHORT).show();
    }
}
