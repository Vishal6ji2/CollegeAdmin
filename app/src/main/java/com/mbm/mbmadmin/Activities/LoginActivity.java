package com.mbm.mbmadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mbm.mbmadmin.R;

public class LoginActivity extends AppCompatActivity {

    MaterialButton btnlogin;
    TextView txtregister, txtforgot;
    TextInputEditText edtemail, edtpassword;
    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initviews();


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtemail.getText().toString().equals("") && edtpassword.getText().toString().equals("")) {
                    toast = Toast.makeText(LoginActivity.this,"Please enter your credentials",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    toast = Toast.makeText(LoginActivity.this,"Please enter your credentials",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
            }
        });

        txtforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotpwdActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        txtregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    private void initviews() {

        btnlogin = findViewById(R.id.login_btnlogin);

        txtregister = findViewById(R.id.login_txtregister);
        txtforgot = findViewById(R.id.login_txtforgotpwd);

        edtemail = findViewById(R.id.login_edtemailid);
        edtpassword = findViewById(R.id.login_edtpassword);

    }
}