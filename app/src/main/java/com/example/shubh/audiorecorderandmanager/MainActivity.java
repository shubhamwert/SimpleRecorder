package com.example.shubh.audiorecorderandmanager;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button Record,Stop,Play;
    String AudioSavedPathInDevice=null;
    MediaRecorder md;
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Record=findViewById(R.id.record);
        Stop=findViewById(R.id.stop);
        Play=findViewById(R.id.Play);

        Record.setEnabled(true);
        Stop.setEnabled(false);
        Play.setEnabled(false);







    }
}
