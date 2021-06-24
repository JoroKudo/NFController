package ch.bbcag.NFController.MapActivities;

import androidx.fragment.app.FragmentActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import ch.bbcag.NFController.Const;
import ch.bbcag.NFController.R;
import ch.bbcag.NFController.databinding.FragmentMapsBinding;

import static android.graphics.Color.TRANSPARENT;

public class FinalGeoFencingViewActivity extends FragmentActivity implements OnMapReadyCallback {

    Marker marker;
    Circle circle;
    private int h;
    private int m;
    private int s;
    private final double latitude = Double.parseDouble(Const.fulltask[2]);
    private final double longitude = Double.parseDouble(Const.fulltask[3]);
    private final String address = Const.fulltask[4];
    private final LatLng placeLatLng = new LatLng(latitude, longitude);
    private final int radius = Integer.parseInt(Const.fulltask[5]);
    private final long expirationTimeInMilliseconds = Long.parseLong(Const.fulltask[6]);
    private FragmentMapsBinding binding;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        loadMap();
        setText();

        FloatingActionButton floatingActionButton = findViewById(R.id.continue_to_NFC_writer);
        floatingActionButton.setOnClickListener(v -> {

            Const.fulltask[1] = "ID";
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        marker = mMap.addMarker(new MarkerOptions().position(placeLatLng).title(address));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(placeLatLng, 10));
        circle = mMap.addCircle(new CircleOptions().center(placeLatLng).radius(radius).strokeColor(TRANSPARENT).fillColor(0x50021CDE));
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

    private void setText() {
        TextView addressText = findViewById(R.id.address);
        TextView radiusText = findViewById(R.id.radius);
        TextView timeText = findViewById(R.id.time);
        TextView featureText = findViewById(R.id.feature);

        convertMillisecondsToTime();

        addressText.setText(address);
        radiusText.setText(String.valueOf(radius));
        timeText.setText(h + "h " + m + "m " + s + "s");
        featureText.setText(Const.fulltask[6]);

}

    private void calculateExpirationTime() {
        long expirationTime = new SelectGeofencingExpirationTimeActivity().getExpirationTimeInMilliseconds();
        long expirationTimeInSeconds = expirationTime / 1000;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalTime currentTime = LocalTime.now();
            LocalDate currentDate = LocalDate.now();
            LocalDateTime currentTimeAndDate = LocalDateTime.of(currentDate, currentTime);
            LocalDateTime timeAndDateWhenExpired = currentTimeAndDate.plusSeconds(expirationTimeInSeconds);
        }
    }

    private void convertMillisecondsToTime() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            h = Math.toIntExact(TimeUnit.MILLISECONDS.toHours(expirationTimeInMilliseconds));
            m = Math.toIntExact(TimeUnit.MILLISECONDS.toMinutes(expirationTimeInMilliseconds) - TimeUnit.HOURS.toMinutes(h));
            s = Math.toIntExact(TimeUnit.MILLISECONDS.toSeconds(expirationTimeInMilliseconds) - TimeUnit.MINUTES.toSeconds(m) - TimeUnit.HOURS.toSeconds(h));
        }

    }

}