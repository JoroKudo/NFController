package ch.bbcag.NFController;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import ch.bbcag.NFController.Dagger2.NFControllerApplication;
import ch.bbcag.NFController.MapActivities.GeofencingInfo;


public class NfcHome extends AppCompatActivity implements View.OnClickListener {

    @Inject
    AppDataManager appDataManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ((NFControllerApplication)getApplicationContext()).appComponent.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_home);
        RelativeLayout rlRead = findViewById(R.id.rlReadNFCTAG);
        RelativeLayout rlWrite = findViewById(R.id.rlWriteWithNFC);
        RelativeLayout rladd = findViewById(R.id.rladdtask);
        RelativeLayout rlWipe = findViewById(R.id.rlWipeWithNFC);
        RelativeLayout rlTest = findViewById(R.id.rlTestWithNFC);
        rlRead.setOnClickListener(this);
        rladd.setOnClickListener(this);
        rlWrite.setOnClickListener(this);
        rlWipe.setOnClickListener(this);
        rlTest.setOnClickListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rlReadNFCTAG:
                intent = new Intent(this, NFCRead.class);
                startActivity(intent);
                break;

            case R.id.rlWriteWithNFC:

                intent = new Intent(this, NFCWrite.class);
                startActivity(intent);
                break;
            case R.id.rlWipeWithNFC:
                intent = new Intent();
                intent.setClass(this, NFCWipe.class);

                startActivity(intent);
                break;
            case R.id.rlTestWithNFC:

                intent = new Intent();
                intent.setClass(this, NFCRead.class);
                intent.putExtra("NFCRead", "From_NFCHome");
                startActivity(intent);
                break;
            case R.id.rladdtask:
                intent = new Intent(this, TaskAdder.class);
                startActivity(intent);
                break;


        }
    }


}