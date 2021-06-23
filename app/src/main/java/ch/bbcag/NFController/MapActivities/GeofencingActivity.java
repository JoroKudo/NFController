package ch.bbcag.NFController.MapActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

public class GeofencingActivity extends AppCompatActivity {

    private static final String myId = "10";
    private static final int LATITUDE = 10;
    private static final int LONGITUDE = 10;
    private static final int RADIUS = 10;
    private static final int EXPIRATION_TIME_IN_MILISECONDS = 10;
    private static final String ACCESS_BACKGROUND_LOCATION = Manifest.permission.ACCESS_BACKGROUND_LOCATION;
    private static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    private final String[] permissions = new String[]{ACCESS_BACKGROUND_LOCATION, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};

    List<Geofence> geofenceList = Collections.emptyList();
    private PendingIntent geofencePendingIntent;
    GeofencingClient geofencingClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        geofencingClient = new GeofencingClient(this);

        startGeoFenceMonitoring();

/*        Button button = findViewById(R.id.buttonMaps);
        button.setOnClickListener(v -> {
            startActivity(new Intent(this, MapsActivity.class));
        });*/
    }


    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofencingBroadcastReceiver.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }

    @SuppressLint("MissingPermission")
    private void addGeofence() {
        if (checkForMyRequiredLocationPermissions()) {
            geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                    .addOnSuccessListener(this, aVoid -> Toast.makeText(getApplicationContext(), ("Geofence added"), Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(this, e -> Toast.makeText(getApplicationContext(), ("failed to add Geofence"), Toast.LENGTH_SHORT).show());
        }

    }


    private void removeGeofence() {
        geofencingClient.removeGeofences(getGeofencePendingIntent())
                .addOnSuccessListener(this, aVoid -> Toast.makeText(getApplicationContext(), ("Geofence removed successfully"), Toast.LENGTH_SHORT).show())
                .addOnFailureListener(this, e -> Toast.makeText(getApplicationContext(), ("failed to remove Geofence"), Toast.LENGTH_SHORT).show());
    }


    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    private void createGeofence(String id, int latitude, int longitude, int radius) {
        geofenceList.add(new Geofence.Builder()
                // Set the request ID of the geofence. This is a string to identify this
                // geofence.
                .setRequestId(id)
                .setCircularRegion(
                        latitude,
                        longitude,
                        radius
                )
                .setExpirationDuration(EXPIRATION_TIME_IN_MILISECONDS)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());
    }

    public void requestMultiplePermissions() {
        if (!checkForMyRequiredLocationPermissions()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_BACKGROUND_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_COARSE_LOCATION)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Snackbar.make(this.findViewById(android.R.id.content),
                            "Please change your Location settings for the geofencing feature",
                            Snackbar.LENGTH_INDEFINITE).setAction("Continue",
                            v -> requestPermissions(
                                    permissions, PERMISSIONS_MULTIPLE_REQUEST)).show();
                }

            }

        }
    }

    public boolean checkForMyRequiredLocationPermissions() {
        return (ContextCompat.checkSelfPermission(this, ACCESS_BACKGROUND_LOCATION) +
                ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) +
                ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION))
                == PackageManager.PERMISSION_GRANTED;
    }

    private void startGeoFenceMonitoring() {
        try {
            requestMultiplePermissions();
            createGeofence(myId, LATITUDE, LONGITUDE, RADIUS);
            addGeofence();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopGeoFencingMonitoring() {
        try {
            removeGeofence();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}