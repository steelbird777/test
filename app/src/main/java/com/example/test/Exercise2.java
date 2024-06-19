package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Exercise2 extends AppCompatActivity {
    private TextView nameText;
    private TextView registernoText;
    private TextView deptText;
    private EditText editName;
    private EditText editRegisterno;
    private Spinner spinner;
    private Button nextbtn;
    private Button submitbtn;
    private String selectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editName = findViewById(R.id.name);
        editRegisterno = findViewById(R.id.registerno);
        nameText = findViewById(R.id.nameText);
        registernoText = findViewById(R.id.registernoText);
        deptText = findViewById(R.id.deptText);
        spinner = findViewById(R.id.spinner);
        nextbtn = findViewById(R.id.nextBtn);
        submitbtn = findViewById(R.id.submitBtn);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               displayText();
            }
        });
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise2.this, Exercise3.class);
                startActivity(intent);
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void displayText() {
        String name = editName.getText().toString();
        nameText.setText(name);
        String registerno = editRegisterno.getText().toString();
        registernoText.setText(registerno);
        deptText.setText(selectedItem);
    }
}