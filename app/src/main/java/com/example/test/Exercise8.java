package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Exercise8 extends AppCompatActivity {
    private SeekBar seekBar;
    private Switch visibleSwitch;
    private ImageView imageView;
    private Button rotateBtn;
    private ImageView circleImage;
    private ImageView cresentImage;
    private float dX,dY;
    private Button animateBtn;
    private Button incrBtnincr;
    private Button nxtBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise8);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        float screenWidth = getResources().getDisplayMetrics().widthPixels;
        nxtBtn = findViewById(R.id.nxtBtn);
        seekBar = findViewById(R.id.seekBar);
        animateBtn = findViewById(R.id.animateBtn);
        circleImage = findViewById(R.id.circleImage);
        cresentImage = findViewById(R.id.cresentImage);
        visibleSwitch = findViewById(R.id.visibleSwitch);
        imageView = findViewById(R.id.imageView);
        rotateBtn = findViewById(R.id.rotateBtn);
        incrBtnincr = findViewById(R.id.BtnIncrBtn);

        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise8.this, Exercise9.class);
                startActivity(intent);
            }
        });

        incrBtnincr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation scaleUp = AnimationUtils.loadAnimation(Exercise8.this,R.anim.scale_up);
                Animation scaleDown = AnimationUtils.loadAnimation(Exercise8.this, R.anim.scale_down);
                scaleUp.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        incrBtnincr.startAnimation(scaleDown);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                incrBtnincr.startAnimation(scaleUp);
            }
        });
        rotateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateImage(imageView);
            }
        });

        animateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateImage(imageView);
            }
        });
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imageView.startAnimation(fadeIn);


        Animation animation = new TranslateAnimation(0,screenWidth,0, 0);
        animation.setDuration(2000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            Animation reverseAnimation = new TranslateAnimation(-imageView.getWidth() ,screenWidth-imageView.getWidth()-imageView.getLeft(), 0, 0);
            reverseAnimation.setDuration(2000);
            imageView.startAnimation(reverseAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(animation);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float alphaValue = (float)progress/255;
                imageView.setAlpha(alphaValue);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        visibleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                imageView.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.INVISIBLE);
            }
        });
        setDragListener(circleImage);
        setDragListener(cresentImage);
    }
    public void rotateImage(View view) {
        RotateAnimation rotate = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(2000);
        rotate.setRepeatCount(0);
        view.startAnimation(rotate);
    }

    public void animateImage(View view) {
        float screenWidth = getResources().getDisplayMetrics().widthPixels;
        Animation reverseAnimation = new TranslateAnimation(-view.getWidth() ,screenWidth-view.getWidth()-view.getLeft(), 0, 0);
        reverseAnimation.setDuration(2000);
        view.startAnimation(reverseAnimation);
        Animation animation = new TranslateAnimation(0,screenWidth,0,0);
        animation.setDuration(2000);
        view.startAnimation(animation);
    }

    public void setDragListener(ImageView imageView){
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }
}