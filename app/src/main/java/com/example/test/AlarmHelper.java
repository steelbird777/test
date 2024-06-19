package com.example.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.widget.Toast;

public class AlarmHelper extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent){
        Toast.makeText(context, "Alarm, WakeUp!!!", Toast.LENGTH_SHORT).show();

        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
        mediaPlayer.start();
    }
}
