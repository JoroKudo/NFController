package ch.bbcag.NFController;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ch.bbcag.NFController.AttributeFragments.*;


public class AttributeSetter extends AppCompatActivity implements View.OnClickListener {

    private Fragment fragment;
    private String[] fulltask;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attribute_setter);
        Intent intent = getIntent();
        fulltask = intent.getStringArrayExtra("FULL_TASK");
        initViews();

        whichFeatureHasBeenSelected();

        fragmentlauncher();


    }

    public void onClick(View view) {
        Intent intent;
        if (fragment instanceof TextParameter) {
            ((TextParameter) fragment).getTextinput();

        } else if (fragment instanceof AlarmAttribute) {
            for (int i = 0; i < ((AlarmAttribute) fragment).setAlarm().length; i++) {
                fulltask[i+1]=((AlarmAttribute) fragment).setAlarm()[i];
            }


        } else if (fragment instanceof TimerAttribute) {
            for (int i = 0; i < ((TimerAttribute) fragment).getTimer().length; i++) {
                fulltask[i+1]=((TimerAttribute) fragment).getTimer()[i];
            }


        } else if (fragment instanceof MessageParameter) {
            for (int i = 0; i < ((MessageParameter) fragment).getMessage().length; i++) {
                fulltask[i+1]=((MessageParameter) fragment).getMessage()[i];
            }

        } else if (fragment instanceof IOAttribute) {
            fulltask[1]=((IOAttribute) fragment).getstate();

        } else if (fragment instanceof AppSelector) {
            fulltask[1]=((AppSelector) fragment).getapp();

        }
        Const.taskcontainer.add(fulltask);

        if (view.getId() == R.id.rlWriteTask) {


            intent = new Intent(this, TaskWriter.class);
            startActivity(intent);

        } else if (view.getId() == R.id.rlAddTask) {






            intent = new Intent(this, NfcHome.class);
            startActivity(intent);

        }

    }


    protected void initViews() {
        LinearLayout rlWriteTask = findViewById(R.id.rlWriteTask);
        LinearLayout rladdtask = findViewById(R.id.rlAddTask);
        rlWriteTask.setOnClickListener(this);
        rladdtask.setOnClickListener(this);

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
        fragment = null;
        switch (fulltask[0]) {
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

                fragment = new TextParameter();
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

            case "send":
                fragment = new MessageParameter();
                break;
            case "open":
                fragment = new AppSelector();
                break;
        }
        return fulltask[0];
    }


}


