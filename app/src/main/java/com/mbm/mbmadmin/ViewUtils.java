package com.mbm.mbmadmin;

import android.content.Context;
import android.widget.Toast;
import androidx.annotation.NonNull;

public class ViewUtils {

//    toast
    public static void toast(@NonNull Context context , @NonNull String msg){
        Toast.makeText(context , msg, Toast.LENGTH_SHORT).show();
    }

}
