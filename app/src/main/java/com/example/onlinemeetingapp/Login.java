package com.example.onlinemeetingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    TextInputEditText mail,pass;
    TextView gotoregister;
    RelativeLayout login;
    String email_regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    public static String get_DBName,get_DBEmail;
    FirebaseFirestore firestore;
    String password,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    gotoregister=findViewById(R.id.gotoRegister);
    login=findViewById(R.id.btnLogin);
    mail=findViewById(R.id.mail);
    pass=findViewById(R.id.pass);



    firebaseAuth = FirebaseAuth.getInstance();
    firebaseUser = firebaseAuth.getCurrentUser();
    firestore= FirebaseFirestore.getInstance();

        //Stay logged in
    if (firebaseUser!=null)
    {
        finish();
        startActivity(new Intent(Login.this, HomePage.class));
    }
    gotoregister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Login.this,Register.class));
        }
    });
    login.setOnClickListener(new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            email=mail.getText().toString().trim();
            password=pass.getText().toString().trim();

            if (!email.matches(email_regex)) {
                mail.setError("Enter correct Email");
            } else if (password.length() < 6) {
                pass.setError("Password Length must be greater than 5");
            } else {

                firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        DocumentReference documentReference=firestore.collection("Users").document(email);
                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    get_DBName=documentSnapshot.getString("Name");
                                    get_DBEmail=documentSnapshot.getString("Email");

                                    checkmailverification();
                                    Toast.makeText(getApplicationContext(), "Login in Successful", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Login.this, HomePage.class);
//                                intent.putExtra("NAME",get_DBName);
//                                intent.putExtra("Email",get_DBEmail);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Login in Failed\nPlease Recheck Username and Password", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Login in Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    });
    }
    private void checkmailverification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        if (firebaseUser.isEmailVerified()==true)
        {
            Toast.makeText(getApplicationContext(),"Logged In",Toast.LENGTH_SHORT).show();
            finish();
//            startActivity(new Intent(Login.this, ChooseMode.class));
            startActivity(new Intent(Login.this, HomePage.class));
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Verify Your Email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }

    }

}