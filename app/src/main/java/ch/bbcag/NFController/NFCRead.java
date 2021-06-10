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

import android.os.Bundle;
import android.os.Parcelable;

import android.speech.tts.TextToSpeech;
import android.widget.TextView;

import com.bbcag.NFController.R;


public class NFCRead extends NFCBase {
    private TextView listTitle;
private Tasks tasks;

    private TextToSpeech tts;
    private final BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();




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


    @SuppressLint("SetTextI18n")
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


}
