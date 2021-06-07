package ch.bbcag.NFController;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;

import android.os.Bundle;

import android.widget.Toast;

import com.bbcag.NFController.R;


public class NFCWipe extends Activity {


    private NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_wipe);
        initViews();


    }

    private void initViews() {

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected, tagDetected, ndefDetected};

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        if (tag != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.Wipetag_alert)
                    .setPositiveButton(R.string.Wipe_Tag, (dialog, id) -> {
                        try {
                            Ndef ndef = Ndef.get(tag);
                            if (ndef != null) {
                                ndef.connect();

                                ndef.writeNdefMessage(new NdefMessage(new NdefRecord(NdefRecord.TNF_EMPTY, null, null, null)));
                                Toast.makeText(this, (getString(R.string.message_wipe_success)), Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        } catch (Exception e) {
                            Toast.makeText(this, (getString(R.string.message_write_error)), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(R.string.cancel, (dialog, id) -> {
                        finish();
                    });
            builder.show();




        }
    }
}


