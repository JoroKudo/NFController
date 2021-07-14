package ch.bbcag.NFController.Features;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;

import ch.bbcag.NFController.R;

public class Flashlight {

    private final Context context;

    public Flashlight(Context context) {
        this.context = context;
    }

    public void flash(String switcher) {
        CameraManager camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        String cameraId;
        try {
            cameraId = camManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (switcher.equals(context.getResources().getString(R.string.on))) {
                    camManager.setTorchMode(cameraId, true);
                } else if (switcher.equals(context.getResources().getString(R.string.off))) {
                    camManager.setTorchMode(cameraId, false);
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}
