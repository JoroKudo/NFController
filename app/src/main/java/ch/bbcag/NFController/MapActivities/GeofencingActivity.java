package ch.bbcag.NFController.MapActivities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ch.bbcag.NFController.AppDataManager;
import ch.bbcag.NFController.Dagger2.NFControllerApplication;
import ch.bbcag.NFController.NfcHome;
import ch.bbcag.NFController.PermissionSecurityManager;
import ch.bbcag.NFController.R;

public class GeofencingActivity extends AppCompatActivity {

    private final List<Geofence> geofenceList = new ArrayList<>();
    @Inject
    AppDataManager appDataManager;
    @Inject
    PermissionSecurityManager permissionSecurityManager;
    private PendingIntent geofencePendingIntent;
    private GeofencingClient geofencingClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        geofencingClient = new GeofencingClient(this);

        ((NFControllerApplication) getApplicationContext()).appComponent.inject(this);

        startGeoFenceMonitoring(appDataManager.getSplitted()[1], Double.parseDouble(appDataManager.getSplitted()[2]), Double.parseDouble(appDataManager.getSplitted()[3]), Float.parseFloat(appDataManager.getSplitted()[5]), Long.parseLong(appDataManager.getSplitted()[6]));

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
                    .addOnSuccessListener(this, aVoid -> Toast.makeText(getApplicationContext(), (getApplicationContext().getResources().getString(R.string.geofence_added_toast)), Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(this, e -> Toast.makeText(getApplicationContext(), (getApplicationContext().getResources().getString(R.string.failed_add_geofence_toast)), Toast.LENGTH_SHORT).show());
        }
        Intent NFCHomeIntent = new Intent(this, NfcHome.class);
        startActivity(NFCHomeIntent);

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

    public boolean checkForMyRequiredLocationPermissions() {
        String ACCESS_BACKGROUND_LOCATION = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            ACCESS_BACKGROUND_LOCATION = Manifest.permission.ACCESS_BACKGROUND_LOCATION;
        }
        String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
        String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
        assert ACCESS_BACKGROUND_LOCATION != null;
        return (ContextCompat.checkSelfPermission(this, ACCESS_BACKGROUND_LOCATION) +
                ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) +
                ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION))
                == PackageManager.PERMISSION_GRANTED;
    }

    private void startGeoFenceMonitoring(String myId, double latitude, double longitude, float radius, long expirationTime) {
        permissionSecurityManager.requestBackgroundLocation(this);
        createGeofence(myId, latitude, longitude, radius, expirationTime);
        addGeofence();
    }
}