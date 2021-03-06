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
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class NFCWrite extends NFCBase {

    private EditText evTagMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_write);
        initViews();
    }

    @Override
    protected void initViews() {
        evTagMessage = findViewById(R.id.evTagMessage);
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

            String messageToWrite = evTagMessage.getText().toString();

            if (!TextUtils.equals(messageToWrite, "null") && !TextUtils.isEmpty(messageToWrite)) {
                NdefRecord record = NdefRecord.createMime(messageToWrite, messageToWrite.getBytes());
                NdefMessage message = new NdefMessage(new NdefRecord[]{record});

                if (writeTag(tag, message)) {
                    Toast.makeText(this, (getString(R.string.Tag_write_success)), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, (getString(R.string.Tag_write_error)), Toast.LENGTH_SHORT).show();
                }
            } else {
                evTagMessage.setError(getApplicationContext().getResources().getString(R.string.please_enter_text_error));
            }
        }
    }
}
