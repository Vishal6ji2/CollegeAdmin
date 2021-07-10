package com.mbm.mbmadmin.Activities;


import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.mbm.mbmadmin.ModelResponse.AddResponses.AddAdminResponse;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.RetrofitClient;
import com.mbm.mbmadmin.Sessions.LoginSession;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mbm.mbmadmin.Sessions.LoginSession.KEY_DEPT;
import static com.mbm.mbmadmin.Sessions.LoginSession.KEY_DEPT_ID;
import static com.mbm.mbmadmin.Sessions.LoginSession.KEY_IMAGE;
import static com.mbm.mbmadmin.Sessions.LoginSession.KEY_NAME;
import static com.mbm.mbmadmin.Sessions.LoginSession.KEY_SUPER_ADMIN;

import static com.mbm.mbmadmin.ViewUtils.toast;


public class HomeActivity extends AppCompatActivity {

    MaterialToolbar toolbar;

    ShapeableImageView profileimg;

    TextView txtname,txtbranch,txtSuperAdmin;

    RelativeLayout newslayout,noticelayout,placementlayout,ttlayout,paperlayout,linkslayout,ebooklayout,studentslayout;

    BottomSheetDialog bottomSheetDialog;

    public static ActivityResultLauncher<Intent> launchSomeActivity;

    LoginSession loginSession;

    int flag = 0;

    int addAdmin_menu_id = 101;

    Menu menu;

    String name, email, phone, branch, imageurl, branchid, status, adminid,superAdmin;


    //Add admin Bottomsheet Views

    ImageView cancelimg;

    TextInputEditText edtemail,edtname,edtmob;

    MaterialButton btnsubmit;

    ProgressBar progressBar;


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initviews();

        setSupportActionBar(toolbar);

        loginSession = new LoginSession(this);

        imageurl = loginSession.getAdminDetailsFromSession().get(KEY_IMAGE);
        name = loginSession.getAdminDetailsFromSession().get(KEY_NAME);
        branch = loginSession.getAdminDetailsFromSession().get(KEY_DEPT);
        superAdmin = loginSession.getAdminDetailsFromSession().get(KEY_SUPER_ADMIN);

        if (superAdmin.equals("0")){
            txtSuperAdmin.setVisibility(View.VISIBLE);
        }else {
            txtSuperAdmin.setVisibility(View.GONE);
        }

        Uri uri = Uri.parse(imageurl);
        Glide.with(this).load(uri).into(profileimg);
        txtname.setText(name);
        txtbranch.setText(branch);

