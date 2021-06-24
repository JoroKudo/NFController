package ch.bbcag.NFController;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.NotificationManager;
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
import android.util.Log;
import android.widget.TextView;

import ch.bbcag.NFController.Features.ApplicationOpener;
import ch.bbcag.NFController.Features.Bluetooth;
import ch.bbcag.NFController.Features.Clock;
import ch.bbcag.NFController.Features.Flashlight;
import ch.bbcag.NFController.Features.TextToSpeechConverter;
import ch.bbcag.NFController.Features.Volume;
import ch.bbcag.NFController.Features.Website;
import ch.bbcag.NFController.Features.WhatsappTexter;
import ch.bbcag.NFController.Features.Wifi;


public class NFCRead extends NFCBase {

    private TextView listTitle;


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
        intent.setType("tag/nc");
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {

            readFromNFC(tag, intent);
        }
    }

    @SuppressLint({"SetTextI18n", "ServiceCast"})
    private void readFromNFC(Tag tag, Intent intent) {


        NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        AudioManager audioManager = (AudioManager) getApplication().getSystemService(Context.AUDIO_SERVICE);
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

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

                                whichActionIsOnTag(payload, record, audioManager, notificationManager, wifiManager);
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

    private void whichActionIsOnTag(byte[] payload,NdefRecord record,  AudioManager audioManager, NotificationManager notificationManager, WifiManager wifiManager) {
        String text = new String(payload);
        byte[] type = record.getType();
        String mimetype = new String(type);
        String[] splitted = text.split(Const.SPACER);

        int subFeaturePosition;

        if (splitted[0].equals("geofencing")){
            subFeaturePosition = 7;
            //TODO Implement what should be done if geofencing is selected

        }else
            subFeaturePosition = 0;

        switch (splitted[subFeaturePosition]) {
            case "blue":
                Bluetooth bluetooth = new Bluetooth();
                bluetooth.toggleBluetooth(splitted[subFeaturePosition + 1]);
                break;
            case "wifi":
                Wifi wifiClass = new Wifi(wifiManager, this);
                wifiClass.toggleWifi(splitted[subFeaturePosition + 1]);
                break;
            case "tone":
                Volume toneVolume = new Volume(this, audioManager, notificationManager);
                toneVolume.setToTone();
                break;
            case "mute":
                Volume muteVolume = new Volume(this, audioManager, notificationManager);
                muteVolume.setTomute();
                break;
            case "vibrate":
                Volume vibrate = new Volume(this, audioManager, notificationManager);
                vibrate.setToVibrate();
                break;
            case "vol":
                Volume volume = new Volume(this, audioManager, notificationManager);
                volume.changeVolume(splitted[subFeaturePosition + 1]);
                break;
            case "tts":
                TextToSpeechConverter textToSpeechConverter = new TextToSpeechConverter(this);
                textToSpeechConverter.TextToSpeech(splitted[1]);
                break;
            case "open":
                ApplicationOpener applicationOpener = new ApplicationOpener(this);
                applicationOpener.openApp(splitted[subFeaturePosition + 1]);
                break;
            case "alarm":
                Clock alarmClock = new Clock(this);
                alarmClock.setAlarm(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]), splitted[3]);
                break;
            case "timer":
                Clock timerClock = new Clock(this);
                timerClock.setTimer(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]), splitted[4]);
                break;
            case "flash":
                Flashlight flashlight = new Flashlight(this);
                flashlight.flash(splitted[subFeaturePosition + 1]);
                break;
            case "send":
                WhatsappTexter whatsappTexter = new WhatsappTexter(this);
                whatsappTexter.sendWhatsapp(splitted[1], splitted[2]);
                break;
            case "web":
                Website website = new Website(this);
                website.opensite(splitted[subFeaturePosition + 1]);
                break;

        }
        if (text.isEmpty()) {
            listTitle.setText("Empty Tag");
        } else {

            listTitle.setText(text);
            Log.e("MIMETYPE", mimetype);
        }
    }


}
