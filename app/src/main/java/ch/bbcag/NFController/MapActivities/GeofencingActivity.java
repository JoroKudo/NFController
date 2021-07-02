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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ch.bbcag.NFController.Dagger2.NFControllerApplication;
import ch.bbcag.NFController.AppDataManager;

public class GeofencingActivity extends AppCompatActivity {

    @Inject
    AppDataManager appDataManager;

    private final String ACCESS_BACKGROUND_LOCATION = Manifest.permission.ACCESS_BACKGROUND_LOCATION;
    private final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    private final String[] permissions = new String[]{ACCESS_BACKGROUND_LOCATION, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};

    List<Geofence> geofenceList = new ArrayList<>();
    private PendingIntent geofencePendingIntent;
    GeofencingClient geofencingClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        geofencingClient = new GeofencingClient(this);

        ((NFControllerApplication) getApplicationContext()).appComponent.inject(this);

        startGeoFenceMonitoring(appDataManager.getSplitted()[1], Double.parseDouble(appDataManager.getSplitted()[2]),Double.parseDouble(appDataManager.getSplitted()[3]), Float.parseFloat(appDataManager.getSplitted()[5]), Long.parseLong(appDataManager.getSplitted()[6]));

        super.onCreate(savedInstanceState);
    }


    private PendingIntent getGeofencePendingIntent() {
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofencingBroadcastReceiver.class);
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

    private void createGeofence(String id, double latitude, double longitude, float radius, long expirationTime) {
        Geofence build = new Geofence.Builder()
                .setRequestId(id)
                .setCircularRegion(
                        latitude,
                        longitude,
                        radius
                )
                .setExpirationDuration(expirationTime)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();
        geofenceList.add(build);
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

    private void startGeoFenceMonitoring(String myId, double latitude, double longitude, float radius, long expirationTime) {
        requestMultiplePermissions();
        createGeofence(myId, latitude, longitude, radius, expirationTime);
        addGeofence();
    }

    private void stopGeoFencingMonitoring() {
        try {
            removeGeofence();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}