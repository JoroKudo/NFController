package ch.bbcag.NFController.MapActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;

import ch.bbcag.NFController.R;

public class SelectGeofencingExpirationTimeActivity extends AppCompatActivity {

    Intent intent;
    NumberPicker numberPickerHours;
    NumberPicker numberPickerMinutes;
    NumberPicker numberPickerSeconds;
    private int h;
    private int m;
    private int s;
    long expirationTimeInMilliseconds;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_geofencing_expiration_time);


        FloatingActionButton floatingActionButton = findViewById(R.id.continue_to_final_geofencing_view);
        floatingActionButton.setOnClickListener(v -> {
            setMaxValueOfNumberPickers();
            formatNumberPickers();
                if (h != 0 && m != 0 && s != 0) {
                    setExpirationTime(h, m, s);
                    intent = new Intent(getApplicationContext(), SelectGeoFencingFeatureActivity.class);
                    getApplicationContext().startActivity(intent);
                }else
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
        expirationTimeInMilliseconds = (60 * (h * 60 + m) + s)*1000;
    }

}