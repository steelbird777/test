package com.example.test;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class Exercise9 extends AppCompatActivity {
    private Button alarmBtn;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise9);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        timePicker = findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmBtn = findViewById(R.id.alarmBtn);
        Calendar calendar = Calendar.getInstance();
        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Exercise9.this, "Alarm set Successfully!!!", Toast.LENGTH_SHORT).show();
                setAlarm();
            }
        });
    }
    private void setAlarm() {
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

    Intent intent = new Intent(Exercise9.this, AlarmHelper.class);
    alarmIntent = PendingIntent.getBroadcast(Exercise9.this,0,intent, PendingIntent.FLAG_IMMUTABLE);
    alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),alarmIntent);
    }
}