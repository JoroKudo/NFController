package ch.bbcag.NFController;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
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
import android.provider.Settings;

import android.widget.TextView;


import androidx.annotation.RequiresApi;

import com.bbcag.NFController.R;

import java.util.ArrayList;


public class NFCRead extends NFCBase {
    private TextView listTitle;
    private ArrayList<String> ndefRecordStringContent = new ArrayList<String>();
    private BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();

    WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    AudioManager audioManager = (AudioManager) getApplication().getSystemService(Context.AUDIO_SERVICE);
    NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);

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


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onNewIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            readFromNFC(tag, intent);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    private void readFromNFC(Tag tag, Intent intent) {

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
                                NdefRecord record = ndefMessages[0].getRecords()[0];
                                byte[] payload = record.getPayload();
                                String text = new String(payload);
                                switch (text) {

                                    case "blue1":
                                        bAdapter.enable();
                                        break;

                                    case "blue0":
                                        bAdapter.disable();
                                        break;

                                    case "wifi1":
                                        wifi.setWifiEnabled(true);
                                        break;

                                    case "1ifi0":
                                        wifi.setWifiEnabled(false);
                                        break;

                                    case "tone":
                                        checkIfNotificationPermissionIsGranted();
                                        setToTone();
                                        break;

                                    case "mute":
                                        checkIfNotificationPermissionIsGranted();
                                        setTomute();
                                        break;

                                    case "vibrate":
                                        checkIfNotificationPermissionIsGranted();
                                        setToVibrate();
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

    private void setTomute(){
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }
    private void setToTone() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }
    private void setToVibrate() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkIfNotificationPermissionIsGranted() {
        if (!notificationManager.isNotificationPolicyAccessGranted()) {
            startActivityForResult(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS), 0);
        }
    }
}
