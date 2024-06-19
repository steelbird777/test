package com.example.test;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class Exercise6 extends AppCompatActivity {
    private ListView reminderListView;
    private ArrayAdapter<String> reminderAdapter;
    private ArrayList<String> remindersList;
    private Calendar calendar;
    private TextView emptyTextView;
    private TextView dateText;
    private TextView timeText;
    private Button dateBtn;
    private Button timeBtn;
    private Button nextBtn;
    private Button setReminderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise6);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        reminderListView = findViewById(R.id.reminderListView);
        emptyTextView = findViewById(R.id.emptyTextView);
        setReminderBtn = findViewById(R.id.setReminderBtn);
        remindersList = new ArrayList<>();
        reminderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, remindersList);
        reminderListView.setAdapter(reminderAdapter);

        checkIfListEmpty();

        calendar = Calendar.getInstance();

        dateText = findViewById(R.id.dateText);
        timeText = findViewById(R.id.timeText);
        dateBtn = findViewById(R.id.dateBtn);
        timeBtn = findViewById(R.id.timeBtn);
        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise6.this, Exercise7.class);
                startActivity(intent);
            }
        });
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });
        setReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker(v);
            }
        });
    }

    private void checkIfListEmpty() {
        if(remindersList.isEmpty()){
            emptyTextView.setVisibility(View.VISIBLE);
            reminderListView.setVisibility(View.GONE);
        }else{
            emptyTextView.setVisibility(View.GONE);
            reminderListView.setVisibility(View.VISIBLE);
        }
    }

    private void showDateTimePicker(View view){
        final Calendar currentDateTime = Calendar.getInstance();
        new DatePickerDialog(this, dateSetListener,
                currentDateTime.get(Calendar.YEAR),
                currentDateTime.get(Calendar.MONTH),
                currentDateTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            new TimePickerDialog(Exercise6.this, timeSetListener,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false).show();
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            String dateTime = sdf.format(calendar.getTime());

            addReminder(dateTime);
        }
    };

    private  void addReminder(String dateTime) {
        remindersList.add(dateTime);
        Toast.makeText(this, "Reminder set for " + dateTime, Toast.LENGTH_SHORT).show();
        reminderAdapter.notifyDataSetChanged();
        checkIfListEmpty();
    }
    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            dateText.setText("Selected Date: " + selectedDate);
        },year,month,day);
        datePickerDialog.show();
    }
    private void showTimeDialog() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
            String selectedTime = hourOfDay + ":" + minute1;
            timeText.setText("Selected Time: " + selectedTime);
        },hour,minute,true);
        timePickerDialog.show();
        }
}