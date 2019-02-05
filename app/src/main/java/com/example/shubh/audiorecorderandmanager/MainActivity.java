package com.example.shubh.audiorecorderandmanager;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    Button Record,Stop,Play,Splay;
    boolean recordStatus=true;

    String AudioSavedPathInDevice=null;
    Random random ;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    MediaRecorder md;
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButton();
        random=new Random();
        if(CheckPermission()){
           Record.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(CheckPermission()){
                       AudioSavedPathInDevice=Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+CreateRand(5)+"AudioRecording.3gp";

                       MediaRecorderReady();
                       try {
                           md.prepare();
                           md.start();

                       } catch (IllegalStateException | IOException e) {
                           e.printStackTrace();
                       }
                   }else{requestPermissions();}
                   Play.setEnabled(false);
                   Stop.setEnabled(true);
                   Toast.makeText(MainActivity.this, "Recording started",Toast.LENGTH_SHORT).show();
               }
           });
         Stop.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 md.stop();


                 Stop.setEnabled(false);
                 Play.setEnabled(true);
                 Toast.makeText(MainActivity.this, "Recording Stopped",Toast.LENGTH_SHORT).show();

             }
         });
        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer=new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavedPathInDevice);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(MainActivity.this, "Playing",
                        Toast.LENGTH_LONG).show();

            }
        });
        Splay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Stop.setEnabled(false);
                    Record.setEnabled(true);
                    Play.setEnabled(false);
                    Splay.setEnabled(true);

                    if(mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        MediaRecorderReady();
                    }
                }
        });
            
        }else{requestPermissions();}











    }

    private void MediaRecorderReady() {
        md=new MediaRecorder();
        md.setAudioSource(MediaRecorder.AudioSource.MIC);
        md.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        md.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        md.setOutputFile(AudioSavedPathInDevice);
    }

    private String CreateRand(int i) {
        StringBuilder stringBuilder = new StringBuilder( i );
        int j = 0 ;
        while(j < i ) {
            stringBuilder.append(RandomAudioFileName.charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    private boolean CheckPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(MainActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
    private void setButton() {
        Splay=findViewById(R.id.Stop_playing);
        Record=findViewById(R.id.record);
        Stop=findViewById(R.id.stop);
        Play=findViewById(R.id.Play);

        Record.setEnabled(true);
        Stop.setEnabled(false);
        Play.setEnabled(false);
    }
    private class mOnClick implements View.OnClickListener {


        @Override
        public void onClick(View v) {


        }
    }
}
