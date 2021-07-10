package com.mbm.mbmadmin;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class ViewUtils implements ActivityCompat.OnRequestPermissionsResultCallback {

    @NonNull
    public static Context context = null;

    public static final int STORAGE_PERMISSION_REQUEST_CODE = 1;

    private static final int BUFFER_SIZE = 1024;
    private static final int REQUEST_WRITE_PERMISSION = 786;

    public ViewUtils(@NonNull Context context) {
        ViewUtils.context = context;
    }

    //    toast
    public static void toast(@NonNull Context context, @NonNull String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

//    Request Permissions


    public static void askPermissions() {

        int permissionCheckStorage = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // we already asked for permisson & Permission granted, call camera intent
        if (permissionCheckStorage == PackageManager.PERMISSION_GRANTED) {

            //do what you want

        } else {

            // if storage request is denied
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("You need to give permission to access storage in order to work this feature.");
                builder.setNegativeButton("CANCEL", (dialogInterface, i) -> dialogInterface.dismiss());
                builder.setPositiveButton("GIVE PERMISSION", (dialogInterface, i) -> {
                    dialogInterface.dismiss();

                    // Show permission request popup
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            STORAGE_PERMISSION_REQUEST_CODE);
                });
                builder.show();

            } //asking permission for first time
            else {
                // Show permission request popup for the first time
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_REQUEST_CODE);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // check whether storage permission granted or not.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //do what you want;
                }
            }
        }
    }
}
