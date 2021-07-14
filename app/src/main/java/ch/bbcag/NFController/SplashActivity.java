package ch.bbcag.NFController;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {

    final Context context = SplashActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent i = new Intent(context, NfcHome.class);
            startActivity(i);
            finish();
        }, 2000);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);

        super.onNewIntent(intent);
        intent.setClass(this, NFCRead.class);
        startActivity(intent);
        finish();
    }
}
