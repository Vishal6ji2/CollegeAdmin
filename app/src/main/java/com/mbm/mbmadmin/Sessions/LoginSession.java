package com.mbm.mbmadmin.Sessions;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class LoginSession {

   public SharedPreferences adminSession;

   public SharedPreferences.Editor editor;

   public Context context;

   public static final String IS_LOGIN = "isLoggedIn";

    public static final String KEY_ADMIN_ID = "adminId";
    public static final String KEY_SUPER_ADMIN = "superAdmin";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_DEPT = "dept";
    public static final String KEY_DEPT_ID = "deptId";
    public static final String KEY_IMAGE = "imageUrl";
    public static final String KEY_STATUS = "status";


    public LoginSession(@NonNull Context context){
        this.context = context;

        adminSession = context.getSharedPreferences("adminLoginSession",Context.MODE_PRIVATE);
        editor = adminSession.edit();
    }

    public void createLoginSession(String superAdmin,String name,String email,String phone,String branch,String imageurl,String branchid,String status,String adminid){

        editor.putBoolean(IS_LOGIN,true);

        editor.putString(KEY_ADMIN_ID,adminid);
        editor.putString(KEY_SUPER_ADMIN,superAdmin);
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PHONE,phone);
        editor.putString(KEY_DEPT,branch);
        editor.putString(KEY_DEPT_ID,branchid);
        editor.putString(KEY_IMAGE,imageurl);
        editor.putString(KEY_STATUS,status);

        editor.commit();

    }

    @NonNull
    public HashMap<String,String> getAdminDetailsFromSession(){

        HashMap<String,String> adminDetails = new HashMap<>();

        adminDetails.put(KEY_NAME,adminSession.getString(KEY_NAME,null));
        adminDetails.put(KEY_SUPER_ADMIN,adminSession.getString(KEY_SUPER_ADMIN,null));
        adminDetails.put(KEY_EMAIL,adminSession.getString(KEY_EMAIL,null));
        adminDetails.put(KEY_PHONE,adminSession.getString(KEY_PHONE,null));
        adminDetails.put(KEY_DEPT_ID,adminSession.getString(KEY_DEPT_ID,null));
        adminDetails.put(KEY_DEPT,adminSession.getString(KEY_DEPT,null));
        adminDetails.put(KEY_IMAGE,adminSession.getString(KEY_IMAGE,null));
        adminDetails.put(KEY_ADMIN_ID,adminSession.getString(KEY_ADMIN_ID,null));
        adminDetails.put(KEY_STATUS,adminSession.getString(KEY_STATUS,null));

        return adminDetails;
    }

    public boolean checkLogin(){
        if (adminSession.getBoolean(IS_LOGIN, false)){
            return true ;
        }else {
            return false;
        }
    }

    public void logoutAdminSession(){
        editor.clear();
        editor.commit();
    }
}
