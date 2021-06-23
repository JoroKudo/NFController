package ch.bbcag.NFController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ch.bbcag.NFController.AttributeFragments.*;
import ch.bbcag.NFController.MapActivities.MapsActivity;
import ch.bbcag.NFController.MapActivities.SelectGeofencingRadiusActivity;


public class AttributeSetter extends AppCompatActivity implements View.OnClickListener {
    private Fragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attribute_setter);
        initViews();

        whichFeatureHasBeenSelected();

        fragmentlauncher();


    }

    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.rlWriteTask) {
            if (fragment instanceof TextAttribute) {
                ((TextAttribute) fragment).setText();
            } else if (fragment instanceof AlarmAttribute) {
                ((AlarmAttribute) fragment).setAlarm();
            } else if (fragment instanceof TimerAttribute) {
                ((TimerAttribute) fragment).setTimer();
            }
        }
        intent = new Intent(this, TaskWriter.class);
        startActivity(intent);
    }


    protected void initViews() {
        LinearLayout rlWriteTask = findViewById(R.id.rlWriteTask);
        rlWriteTask.setOnClickListener(this);

    }

    public void fragmentlauncher() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        assert fragment != null;
        ft.replace(R.id.coolFrameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();


    }

    public String whichFeatureHasBeenSelected() {
        switch (Const.fulltask[0]) {
            case "blue":
            case "flash":
            case "vol":
            case "wifi":
                fragment = new IOAttribute();
                break;

            case "alarm":
                fragment = new AlarmAttribute();
                break;

            case "tts":
            case "web":

                fragment = new TextAttribute();
                break;
            case "tone":
            case "mute":
            case "vibrate":

                Intent intent = new Intent(this, TaskWriter.class);
                startActivity(intent);
                break;
            case "timer":
                fragment = new TimerAttribute();
            case "geofencing":
                break;
        }
        return Const.fulltask[0];
    }


}

