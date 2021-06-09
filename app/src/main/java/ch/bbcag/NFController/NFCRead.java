package ch.bbcag.NFController;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;


import com.bbcag.NFController.R;


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
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            readFromNFC(tag, intent);
        }
    }


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
                        NdefRecord record = ndefMessages[0].getRecords()[0];

                        byte[] payload = record.getPayload();
                        String text = new String(payload);

                        if (text.isEmpty()) {
                            listTitle.setText("Empty Tag");
                        } else {
                            listTitle.setText(text);
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
