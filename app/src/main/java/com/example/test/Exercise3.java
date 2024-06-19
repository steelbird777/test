package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Exercise3 extends AppCompatActivity implements View.OnClickListener{
    private EditText num1;
    private EditText num2;
    private TextView answer;
    private Button addBtn;
    private Button subBtn;
    private Button mulBtn;
    private Button divBtn;
    private Button nxtBtn;
    int number1, number2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        answer = findViewById(R.id.answer);
        addBtn = findViewById(R.id.addBtn);
        subBtn = findViewById(R.id.subBtn);
        mulBtn = findViewById(R.id.mulBtn);
        divBtn = findViewById(R.id.divBtn);
        addBtn.setOnClickListener((View.OnClickListener) this);
        subBtn.setOnClickListener((View.OnClickListener) this);
        mulBtn.setOnClickListener((View.OnClickListener) this);
        divBtn.setOnClickListener((View.OnClickListener) this);

        Button nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise3.this, Exercise4.class);
                startActivity(intent);
            }
        });
    }

    public int getIntFromEditText(EditText editText) {
        if (editText.getText().toString().equals("")) {
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
            return 0;
        } else {
            return Integer.parseInt(editText.getText().toString());
        }
    }

    @Override
    public void onClick(View v){
    number1 = getIntFromEditText(num1);
    number2 = getIntFromEditText(num2);
    int id = v.getId();

    if (id == R.id.addBtn) {
        answer.setText("Answer: " + (number1 + number2));
    }
    if (id == R.id.subBtn) {
        answer.setText("Answer: " + (number1 - number2));
    }
    if (id == R.id.mulBtn) {
        answer.setText("Answer: " + (number1 * number2));
    }
    if (id == R.id.divBtn) {
        if (number2 != 0) {
            answer.setText("Answer: " + ((float) number1 / (float) number2));
            }
        }
    }
}