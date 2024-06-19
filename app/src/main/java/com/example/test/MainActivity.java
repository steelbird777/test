package com.example.test;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button changeFontbtn;
    private Button changeSizebtn;
    private Button displayTextbtn;
    private Button resetBtn;
    private Random random;
    private Button nextbtn;
    private int colors[];
    private float defaultTextSize;
    private int defaultFontColour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        random = new Random();
        colors = new int[]{Color.RED,Color.BLUE, Color.GREEN, Color.YELLOW, Color.BLACK, Color.GRAY, Color.BLACK, Color.MAGENTA, Color.MAGENTA};
        textView = findViewById(R.id.textView);
        defaultTextSize = textView.getTextSize();
        defaultFontColour = textView.getCurrentTextColor();
        changeFontbtn = findViewById(R.id.buttonChangeColor);
        changeSizebtn = findViewById(R.id.buttonIncreaseFontSize);
        displayTextbtn = findViewById(R.id.buttonDisplayText);
        nextbtn = findViewById(R.id.nextBtn);
        resetBtn = findViewById(R.id.buttonReset);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Exercise2.class);
                startActivity(intent);
            }
        });

        displayTextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayText();
            }
        });

        changeSizebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextSize();
            }
        });

        changeFontbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextFont();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetToNormal();
            }
        });
    }
    private void changeTextSize(){
        float currentSize =textView.getTextSize();
        textView.setTextSize(0,currentSize + 5);
    }
    private int getRandomColor(){
        return colors[random.nextInt(colors.length)];
    }
    private void changeTextFont(){
        int color = getRandomColor();
        textView.setTextColor(color);
    }
    private void displayText(){
        String text = "Sharon";
        textView.setText(text.toUpperCase());
    }
    private void resetToNormal(){
        textView.setTextSize(0, defaultTextSize);
        textView.setTextColor(defaultFontColour);
        textView.setText("Hello World!");
    }

}