package ch.bbcag.NFController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import ch.bbcag.NFController.AttributeFragments.AlarmAttribute;
import ch.bbcag.NFController.AttributeFragments.AppSelector;
import ch.bbcag.NFController.AttributeFragments.IOAttribute;
import ch.bbcag.NFController.AttributeFragments.MessageParam;
import ch.bbcag.NFController.AttributeFragments.TextParam;
import ch.bbcag.NFController.AttributeFragments.TimerAttribute;
import ch.bbcag.NFController.AttributeFragments.VolumeModeParam;


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
        Util.fragmentLauncher(fragment, R.id.coolFrameLayout, this);
    }

    public void onClick(View view) {
        Intent intent;
        if (fragment instanceof TextParam) {
            ((TextParam) fragment).getTextinput();

        } else if (fragment instanceof AlarmAttribute) {
            for (int i = 0; i < ((AlarmAttribute) fragment).setAlarm().length; i++) {
                fulltask[i + 1] = ((AlarmAttribute) fragment).setAlarm()[i];
            }

        } else if (fragment instanceof TimerAttribute) {
            for (int i = 0; i < ((TimerAttribute) fragment).getTimer().length; i++) {
                fulltask[i + 1] = ((TimerAttribute) fragment).getTimer()[i];
            }

        } else if (fragment instanceof MessageParam) {
            for (int i = 0; i < ((MessageParam) fragment).getMessage().length; i++) {
                fulltask[i + 1] = ((MessageParam) fragment).getMessage()[i];
            }

        } else if (fragment instanceof IOAttribute) {
            fulltask[1] = ((IOAttribute) fragment).getstate();

        } else if (fragment instanceof AppSelector) {
            fulltask[1] = ((AppSelector) fragment).getapp();

        } else if (fragment instanceof VolumeModeParam) {
            fulltask[0] = ((VolumeModeParam) fragment).getVolMode();

        }
        Const.taskcontainer.add(fulltask);

        if (view.getId() == R.id.rlAddTask) {
            intent = new Intent(this, TaskAdder.class);
            startActivity(intent);
        }
    }

    protected void initViews() {
        LinearLayout rladdtask = findViewById(R.id.rlAddTask);
        rladdtask.setOnClickListener(this);
    }

    private void whichFeatureHasBeenSelected() {
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
                fragment = new TextParam();
                break;
            case "tone":
            case "mute":
            case "vibrate":
                fragment = new VolumeModeParam();
                break;
            case "timer":
                fragment = new TimerAttribute();
            case "geofencing":
                break;
            case "send":
                fragment = new MessageParam();
                break;
            case "open":
                fragment = new AppSelector();
                break;
        }
    }
}


