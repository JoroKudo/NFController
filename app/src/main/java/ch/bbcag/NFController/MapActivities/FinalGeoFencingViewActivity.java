package ch.bbcag.NFController.MapActivities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

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

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import ch.bbcag.NFController.AppDataManager;
import ch.bbcag.NFController.Const;
import ch.bbcag.NFController.Dagger2.NFControllerApplication;
import ch.bbcag.NFController.NfcHome;
import ch.bbcag.NFController.PermissionSecurityManager;
import ch.bbcag.NFController.R;
import ch.bbcag.NFController.TaskWriter;
import ch.bbcag.NFController.Util;
import ch.bbcag.NFController.databinding.ActivityFinalGeoFencingViewBinding;

import static android.graphics.Color.TRANSPARENT;

public class FinalGeoFencingViewActivity extends FragmentActivity implements OnMapReadyCallback {

    @Inject
    PermissionSecurityManager permissionSecurityManager;
    @Inject
    AppDataManager appDataManager;

    private TextView addressText;
    private TextView radiusText;
    private TextView timeText;
    private TextView featureText;

    private Marker marker;
    private Circle circle;
    private int h;
    private int m;
    private int s;

    //TODO
    //Differentiate between latitude when FinalView is launched from NFCRead or from FeatureSelector
    //Problem will be solved when refactoring code and using AppDataManager as main data source.



    private ActivityFinalGeoFencingViewBinding binding;

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((NFControllerApplication) getApplicationContext()).appComponent.inject(this);



        super.onCreate(savedInstanceState);
        binding = ActivityFinalGeoFencingViewBinding.inflate(getLayoutInflater());
        ConstraintLayout root = binding.getRoot();
        setContentView(root);
        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        loadMap();

        if (appDataManager.getSplitted()[7].equals(Const.GEOTASKS[4]) | appDataManager.getSplitted()[7].equals(Const.GEOTASKS[5]) | appDataManager.getSplitted()[7].equals(Const.GEOTASKS[6]) | appDataManager.getSplitted()[7].equals(Const.GEOTASKS[7])) {
            permissionSecurityManager.checkIfNotificationPermissionIsGranted(this, Util.getNotificationManager(getApplicationContext()));
        }

        Intent intentBefore = this.getIntent();
        String extraInformation = intentBefore.getExtras().getString("FinalView");
        initializeViewContent();
        setText();

        if (extraInformation.equals("FromFeatureSelector")) {
            initializeViewContent();
            FloatingActionButton floatingActionButton = findViewById(R.id.continue_to_NFC_writer);
            floatingActionButton.setOnClickListener(v -> {

                appDataManager.getSplitted()[1] = "ID";

                Collections.addAll(Const.taskcontainer, appDataManager.getSplitted());

                Intent intent = new Intent(getApplicationContext(), TaskWriter.class);
                startActivity(intent);
            });
        } else if (extraInformation.equals("From_NFCRead")) {
            initializeViewContent();
            FloatingActionButton floatingActionButton = findViewById(R.id.continue_to_NFC_Home);
            floatingActionButton.setOnClickListener(v -> {
                Intent homeIntent = new Intent(this, NfcHome.class);
                startActivity(homeIntent);
            });
        }


    }

    @Override
    public void onMapReady(@NotNull GoogleMap googleMap) {
        mMap = googleMap;
        marker = mMap.addMarker(new MarkerOptions().position(placeLatLng).title(address));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(placeLatLng, 17));
        circle = mMap.addCircle(new CircleOptions().center(placeLatLng).radius(radius).strokeColor(TRANSPARENT).fillColor(0x50021CDE));
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

    @SuppressLint("SetTextI18n")
    private void setText() {


        convertMillisecondsToTime();

        addressText.setText(address);
        radiusText.setText(String.valueOf(radius));
        timeText.setText(h + "h " + m + "m " + s + "s");
        featureText.setText(appDataManager.getSplitted()[9]);

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


    private void initializeViewContent() {
        addressText = findViewById(R.id.address);
        radiusText = findViewById(R.id.radius);
        timeText = findViewById(R.id.time);
        featureText = findViewById(R.id.feature);
    }

}