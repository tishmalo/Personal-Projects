package com.example.savior.Services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class RecordService extends Service {


    private Timer timer;
    MediaPlayer player;


    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String fileName = null;
    private static String path = null;


    private MediaRecorder recorder = null;

    private static final int REQUEST_CODE = 1;



    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {




        recorder= new MediaRecorder();



        doSomethingRepeatedly();



        return START_STICKY;
    }

    private void doSomethingRepeatedly() {


        Timer timer = new Timer();
        timer.scheduleAtFixedRate( new TimerTask() {
            public void run() {

                try{


                   // player=MediaPlayer.create(getApplicationContext(),  Settings.System.DEFAULT_RINGTONE_URI);

                    //player.setLooping(true);
                   // player.start();
                    fileName = getExternalCacheDir().getAbsolutePath();
                    fileName += "audiorecordtest.3gp";

                    recorder= new MediaRecorder();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setOutputFile(fileName);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);


                    try {
                        recorder.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    recorder.start();

                    System.out.print("recording");

                       stop();
                    playing();

                }
                catch (Exception e) {
                    // TODO: handle exception
                }

            }
        }, 3, 10000);
    }

    private void stop() {
       // player=MediaPlayer.create(getApplicationContext(),  Settings.System.DEFAULT_RINGTONE_URI);
       // player.stop();



        recorder.stop();
        recorder.release();
        recorder=null;



    }

    private void playing() {
        fileName = getExternalCacheDir().getAbsolutePath();
        fileName += "audiorecordtest.3gp";
        player= new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
