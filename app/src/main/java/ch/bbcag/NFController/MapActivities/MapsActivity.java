package ch.bbcag.NFController.MapActivities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import ch.bbcag.NFController.Alerts;
import ch.bbcag.NFController.AppDataManager;
import ch.bbcag.NFController.Dagger2.NFControllerApplication;
import ch.bbcag.NFController.PermissionSecurityManager;
import ch.bbcag.NFController.R;
import ch.bbcag.NFController.databinding.FragmentMapsBinding;

import static android.graphics.Color.TRANSPARENT;

@SuppressWarnings("deprecation")
public class MapsActivity extends SecurityFragmentActivity implements OnMapReadyCallback {

    @Inject
    PermissionSecurityManager permissionSecurityManager; // TODO make private if possible
    @Inject
    Alerts alerts;
    @Inject
    AppDataManager appDataManager;
    private EditText editText;
    private Marker marker;
    private Double radius;
    private GoogleMap mMap;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((NFControllerApplication) getApplicationContext()).appComponent.inject(this);

        super.onCreate(savedInstanceState);

        ch.bbcag.NFController.databinding.FragmentMapsBinding binding = FragmentMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        loadMap();

        editText = findViewById(R.id.sv_location);
        floatingActionButton = findViewById(R.id.continue_to_radius);
        permissionSecurityManager.requestMultiplePermissions(this);

        showAlertIfNeeded();
        hideFloatingActionButtonIfNeeded();

        editText.setOnClickListener(v -> createNewMarkerThroughAutocomplete());
        floatingActionButton.setOnClickListener(v -> startActivityIfPlaceSelected());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            assert data != null;
            Place place = Autocomplete.getPlaceFromIntent(data);

            editText.setText(place.getAddress());

            LatLng placeLatLng = place.getLatLng();
            assert placeLatLng != null;
            double placeLatitude = placeLatLng.latitude;
            double placeLongitude = placeLatLng.longitude;
            String address = place.getAddress();

            appDataManager.getSplitted()[0] = "geofencing";
            appDataManager.getSplitted()[2] = String.valueOf(placeLatitude);
            appDataManager.getSplitted()[3] = String.valueOf(placeLongitude);
            appDataManager.getSplitted()[4] = address;

            if (marker != null) {
                marker.remove();
            }
            marker = mMap.addMarker(new MarkerOptions().position(placeLatLng).title(place.getName() + " " + place.getAddress()));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(placeLatLng, 15));
            if (radius != null) {
                mMap.addCircle(new CircleOptions().center(placeLatLng).radius(radius).strokeColor(TRANSPARENT).fillColor(0x50021CDE));
            }

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            assert data != null;
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(@NotNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng zurich = new LatLng(47.3769, 8.5417);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zurich, 10));

        mMap.setOnMapLongClickListener(latLng -> {
            if (marker != null) {
                marker.remove();
            }
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).title(latLng.latitude + ", " + latLng.longitude));
            appDataManager.getSplitted()[0] = "geofencing";
            appDataManager.getSplitted()[2] = String.valueOf(marker.getPosition().latitude);
            appDataManager.getSplitted()[3] = String.valueOf(marker.getPosition().longitude);

            try {
                List<Address> addresses = new Geocoder(getApplicationContext()).getFromLocation(latLng.latitude, latLng.longitude, 1);
                appDataManager.getSplitted()[4] = addresses.get(0).getAddressLine(0);
            } catch (IOException e) {
                e.printStackTrace();
                appDataManager.getSplitted()[4] = "No_Address_available";
            }

        });
    }

    private void createNewMarkerThroughAutocomplete() {
        List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(getApplicationContext());

        startActivityForResult(intent, 100);
    }

    private void loadMap() {
        try {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            assert mapFragment != null;
            mapFragment.getMapAsync(this);
        } catch (NullPointerException e) {
            Log.e("NullPointerException", "Map ist not loading");
            Toast.makeText(getApplicationContext(), "Map is not loading", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideFloatingActionButtonIfNeeded() {
        if (!permissionSecurityManager.areMultiplePermissionsGranted(this)) {
            floatingActionButton.hide();
        }
    }

    private void showAlertIfNeeded() {
        if (permissionSecurityManager.hasThePermissionAlreadyBeenDenied()) {
            alerts.displayPermissionAlert(this);
        }
    }

    private void startActivityIfPlaceSelected() {
        if (appDataManager.getSplitted()[0].isEmpty() || appDataManager.getSplitted()[2].isEmpty() || appDataManager.getSplitted()[3].isEmpty() || appDataManager.getSplitted()[4].isEmpty()) {
            Toast.makeText(this, "please Select a Place on the map to continue", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getApplicationContext(), SelectGeofencingRadiusActivity.class);
            startActivity(intent);
        }
    }
}