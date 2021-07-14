package ch.bbcag.NFController.MapActivities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;

import javax.inject.Inject;

import ch.bbcag.NFController.AppDataManager;
import ch.bbcag.NFController.Const;
import ch.bbcag.NFController.Dagger2.NFControllerApplication;
import ch.bbcag.NFController.R;

public class SelectGeofencingExpirationTimeActivity extends AppCompatActivity {

    private Intent intent;
    private NumberPicker numberPickerHours;
    private NumberPicker numberPickerMinutes;
    private NumberPicker numberPickerSeconds;
    private int h;
    private int m;
    private int s;
    private long expirationTimeInMilliseconds;

    @Inject
    AppDataManager appDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ((NFControllerApplication) getApplicationContext()).appComponent.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_geofencing_expiration_time);

        numberPickerHours = findViewById(R.id.NP_Expiration_Time_H);
        numberPickerMinutes = findViewById(R.id.NP_Expiration_Time_M);
        numberPickerSeconds = findViewById(R.id.NP_Expiration_Time_S);

        setMaxValueOfNumberPickers();

        FloatingActionButton floatingActionButton = findViewById(R.id.continue_to_final_geofencing_view);
        floatingActionButton.setOnClickListener(v -> {
            formatNumberPickers();
            setExpirationTime(h, m, s);
            appDataManager.getSplitted()[6] = String.valueOf(expirationTimeInMilliseconds);
            if (getExpirationTimeInMilliseconds() > 0) {
                intent = new Intent(getApplicationContext(), SelectGeoFencingFeatureActivity.class);
                startActivity(intent);
            } else
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.geofencing_expiration_time_0_value_toast), Toast.LENGTH_SHORT).show();

        });


    }

    private void setMaxValueOfNumberPickers() {

        for (NumberPicker numberPicker : Arrays.asList(numberPickerHours, numberPickerMinutes, numberPickerSeconds)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                numberPicker.setTextColor(Color.WHITE);
            }
            numberPicker.setMinValue(0);
            if (numberPicker == numberPickerHours) {
                numberPicker.setMaxValue(23);
            } else {
                numberPicker.setMaxValue(59);
            }
        }
    }

    private void formatNumberPickers() {
        h = numberPickerHours.getValue();
        m = numberPickerMinutes.getValue();
        s = numberPickerSeconds.getValue();
    }

    public void setExpirationTime(int h, int m, int s) {
        expirationTimeInMilliseconds = (60 * (h * 60 + m) + s) * 1000;
    }


    public long getExpirationTimeInMilliseconds() {
        return expirationTimeInMilliseconds;
    }

    public int getH() {
        return h;
    }

    public int getM() {
        return m;
    }

    public int getS() {
        return s;
    }
}