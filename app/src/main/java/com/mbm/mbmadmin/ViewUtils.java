package com.mbm.mbmadmin;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
import androidx.annotation.NonNull;

public class ViewUtils {

//    toast
    public static void toast(@NonNull Context context , @NonNull String msg){
        Toast toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

}
