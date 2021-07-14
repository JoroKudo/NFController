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

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import ch.bbcag.NFController.Dagger2.NFControllerApplication;
import ch.bbcag.NFController.Database.RequestHandler;
import ch.bbcag.NFController.Features.FeatureActivator;
import ch.bbcag.NFController.MapActivities.FinalGeoFencingViewActivity;
import ch.bbcag.NFController.MapActivities.GeofencingActivity;


public class NFCRead extends NFCBase {

    private TextView listTitle;

    @Inject
    public AppDataManager appDataManager;
    @Inject
    public FeatureActivator featureActivator;
    @Inject
    Alerts alerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((NFControllerApplication) getApplicationContext()).appComponent.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_read);

        startFinalGeofencingViewIfNeeded();

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

    @SuppressLint({"SetTextI18n", "ServiceCast"})
    private void readFromNFC(Tag tag, Intent intent) {

        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();
                RequestHandler rh = new RequestHandler();
                NdefMessage ndefMessage = ndef.getNdefMessage();
                if (ndefMessage != null) {
                    Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                    if (messages != null) {
                        rh.SaveTagData(ndef, messages, ndef.getNdefMessage().getRecords());

                        NdefMessage[] ndefMessages = new NdefMessage[messages.length];
                        for (int i = 0; i < messages.length; i++) {
                            ndefMessages[i] = (NdefMessage) messages[i];
                        }
                        for (int i = 0; i <= ndefMessages.length; i++) {
                            for (int j = 0; j <= ndefMessages[i].getRecords().length; j++) {
                                NdefRecord record = ndefMessages[i].getRecords()[j];
                                byte[] payload = record.getPayload();

                                whichActionIsOnTag(payload);
                            }
                        }
                        ndef.close();
                    }
                } else {

                    listTitle.setText("Empty Tag1");
                }
            } else {
                listTitle.setText("Empty Tag2");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void whichActionIsOnTag(byte[] payload) throws UnsupportedEncodingException {
        //Encode Message manually because otherwise the bytes could be interpreted as chinese characters
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-8";
        String text = new String(payload, textEncoding);

        appDataManager.setSplitted(text.split(Const.SPACER));

        startFinalGeofencingViewIfNeeded();
        startGeofencingActivityIfNeeded();
        int subFeaturePosition = 0;

        featureActivator.activateFeature(this, subFeaturePosition, appDataManager.getSplitted());

        if (text.isEmpty()) {
            listTitle.setText(R.string.empty_tag);
        } else {

            listTitle.setText(text);
        }
    }

    private boolean isTheGeoFencingFeatureSelected() {
        return appDataManager.getSplitted()[0].equals("geofencing");
    }

    private boolean isGeofencingInformationRequested() {
        Intent intent = this.getIntent();
        String extraInformation = intent.getExtras().getString("NFCRead");
        return extraInformation.equals("From_NFCHome_For_Geofencing_Info");
    }

    private boolean isAnyInformationInSplittedAvailable() {
        return appDataManager.getSplitted()[0].equals("geofencing");
    }

    private void startFinalGeofencingView() {
        Intent geofencingInfoIntent = new Intent();
        geofencingInfoIntent.setClass(this, FinalGeoFencingViewActivity.class);
        geofencingInfoIntent.putExtra("FinalView", "From_NFCRead");
        startActivity(geofencingInfoIntent);
    }

    private void startGeofencingActivity() {
        Intent intent = new Intent(getApplicationContext(), GeofencingActivity.class);
        startActivity(intent);
    }

    private void startGeofencingActivityIfNeeded() {
        if (isTheGeoFencingFeatureSelected()) {
            startGeofencingActivity();
        }
    }

    private void startFinalGeofencingViewIfNeeded() {
        if (isGeofencingInformationRequested()) {
            try {
                if (isAnyInformationInSplittedAvailable()) {
                    startFinalGeofencingView();
                }
            } catch (NullPointerException e) {
                alerts.displayNoGeofenceActiveAlert(this);
            }
        }
    }
}