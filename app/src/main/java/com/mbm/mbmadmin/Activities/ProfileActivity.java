package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Sessions.LoginSession;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.HashMap;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    MaterialToolbar toolbar;

    ImageView backimg,emaileditimg,mobeditimg;

    TextView txtname,txtemail,txtmob,txtbranch;

    FloatingActionButton cameraimg;

    ShapeableImageView profileimg;

    LoginSession loginSession;

    String name, email, phone, branch, imageurl, branchid, status, adminid,superAdmin;


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initviews();

        setSupportActionBar(toolbar);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cameraimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,100);
            }
        });

        getAdminDetails();

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ProfileActivity.this, R.style.BottomSheetDialogTheme);

        emaileditimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View bottomsheetview = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.emailbottomsheetlayout, (LinearLayout) findViewById(R.id.emailbottomlayout));

                bottomsheetview.findViewById(R.id.emailbottom_btnsave).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TextInputEditText edtemail = bottomsheetview.findViewById(R.id.emailbottom_edtemail);
                        if (edtemail.getText().toString().equals("")){
                            edtemail.setError("enter email id");
                        }else {
                            Toast.makeText(ProfileActivity.this, "Email-id changed successfully", Toast.LENGTH_SHORT).show();

                            bottomSheetDialog.dismiss();
                        }
                    }
                });

                bottomSheetDialog.setContentView(bottomsheetview);
                bottomSheetDialog.show();

            }
        });

        mobeditimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View bottomsheetview = LayoutInflater.from(ProfileActivity.this).inflate(R.layout.mobbottomsheetlayout, (LinearLayout) findViewById(R.id.mobbottomlayout));

                bottomsheetview.findViewById(R.id.mobbottom_btnsave).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TextInputEditText edtemail = bottomsheetview.findViewById(R.id.mobbottom_edtmob);
                        if (edtemail.getText().toString().equals("")){
                            edtemail.setError("enter email id");
                        }else {
                            Toast.makeText(ProfileActivity.this, "Mobile no. changed successfully", Toast.LENGTH_SHORT).show();

                            bottomSheetDialog.dismiss();
                        }
                    }
                });

                bottomSheetDialog.setContentView(bottomsheetview);
                bottomSheetDialog.show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 100 && resultCode == RESULT_OK && data != null){

            Uri uri = data.getData();
            Glide.with(this).load(uri).placeholder(R.drawable.mbmlogo).into(profileimg);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initviews() {

        toolbar = findViewById(R.id.profile_toolbar);

        profileimg = findViewById(R.id.profile_profileimg);

        cameraimg = findViewById(R.id.profile_fabadd);
        backimg = findViewById(R.id.profile_backimg);

        txtname = findViewById(R.id.profile_txtname);
        txtemail = findViewById(R.id.profile_txtemail);
        txtmob = findViewById(R.id.profile_txtmob);
        txtbranch = findViewById(R.id.profile_txtbranch);

        emaileditimg = findViewById(R.id.profile_emaileditimg);
        mobeditimg = findViewById(R.id.profile_mobeditimg);

    }

    public void getAdminDetails(){
        loginSession = new LoginSession(this);

        HashMap<String,String> adminDetails =  loginSession.getAdminDetailsFromSession();

        name = adminDetails.get(LoginSession.KEY_NAME);
        email = adminDetails.get(LoginSession.KEY_EMAIL);
        phone = adminDetails.get(LoginSession.KEY_PHONE);
        imageurl = adminDetails.get(LoginSession.KEY_IMAGE);
        branch = adminDetails.get(LoginSession.KEY_DEPT);

        txtname.setText(name);
        txtemail.setText(email);
        txtmob.setText(phone);
        txtbranch.setText(branch);

        Uri imageuri = Uri.parse(imageurl);
        Glide.with(this).load(imageuri).into(profileimg);

    }


}