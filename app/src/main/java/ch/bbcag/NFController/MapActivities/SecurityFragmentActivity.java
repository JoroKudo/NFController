package ch.bbcag.NFController.MapActivities;

import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.fragment.app.FragmentActivity;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import ch.bbcag.NFController.Dagger2.NFControllerApplication;
import ch.bbcag.NFController.PermissionSecurityManager;
import ch.bbcag.NFController.TaskAdder;

public class SecurityFragmentActivity extends FragmentActivity {

    @Inject
    PermissionSecurityManager permissionSecurityManager;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        ((NFControllerApplication) getApplicationContext()).appComponent.inject(this);

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length <= 0 ||
                grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(this.findViewById(android.R.id.content),
                            "Without the needed permissions, the geofencing feature will not be available",
                            Snackbar.LENGTH_INDEFINITE).setAction("ok",
                            v -> this.startActivity(new Intent(this, TaskAdder.class))).show();
                    permissionSecurityManager.setIfThePermissionHasAlreadyBeenDenied(true);


                }
    }
}
