package ch.bbcag.NFController;


import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;

public class TaskWriter extends NFCBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_writer);
        initViews();
    }

    @Override
    protected void initViews() {
        this.mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        if (tag != null) {

            NdefRecord[] rec = new NdefRecord[Const.taskcontainer.size()];
            NdefRecord[] rec2 = new NdefRecord[Const.taskcontainer.size() + 1];

            for (int i = 0; i < Const.taskcontainer.size(); i++) {
                String messageToWrite = Const.taskcontainer.get(i)[0]
                        + Const.SPACER + Const.taskcontainer.get(i)[1]
                        + Const.SPACER + Const.taskcontainer.get(i)[2]
                        + Const.SPACER + Const.taskcontainer.get(i)[3]
                        + Const.SPACER + Const.taskcontainer.get(i)[4]
                        + Const.SPACER + Const.taskcontainer.get(i)[5]
                        + Const.SPACER + Const.taskcontainer.get(i)[6]
                        + Const.SPACER + Const.taskcontainer.get(i)[7]
                        + Const.SPACER + Const.taskcontainer.get(i)[8]
                        + Const.SPACER + Const.taskcontainer.get(i)[9];
                rec2[i] = NdefRecord.createMime("my/tag", messageToWrite.getBytes());
                rec2[i + 1] = NdefRecord.createMime("my/tag", "wow" .getBytes());
                NdefRecord nder = new NdefRecord((short) 2, "my/tag" .getBytes(), rec2[i].getId(), messageToWrite.getBytes());
                //NdefRecord nder2 = new NdefRecord((short) 4, "my/tag" .getBytes(), rec2[i + 1].getId(), "wow" .getBytes());
                rec[i] = nder;
                //rec[i + 1] = nder2;
            }
            NdefMessage message = new NdefMessage(rec);

            if (writeTag(tag, message)) {
                Toast.makeText(this, (getString(R.string.Tag_write_success)), Toast.LENGTH_SHORT).show();
                Const.taskcontainer.clear();
                Intent homeIntent = new Intent(this, NfcHome.class);
                startActivity(homeIntent);
                finish();
            } else {
                Toast.makeText(this, (getString(R.string.Tag_write_error)), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean writeTag(Tag tag, NdefMessage message) {
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
            e.printStackTrace();
            return false;
        }
    }
}
