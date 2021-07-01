package ch.bbcag.NFController;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class NFCTest extends AppCompatActivity {

    private static final String ACCESS_BACKGROUND_LOCATION = Manifest.permission.ACCESS_BACKGROUND_LOCATION;
    private static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    private final String[] permissions = new String[]{ACCESS_BACKGROUND_LOCATION, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMultiplePermissions();
    }

    public void requestMultiplePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //LUEGT EB D PERMISSIONS SCHO ERTEILT WORDE SIND
            if ((ContextCompat.checkSelfPermission(this, ACCESS_BACKGROUND_LOCATION) +
                    ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) +
                    ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION))
                    != PackageManager.PERMISSION_GRANTED) {
                //LUEGT EB D PERMISSION EN REQUEST IM UI BRUUCHT
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_BACKGROUND_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION)) {
                    //ZEIGT S UI FÜR DE REQUEST AH
                    Snackbar.make(this.findViewById(android.R.id.content),
                            "Please change your Location settings for the geofencing feature",
                            Snackbar.LENGTH_INDEFINITE).setAction("Continue",
                            v -> requestPermissions(
                                    permissions, PERMISSIONS_MULTIPLE_REQUEST)).show();

                }
            } else {
                Toast.makeText(this, ("you have already have these permissions"), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


}
