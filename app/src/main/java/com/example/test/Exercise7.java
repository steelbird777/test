package com.example.test;

import static com.google.android.material.internal.ViewUtils.getBackgroundColor;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.fonts.FontFamily;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class Exercise7 extends AppCompatActivity {
    private TextView textView;
    private Button nxtBtn;
    private Spinner sizeSpinner;
    private Button resetBtn;
    private Button increaseBtn;
    private Button decreaseBtn;
    private Spinner colorSpinner;
    private int color[];
    private float defaultTextSize;
    private int defaultBackgroundColor;
    private ToggleButton toggleButton;
    private Typeface fontOne;
    private Typeface fontTwo;
    private Typeface defaultFont;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise7);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Random random = new Random();
        color = new int[]{Color.WHITE, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN};
        resetBtn = findViewById(R.id.resetBtn);
        increaseBtn = findViewById(R.id.increaseBtn);
        decreaseBtn = findViewById(R.id.decreaseBtn);
        colorSpinner = findViewById(R.id.backclrSpinner);
        textView = findViewById(R.id.textView);
        nxtBtn = findViewById(R.id.nxtBtn);
        sizeSpinner = findViewById(R.id.sizeSpinner);
        toggleButton = findViewById(R.id.toggleButton);
        defaultFont = Typeface.DEFAULT;
        radioGroup = findViewById(R.id.radioButton);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedColor = radioGroup.getCheckedRadioButtonId();
                int color = Color.TRANSPARENT;
                if(selectedColor == R.id.radioWhiteColor){
                    color = Color.WHITE;
                }else if(selectedColor == R.id.radioRedColor){
                    color = Color.RED;
                }else if(selectedColor == R.id.radioGreenColor){
                    color = Color.GREEN;
                }else if(selectedColor == R.id.radioBlueColor){
                    color = Color.BLUE;
                }
                findViewById(R.id.main).setBackgroundColor(color);
            }
        });

        fontOne = Typeface.createFromAsset(getAssets(), "fonts/plawrite-font.ttf");
        fontTwo = Typeface.createFromAsset(getAssets(), "fonts/oswald-font.ttf");
        textView.setTypeface(fontOne);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    textView.setTypeface(fontOne);
                }else{
                    textView.setTypeface(fontTwo);
                }
            }
        });


        increaseBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                increaseTextSize();
            }
        });
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseTextSize();
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetColorSize();
            }
        });

        defaultTextSize = textView.getTextSize();
        defaultBackgroundColor = getBackgroundColor(findViewById(R.id.main));

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,new String[]{"White","Red", "Green", "Blue", "Yellow", "Magenta", "Cyan"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                findViewById(R.id.main).setBackgroundColor(color[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise7.this, Exercise8.class);
                startActivity(intent);
            }
        });
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this,R.array.size_array, android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSize = parent.getItemAtPosition(position).toString();
                float size = Float.parseFloat(selectedSize);
                textView.setTextSize(size);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void increaseTextSize() {
        float currentSize = textView.getTextSize();
        textView.setTextSize(0,currentSize + 2);
    }
    public void decreaseTextSize() {
        float currentSize = textView.getTextSize();
        textView.setTextSize(0,currentSize - 2);
    }
    public void resetColorSize() {
        textView.setTextSize(0, defaultTextSize);
        setBackgroundColor(findViewById(R.id.main), defaultBackgroundColor);
        textView.setTypeface(defaultFont);
    }

    private int getBackgroundColor(View view) {
        Drawable background = view.getBackground();
        if(background instanceof ColorDrawable){
            return ((ColorDrawable) background).getColor();
        }
        return Color.TRANSPARENT;
    }

    private void setBackgroundColor(View view, int color) {
        view.setBackgroundColor(color);
    }
}