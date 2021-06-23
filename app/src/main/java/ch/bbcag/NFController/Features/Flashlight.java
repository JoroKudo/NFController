package ch.bbcag.NFController.Features;

import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;

public class Flashlight {

    private final Activity activity;

    public Flashlight(Activity activity) {
        this.activity = activity;
    }

    public void flash(String switcher) {

        CameraManager camManager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
        String cameraId;
        try {
            cameraId = camManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (switcher.equals("1")) {
                    camManager.setTorchMode(cameraId, true);
                } else if (switcher.equals("0")) {
                    camManager.setTorchMode(cameraId, false);
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

}
