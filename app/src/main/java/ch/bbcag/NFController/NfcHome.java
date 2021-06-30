package ch.bbcag.NFController;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.RelativeLayout;


import androidx.appcompat.app.AppCompatActivity;


public class NfcHome extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
                intent = new Intent(this, NFCWipe.class);
                startActivity(intent);
                break;
            case R.id.rlTestWithNFC:
                intent = new Intent(this, NFCTest.class);
                startActivity(intent);
                break;
            case R.id.rladdtask:
                intent = new Intent(this, taskadder.class);
                startActivity(intent);
                break;


        }
    }



}