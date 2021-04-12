package com.mbm.mbmadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mbm.mbmadmin.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    MaterialButton btnlogin;
    TextView txtregister, txtsend;
    TextInputEditText edtemail, edtotp;
    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initviews();

        edtotp.setEnabled(false);

        txtsend.setPaintFlags(txtsend.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtemail.getText().toString().equals("") && edtotp.getText().toString().equals("")) {
                    toast = Toast.makeText(LoginActivity.this,"Please enter your credentials",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    toast = Toast.makeText(LoginActivity.this,"Login Successfully",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
            }
        });

        txtsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtotp.setEnabled(true);
            }
        });

        edtotp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int otpcount = Objects.requireNonNull(edtotp.getText()).length();
                if (otpcount==6){
                    btnlogin.setEnabled(true);
                }else {
                    btnlogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
    }

    private void initviews() {

        btnlogin = findViewById(R.id.login_btnlogin);

        txtregister = findViewById(R.id.login_txtregister);
        txtsend = findViewById(R.id.login_txtsendotp);

        edtemail = findViewById(R.id.login_edtemailid);
        edtotp = findViewById(R.id.login_edtpassword);

    }
}