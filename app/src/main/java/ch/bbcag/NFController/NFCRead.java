package ch.bbcag.NFController;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
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
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.bbcag.NFController.R;


public class NFCRead extends NFCBase {
    private TextView listTitle;
    private AudioManager audioManager;
    private NotificationManager notificationManager;

    private TextToSpeech tts;
    private final BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
    private WifiManager wifi;


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
        notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        audioManager = (AudioManager) getApplication().getSystemService(Context.AUDIO_SERVICE);
        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
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


                                //String[] spliced = text.split(",");
                                String[] splitted = text.split("\\s+");


                                switch (splitted[0]) {
                                    case "blue":
                                        if (splitted[1].equals("1")) {
                                            bAdapter.enable();
                                        } else if (splitted[1].equals("0")) {
                                            bAdapter.disable();
                                        }
                                        break;
                                    case "wifi":
                                        if (Build.VERSION.SDK_INT >= 29) {
                                            if (splitted[1].equals("1")) {
                                                wifi.setWifiEnabled(true);
                                            } else if (splitted[1].equals("0")) {
                                                wifi.setWifiEnabled(false);
                                            }
                                        } else {
                                            Toast.makeText(this, ("This function is not working on the newest version of android"), Toast.LENGTH_SHORT).show();

                                        }

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

                                    case "vol":
                                        if (splitted[1].equals("+")) {
                                            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);

                                        } else if (splitted[1].equals("-")) {
                                            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
                                        }
                                        break;


                                    case "tts":
                                        tts = new TextToSpeech(this, status -> {
                                            if (status == TextToSpeech.SUCCESS) {
                                                String textToSay = splitted[1];
                                                tts.speak(textToSay, TextToSpeech.QUEUE_ADD, null);
                                            }
                                        });
                                        tts.speak(splitted[1], TextToSpeech.QUEUE_ADD, null);
                                        break;
                                    case "open":
                                        String packageName = splitted[1];

                                        Intent launch = getPackageManager().getLaunchIntentForPackage(packageName);

                                        if (launch == null) {
                                            try {
                                                // if play store installed, open play store, else open browser
                                                launch = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
                                            } catch (Exception e) {
                                                launch = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                                            }
                                        }
                                        startActivity(launch);
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

    private void setTomute() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    private void setToTone() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

    private void setToVibrate() {
        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }

    private void checkIfNotificationPermissionIsGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!notificationManager.isNotificationPolicyAccessGranted()) {
                startActivityForResult(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS), 0);
            } else {
                Toast.makeText(this, ("This function is not working on this version of android"), Toast.LENGTH_SHORT).show();

            }
        }
    }
}
