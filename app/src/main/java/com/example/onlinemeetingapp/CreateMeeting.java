package com.example.onlinemeetingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.badge.BadgeUtils;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class CreateMeeting extends AppCompatActivity {

    private EditText codebox;
    private Button button , share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meeting);

        codebox=findViewById(R.id.codeBox);
        button=findViewById(R.id.button);


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

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent= new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,"Enter The Roomcode To Join Meeting : "+codebox);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });
    }
}