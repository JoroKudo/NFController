package ch.bbcag.NFController;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bbcag.NFController.R;

import ch.bbcag.NFController.NFCInfo.NFCInfo;

public class NfcHome extends Activity implements View.OnClickListener {

    private RelativeLayout rlRead;
    private RelativeLayout rlWrite;
    private RelativeLayout rlWipe;
    private ImageView Detailscan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_home);
        initViews();
        rlRead.setOnClickListener(this);
        rlWrite.setOnClickListener(this);
        rlWipe.setOnClickListener(this);
        Detailscan.setOnClickListener(this);

    }

    private void initViews() {

        rlRead = findViewById(R.id.rlReadNFCTAG);
        rlWrite = findViewById(R.id.rlWriteWithNFC);
        rlWipe = findViewById(R.id.rlWipeWithNFC);
        Detailscan = findViewById(R.id.DetailScan);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.rlReadNFCTAG:
                intent = new Intent(this, NFCRead.class);
                this.startActivity(intent);
                break;

            case R.id.rlWriteWithNFC:
                intent = new Intent(this, NFCWrite.class);
                this.startActivity(intent);
                break;
            case R.id.rlWipeWithNFC:
                intent = new Intent(this, NFCWipe.class);
                this.startActivity(intent);
                break;

            case R.id.DetailScan:
                intent = new Intent(this, NFCInfo.class);
                this.startActivity(intent);
                break;


        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
