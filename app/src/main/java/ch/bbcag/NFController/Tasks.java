package ch.bbcag.NFController;


import android.app.Activity;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;


import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.speech.tts.TextToSpeech;


public class Tasks {

    private final Activity activity;
    private final NotificationManager notificationManager;
    private final WifiManager wifi;
    private final AudioManager audioManager;


    private TextToSpeech tts;
    private final BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();


    public Tasks(Activity activity, NotificationManager notificationManager, WifiManager wifi, AudioManager audioManager) {

        this.activity = activity;
        this.notificationManager = notificationManager;
        this.wifi = wifi;
        this.audioManager = audioManager;
    }



}

