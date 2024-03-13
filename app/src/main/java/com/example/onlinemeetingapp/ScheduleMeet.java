package com.example.onlinemeetingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinemeetingapp.databinding.ActivityScheduleMeetBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class ScheduleMeet extends AppCompatActivity {

    private @NonNull ActivityScheduleMeetBinding binding;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    Button select , startinstant;
    String timePicked;
    TextView meetingdetails , timelable , time ;

    private static final String PREF_NAME = "YourPrefs";
    private static final String KEY_MEETING_DETAILS_VISIBLE = "meetingDetailsVisible";
    private static final String KEY_TIME_LABEL_VISIBLE = "timeLabelVisible";
    private static final String KEY_TIME_PICKED = "timePicked";
    private SharedPreferences sharedPreferences;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScheduleMeetBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_schedule_meet);

        createNotificationChannel();
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean isMeetingDetailsVisible = sharedPreferences.getBoolean(KEY_MEETING_DETAILS_VISIBLE, true);
        boolean isTimeLabelVisible = sharedPreferences.getBoolean(KEY_TIME_LABEL_VISIBLE, false);
        String savedTimePicked = sharedPreferences.getString(KEY_TIME_PICKED, "");




        select=findViewById(R.id.selectime);
        meetingdetails=findViewById(R.id.meetingdetails);
        timelable=findViewById(R.id.timelable);
        time=findViewById(R.id.timetext);
        startinstant=findViewById(R.id.startinstant);
        //schedule=findViewById(R.id.schedulemeeting);

        if (isMeetingDetailsVisible) {
            meetingdetails.setVisibility(View.VISIBLE);
        } else {
            meetingdetails.setVisibility(View.INVISIBLE);
        }

        if (isTimeLabelVisible) {
            timelable.setVisibility(View.VISIBLE);
            time.setText(savedTimePicked);
        } else {
            timelable.setVisibility(View.INVISIBLE);
        }

        startinstant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri= Uri.parse("https://meet.jit.si/");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });


    }

    private void setAlarm() {

        alarmManager= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent =  new Intent(ScheduleMeet.this,NotifReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ScheduleMeet.this , 0, intent, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,pendingIntent);

        Toast.makeText(ScheduleMeet.this,"Meet Scheduled Successfully",Toast.LENGTH_SHORT).show();
        changeLayout();
        startActivity(new Intent(ScheduleMeet.this,HomePage.class));
        select.setText("Schedule New Meeting");
    }

    private void showTimePicker() {
            picker= new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Set Time For Meeting")
                    .build();

            picker.show(getSupportFragmentManager(), "meeetingapp");


        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
                calendar.set(Calendar.MINUTE,picker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
                int hour= picker.getHour();
                int minute = picker.getMinute();
                timePicked = formatTime(hour,minute);
                setAlarm();
            }
        });


    }

    private String formatTime(int hour, int minute) {
        return String.format("%02d:%02d", hour, minute);
    }

    private void changeLayout() {

        meetingdetails.setVisibility(View.INVISIBLE);
        timelable.setVisibility(View.VISIBLE);
        time.setText(timePicked);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_MEETING_DETAILS_VISIBLE, false);
        editor.putBoolean(KEY_TIME_LABEL_VISIBLE, true);
        editor.putString(KEY_TIME_PICKED, timePicked);
        editor.apply();
    }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name = "meetingappchannel";
            String description = "Channel For Meeting App";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel= new NotificationChannel("meetingapp",name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}