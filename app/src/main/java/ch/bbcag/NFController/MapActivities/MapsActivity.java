package ch.bbcag.NFController.MapActivities;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
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

import java.util.Arrays;
import java.util.List;

import ch.bbcag.NFController.Const;
import ch.bbcag.NFController.R;
import ch.bbcag.NFController.databinding.FragmentMapsBinding;

import static android.graphics.Color.TRANSPARENT;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    EditText editText;
    Marker marker;
    Place place;
    private Double radius;
    FragmentMapsBinding binding;
    private GoogleMap mMap;
    double placeLatitude;
    double placeLongitude;

    public MapsActivity(){
    }

    public MapsActivity(double radius) {
        this.radius = radius;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));

        loadMap();

        editText = findViewById(R.id.sv_location);
        editText.setOnClickListener(v -> createNewMarkerThroughAutocomplete());

        FloatingActionButton floatingActionButton = findViewById(R.id.continue_to_radius);
        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SelectGeofencingRadiusActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            place = Autocomplete.getPlaceFromIntent(data);

            editText.setText(place.getAddress());

            LatLng placeLatLng = place.getLatLng();
            placeLatitude = placeLatLng.latitude;
            placeLongitude = placeLatLng.longitude;

            if (marker!= null){
                marker.remove();
            }
            marker = mMap.addMarker(new MarkerOptions().position(placeLatLng).title(place.getName() + " " + place.getAddress()));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(placeLatLng, 15));
            if (radius != null){
                mMap.addCircle(new CircleOptions().center(placeLatLng).radius(radius).strokeColor(TRANSPARENT).fillColor(0x50021CDE));
            }

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng zurich = new LatLng(47.3769, 8.5417);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zurich, 10));

        mMap.setOnMapLongClickListener(latLng -> {
            if (marker!= null){
                marker.remove();
            }
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).title(latLng.latitude + ", " + latLng.longitude));
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
            mapFragment.getMapAsync(this);
        }catch (NullPointerException e) {
            Log.e("NullPointerException", "Map ist not loading");
            Toast.makeText(getApplicationContext(), "Map is not loading", Toast.LENGTH_SHORT).show();
        }
    }

    public Marker getMarker() {
        return marker;
    }

    public double getPlaceLatitude() {
        return placeLatitude;
    }

    public double getPlaceLongitude() {
        return placeLongitude;
    }

    public Place getPlace() {
        return place;
    }
}