package ch.bbcag.NFController;


import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;

public class NFCommands extends NFCBase implements View.OnClickListener {


    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private RelativeLayout rl3;
    private RelativeLayout rl4;
    private RelativeLayout rl5;
    private RelativeLayout rl6;
    private RelativeLayout rl7;
    private RelativeLayout rl8;
    private RelativeLayout rl9;
    private String task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_ommands);
        initViews();


        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        rl4.setOnClickListener(this);
        rl5.setOnClickListener(this);
        rl6.setOnClickListener(this);
        rl7.setOnClickListener(this);
        rl8.setOnClickListener(this);
        rl9.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rlbluetooth:
                task ="blue";
                break;

            case R.id.rlwifi:
                task ="wifi";
                break;
            case R.id.rltone:
                task ="tone";
                break;
            case R.id.rlmute:
                task ="mute";
                break;
            case R.id.rlvibrate:
                task ="vibrate";
                break;
            case R.id.rlvol:
                task ="vol";
                break;
            case R.id.rltts:
                task ="tts lol";
                break;
            case R.id.rlopenapp:
                task ="open";
                break;
            case R.id.rlalarm:
                task ="alarm";
                break;
            case R.id.rltimer:
                task ="timer";
                break;


        }
    }

    @Override
    protected void initViews() {

        rl1 = findViewById(R.id.rlbluetooth);
        rl2 = findViewById(R.id.rlwifi);
        rl3 = findViewById(R.id.rltone);
        rl4 = findViewById(R.id.rlmute);
        rl5 = findViewById(R.id.rlvibrate);
        rl6 = findViewById(R.id.rlvol);
        rl7 = findViewById(R.id.rltts);
        rl8 = findViewById(R.id.rlopenapp);
        rl9 = findViewById(R.id.rlalarm);
        this.mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    public boolean writeTag(Tag tag, NdefMessage message) {
        int size = message.toByteArray().length;
        try {
            Ndef ndef = Ndef.get(tag);

            if (ndef != null) {
                ndef.connect();
                if (!ndef.isWritable() || ndef.getMaxSize() < size) {
                    return false;
                }

                ndef.writeNdefMessage(message);


                return true;
            } else {
                NdefFormatable format = NdefFormatable.get(tag);
                if (format != null) {
                    try {
                        format.connect();
                        format.format(message);
                        return true;
                    } catch (IOException e) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        if (tag != null) {

            String messageToWrite = task;

            if (!TextUtils.equals(messageToWrite, "null") && !TextUtils.isEmpty(messageToWrite)) {
                NdefRecord record = NdefRecord.createMime(messageToWrite, messageToWrite.getBytes());
                NdefMessage message = new NdefMessage(new NdefRecord[]{record});


                if (writeTag(tag, message)) {
                    Toast.makeText(this, (getString(R.string.message_write_success)), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, (getString(R.string.message_write_error)), Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

}
