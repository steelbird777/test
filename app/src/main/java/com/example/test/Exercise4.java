package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Exercise4 extends AppCompatActivity {

    private FrameLayout drawingArea;
    private String[] shapes = {"Circle", "Rectangle", "Square", "Line", "Triangle"};
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise4);

        Spinner shapeSpinner = findViewById(R.id.shapeSpinner);
        drawingArea = findViewById(R.id.shapeView);
        nextBtn = findViewById(R.id.nextBtn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise4.this, Exercise5.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, shapes);
        shapeSpinner.setAdapter(adapter);

        shapeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                drawShape(shapes[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void drawShape(String shape) {
        drawingArea.removeAllViews();
        CustomShapeView shapeView = new CustomShapeView(this, shape);
        drawingArea.addView(shapeView);
    }

    private static class CustomShapeView extends View {
        private final String shape;

        CustomShapeView(Context context, String shape) {
            super(context);
            this.shape = shape;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);

            switch (shape) {
                case "Circle":
                    canvas.drawCircle(getWidth() / 2, getHeight() / 2, 300, paint);
                    break;
                case "Rectangle":
                    canvas.drawRect(50, 50,700, 500, paint);
                    break;
                case "Square":
                    canvas.drawRect(50, 50, 500, 500, paint);
                    break;
                case "Line":
                    Path path = new Path();
                    path.moveTo(50, 50);
                    path.lineTo(getWidth() - 50, getHeight() - 50);
                    canvas.drawPath(path, paint);
                    break;
                case "Triangle":
                    Path trianglePath = new Path();
                    trianglePath.moveTo(getWidth() / 2f, 50);
                    trianglePath.lineTo(50, getHeight() - 50);
                    trianglePath.lineTo(getWidth() - 50, getHeight() - 50);
                    trianglePath.close();
                    canvas.drawPath(trianglePath, paint);
                    break;
            }
        }
    }
}