        linkslayout.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this,LinksActivity.class)));

        studentslayout.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this,StudentsListActivity.class)));

        newslayout.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this,NewsfeedActivity.class)));

        ebooklayout.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this,EbooksActivity.class)));

        noticelayout.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this,NoticeActivity.class)));

        placementlayout.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this,PlacementActivity.class)));

        paperlayout.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, PapersActivity.class)));

        ttlayout.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, TimetableActivity.class)));

        profileimg.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this,ProfileActivity.class);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this,profileimg, Objects.requireNonNull(ViewCompat.getTransitionName(profileimg)));

            startActivity(intent,optionsCompat.toBundle());
        });

        txtname.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this,ProfileActivity.class)));

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        this.menu = menu;

        getMenuInflater().inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(@NonNull Menu menu) {

        if (superAdmin.equals("0") && flag == 0) {

            MenuItem addAdminmenu = menu.add(Menu.NONE,addAdmin_menu_id,50,"Add Admin");
            addAdminmenu.setIcon(R.drawable.addstudenticon);
            addAdminmenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

            flag = 1;

            addAdminmenu.setOnMenuItemClickListener(item -> {

                addNewAdmin();

                return true;
            });
        }

        return true;
    }

    void addNewAdmin() {

        bottomSheetDialog = new BottomSheetDialog(HomeActivity.this,R.style.BottomSheetDialogTheme);
        View bottomsheetview = LayoutInflater.from(HomeActivity.this).inflate(R.layout.addadmin_bottomsheet,(LinearLayout)findViewById(R.id.addadmin_bottomsheetlayout));
        addAdminViews(bottomsheetview);

        cancelimg.setOnClickListener(v -> bottomSheetDialog.dismiss());

        btnsubmit.setOnClickListener(v -> {
            if (edtname.getText().toString().equals("")){
                edtname.setError("Please Enter Admin Name");
            }else if (edtmob.getText().toString().equals("")){
                edtmob.setError("Please Enter Admin Mobile No.");
            }else if (edtemail.getText().toString().equals("")){
                edtname.setError("Please Enter Admin Email id");
            }else {

                addAdminDetails();
            }
        });

        bottomSheetDialog.setContentView(bottomsheetview);
        bottomSheetDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.homemenu_logout){

            loginSession.logoutAdminSession();

            startActivity(new Intent(HomeActivity.this, EmailVerifyActivity.class));
            finish();

        }
        return true;
    }

    void addAdminDetails() {

        progressBar.setVisibility(View.VISIBLE);

        Call<AddAdminResponse> addResponseCall = RetrofitClient.getInstance().getapi().addAdmin(edtname.getText().toString(),edtemail.getText().toString(),edtmob.getText().toString(),loginSession.getAdminDetailsFromSession().get(KEY_DEPT_ID));

        addResponseCall.enqueue(new Callback<AddAdminResponse>() {
            @Override
            public void onResponse(Call<AddAdminResponse> call, Response<AddAdminResponse> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()){
                    if (response.body() != null){
                        if (response.body().getStatus() == 1) {
                            toast(HomeActivity.this, response.body().getMessage());
                            bottomSheetDialog.dismiss();
                        }else {
                            toast(HomeActivity.this,response.body().getMessage());
                        }
                    }else {
                        toast(HomeActivity.this,"Error Occured");
                    }
                }
            }

            @SuppressLint("LogConditional")
            @Override
            public void onFailure(Call<AddAdminResponse> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                bottomSheetDialog.dismiss();

                Log.d("addadmin",t.getLocalizedMessage());
                toast(HomeActivity.this,"Something went Wrong ");
            }
        });
    }

    private void addAdminViews(View bottomsheetview) {

        cancelimg = bottomsheetview.findViewById(R.id.addadmin_backimg);

        btnsubmit = bottomsheetview.findViewById(R.id.addadmin_btnsubmit);

        edtemail = bottomsheetview.findViewById(R.id.addadmin_edtemail);
        edtmob = bottomsheetview.findViewById(R.id.addadmin_edtmob);
        edtname = bottomsheetview.findViewById(R.id.addadmin_edtname);

        progressBar = bottomsheetview.findViewById(R.id.addadmin_progressbar);

    }

    private void initviews() {

        toolbar = findViewById(R.id.home_toolbar);

        profileimg = findViewById(R.id.home_profileimg);

        txtname = findViewById(R.id.home_txtname);
        txtbranch = findViewById(R.id.home_txtbranch);
        txtSuperAdmin = findViewById(R.id.home_txtadmin);

        ebooklayout = findViewById(R.id.home_ebooklayout);
        newslayout = findViewById(R.id.home_newsfeedlayout);
        noticelayout = findViewById(R.id.home_noticelayout);
        placementlayout = findViewById(R.id.home_placementlayout);
        ttlayout = findViewById(R.id.home_ttlayout);
        paperlayout = findViewById(R.id.home_paperslayout);
        studentslayout = findViewById(R.id.home_studentslayout);
        linkslayout = findViewById(R.id.home_linkslayout);

    }

    public static void takeFilePermission(@NonNull Context context){

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",context.getPackageName())));
                launchSomeActivity.launch(intent);

            }catch (Exception e){
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                launchSomeActivity.launch(intent);

            }
        }else {
            ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},101);
        }
    }

    public static boolean isPermissionGranted(@NonNull Context context){

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
//            for Android 11
            return Environment.isExternalStorageManager();
        }else {
//            for Below
            int readExternalStoragePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
            return readExternalStoragePermission == PackageManager.PERMISSION_GRANTED;
        }
    }


}