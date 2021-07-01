package ch.bbcag.NFController;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;


public class SplashActivity extends Activity {


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


}
