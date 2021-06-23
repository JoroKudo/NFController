package ch.bbcag.NFController.MapActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ch.bbcag.NFController.R;

public class SelectGeofencingExpirationTimeActivity extends AppCompatActivity {

    Intent intent;
    FloatingActionButton floatingActionButton;
    TextView numberPickerHours;
    TextView numberPickerMinutes;
    TextView numberPickerSeconds;
    private int h;
    private int m;
    private int s;
    long expirationTimeInMilliseconds;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_geofencing_expiration_time);
        
        floatingActionButton.setOnClickListener(v -> {
            formatExpirationTime();
                if (h != 0 && m != 0 && s != 0) {
                    setExpirationTime(h, m, s);
                    intent = new Intent(getApplicationContext(), SelectGeoFencingFeatureActivity.class);
                    getApplicationContext().startActivity(intent);
                }else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.geofencing_expiration_time_0_value_toast), Toast.LENGTH_SHORT).show();

        });


    }
    private void formatExpirationTime() {
        numberPickerHours = findViewById(R.id.NP_Expiration_Time_H);
        numberPickerMinutes = findViewById(R.id.NP_Expiration_Time_M);
        numberPickerSeconds = findViewById(R.id.NP_Expiration_Time_S);
        String tempHours = numberPickerHours.getText().toString();
        String tempMinutes = numberPickerMinutes.getText().toString();
        String tempSeconds = numberPickerSeconds.getText().toString();
        h = Integer.parseInt(tempHours);
        m = Integer.parseInt(tempMinutes);
        s = Integer.parseInt(tempSeconds);
    }

    public void setExpirationTime(int h, int m, int s) {
        expirationTimeInMilliseconds = (60 * (h * 60 + m) + s)*1000;
    }

}