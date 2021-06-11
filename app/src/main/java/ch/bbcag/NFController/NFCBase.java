package ch.bbcag.NFController;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;

import androidx.appcompat.app.AppCompatActivity;




public class NFCBase extends AppCompatActivity {
    protected NfcAdapter mNfcAdapter;

    @Override
    protected void onResume() {
        if (!mNfcAdapter.isEnabled()) {
            showNfcSettingsDialog();

        }
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

    protected void initViews() {

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    public void showNfcSettingsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("NFC is disabled")
                .setMessage("You must enable NFC to use this app.")

                .setPositiveButton(R.string.positive, (dialog, which) -> this.startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS)))
                .setNegativeButton(R.string.negative, (dialog, which) -> this.finish())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
