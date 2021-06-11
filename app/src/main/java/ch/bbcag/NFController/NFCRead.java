package ch.bbcag.NFController;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bbcag.NFController.R;
import com.google.android.material.snackbar.Snackbar;

import java.security.Permission;


public class NFCRead extends NFCBase {
    private static final String ACCESS_BACKGROUND_LOCATION = Manifest.permission.ACCESS_BACKGROUND_LOCATION;
    private static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private TextView listTitle;
private Tasks tasks;

    private TextToSpeech tts;
    private final BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();

    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_read);
        initViews();

    }

    @Override
    protected void initViews() {
        listTitle = findViewById(R.id.listTitle);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            readFromNFC(tag, intent);
        }
    }


    @SuppressLint({"SetTextI18n", "ServiceCast"})
    private void readFromNFC(Tag tag, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        AudioManager audioManager = (AudioManager) getApplication().getSystemService(Context.AUDIO_SERVICE);
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        tasks = new Tasks(this,notificationManager,wifi,audioManager);
        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();
                NdefMessage ndefMessage = ndef.getNdefMessage();

                if (ndefMessage != null) {
                    Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                    if (messages != null) {
                        NdefMessage[] ndefMessages = new NdefMessage[messages.length];
                        for (int i = 0; i < messages.length; i++) {
                            ndefMessages[i] = (NdefMessage) messages[i];
                        }
                        for (int i = 0; i <= ndefMessages.length; i++) {
                            for (int j = 0; j <= ndefMessages[i].getRecords().length; j++) {
                                NdefRecord record = ndefMessages[i].getRecords()[j];
                                byte[] payload = record.getPayload();
                                String text = new String(payload);


                                String[] splitted = text.split("\\s+");

                                switch (splitted[0]) {
                                    case "blue":
                                        tasks.bluetooth(splitted[1]);
                                        break;
                                    case "wifi":
                                        tasks.wifi(splitted[1]);
                                        break;
                                    case "tone":
                                        tasks.setToTone();
                                        break;
                                    case "mute":
                                        tasks.setTomute();
                                        break;
                                    case "vibrate":
                                        tasks.setToVibrate();
                                        break;
                                    case "vol":
                                        tasks.changeVolume(splitted[1]);
                                        break;
                                    case "tts":
                                        tasks.TextToSpeech(splitted[1]);
                                        break;
                                    case "open":
                                        tasks.openApp(splitted[1]);
                                        break;
                                    case "alarm":
                                        tasks.setAlarm(Integer.parseInt(splitted[1]),Integer.parseInt(splitted[2]));
                                        break;
                                    case "timer":
                                       tasks.setTimer(Integer.parseInt(splitted[1]));
                                        break;



                                }
                                if (text.isEmpty()) {
                                    listTitle.setText("Empty Tag");
                                } else {
                                    listTitle.setText(text);
                                }
                            }
                        }
                        ndef.close();
                    }
                } else {
                    listTitle.setText("Empty Tag");
                }
            } else {
                listTitle.setText("Empty Tag");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void requestMultiplePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, ACCESS_BACKGROUND_LOCATION) +
                    ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) +
                    ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_BACKGROUND_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION)) {

                    Snackbar.make(this.findViewById(android.R.id.content),
                            "Please Grant Permissions to Access to background Location",
                            Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                            v -> requestPermissions(
                                    new String[]{ACCESS_BACKGROUND_LOCATION, ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION}, PERMISSIONS_MULTIPLE_REQUEST)).show();
                } else {
                    requestPermissions(
                            new String[]{ACCESS_BACKGROUND_LOCATION,ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION},
                            PERMISSIONS_MULTIPLE_REQUEST);
                }
            } else {
                Log.e("test", "HALLLLLLLLLLLLLLLLLLLLLLLLLLLLLLO");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                case PERMISSIONS_MULTIPLE_REQUEST:
                    if (grantResults.length > 0) {
                        boolean backgroundPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                        boolean fineLocationPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                        boolean courseLocationPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                        if (backgroundPermission && fineLocationPermission && courseLocationPermission) {
                            // write your logic here
                        } else {
                            Snackbar.make(this.findViewById(android.R.id.content),
                                    "Please Grant Permissions to Access to background Location",
                                    Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                    v -> requestPermissions(
                                            new String[]{ACCESS_BACKGROUND_LOCATION, ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION}, PERMISSIONS_MULTIPLE_REQUEST)).show();
                        }
                    }
                    break;
            }
        }
    }


}
