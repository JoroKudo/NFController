package ch.bbcag.NFController;


import android.app.AlertDialog;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.widget.Toast;


public class NFCWipe extends NFCBase {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_wipe);
        initViews();


    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        if (tag != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.Wipe_Tag_Alert)
                    .setPositiveButton(R.string.str_Wipe_with_nfc, (dialog, id) -> {
                        try {
                            Ndef ndef = Ndef.get(tag);
                            if (ndef != null) {
                                ndef.connect();

                                ndef.writeNdefMessage(new NdefMessage(new NdefRecord(NdefRecord.TNF_EMPTY, null, null, null)));
                                Toast.makeText(this, (getString(R.string.Tag_wipe_success)), Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        } catch (Exception e) {
                            Toast.makeText(this, (getString(R.string.Tag_write_error)), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(R.string.cancel, (dialog, id) -> finish());
            builder.show();


        }
    }
}


