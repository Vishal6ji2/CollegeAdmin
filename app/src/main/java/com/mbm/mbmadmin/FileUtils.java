package com.mbm.mbmadmin;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;

import static com.mbm.mbmadmin.Activities.EbooksActivity.copy;

public class FileUtils {

    //    getFileName
    @NonNull
    public static String getfilename(@NonNull Context context, @NonNull Uri uri){
        String filename = "";

        String uristring = null;
        uristring = uri.toString();
        File file = null;
        file = new File(uristring);
        //            String mypath = file.getAbsolutePath();

        if (uristring.startsWith("content://")) {

            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    filename = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    Log.d("filename", filename);
                }

            }
        } else if (uristring.startsWith("file://")) {
            filename = file.getName();
            Log.d("filename",filename);
        }
        return filename;
    }


    //getfilepathfromuri
    @NonNull
    public static String getFilePathFromURI(@NonNull Context context, @NonNull Uri contentUri) {
        //copy file and send new file path
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + "10");
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) wallpaperDirectory.mkdirs();
        if (!TextUtils.isEmpty(getfilename(context,contentUri))) {
            File copyFile = new File(wallpaperDirectory + File.separator + getfilename(context,contentUri));
            // create folder if not exists

            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }
}
