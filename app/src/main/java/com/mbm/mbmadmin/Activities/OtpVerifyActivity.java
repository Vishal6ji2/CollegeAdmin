package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;

import static com.mbm.mbmadmin.ViewUtils.toast;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.mbm.mbmadmin.ModelResponse.GetOtpResponse;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.RetrofitClient;
import com.mbm.mbmadmin.Sessions.LoginSession;
import com.mbm.mbmadmin.Sessions.StudentsListSession;
import com.mbm.mbmadmin.Suitcases.GetAdminStudentsResponse;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OtpVerifyActivity extends AppCompatActivity {

    public static final String TAG = "VerifyOtpActivity";

    MaterialToolbar toolbar;

    MaterialButton btnVerify;

    TextView txtSend,txtTime,txtEmail;

    CountDownTimer countDownTimer;

    EditText edtOtp1,edtOtp2,edtOtp3,edtOtp4,edtOtp5,edtOtp6;

    String name, email, phone, branch, imageUrl, branchId, status, adminId, super_admin;

    ProgressBar progressBar;

    LoginSession loginSession;

    Intent intent;

    String intentEmail,strOtp;

    StudentsListSession studentsListSession;

    int inputData;


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify);


        initViews();

        sessionOtpFailed();

        txtSend.setEnabled(false);
        txtSend.setTextColor(ContextCompat.getColor(this,R.color.colordarkgrey));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        intent = getIntent();

        if (intent != null){
            if (intent.getStringExtra("email") != null){
                intentEmail = intent.getStringExtra("email");
                txtEmail.setText(intent.getStringExtra("email"));
                inputData = intent.getIntExtra("data",0);
            }
        }

        progressBar.setVisibility(View.GONE);

        btnVerify.setEnabled(false);

        setupOtpInputs();

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtOtp1.getText().toString().trim().isEmpty()||
                    edtOtp2.getText().toString().trim().isEmpty()||
                    edtOtp3.getText().toString().trim().isEmpty()||
                    edtOtp4.getText().toString().trim().isEmpty()||
                    edtOtp5.getText().toString().trim().isEmpty()||
                    edtOtp6.getText().toString().trim().isEmpty()){

                    toast(OtpVerifyActivity.this,"Please enter OTP");

                } else{
                    strOtp = edtOtp1.getText().toString()+
                             edtOtp2.getText().toString()+
                             edtOtp3.getText().toString()+
                             edtOtp4.getText().toString()+
                             edtOtp5.getText().toString()+
                             edtOtp6.getText().toString();

                    Log.d(TAG,strOtp+"\totp");
                    verifyOtpResponse(strOtp);
                }
            }
        });

        txtSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOtpToEmail();

            }
        });


    }

    void sessionOtpFailed() {

        txtTime.setVisibility(View.VISIBLE);

       countDownTimer =  new CountDownTimer(3*60*1000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                String sDuration = String.format(Locale.ENGLISH, "%02d:%02d"
                ,TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                ,TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                txtTime.setText(sDuration);

            }
            @Override
            public void onFinish() {

                txtTime.setVisibility(View.GONE);

                txtSend.setEnabled(true);
                txtSend.setTextColor(ContextCompat.getColor(OtpVerifyActivity.this,R.color.colorAccent));

            }
        }.start();

    }



    public void sendOtpToEmail(){

        progressBar.setVisibility(View.VISIBLE);
        Call<GetOtpResponse> getOtpResponseCall = RetrofitClient.getInstance().getapi().sendOtp(intentEmail);

        getOtpResponseCall.enqueue(new Callback<GetOtpResponse>() {
            @Override
            public void onResponse(Call<GetOtpResponse> call, Response<GetOtpResponse> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()){
                    if (response.body() != null){
                        if (response.body().getStatus() == 1){

                            txtSend.setEnabled(false);
                            txtSend.setTextColor(ContextCompat.getColor(OtpVerifyActivity.this,R.color.colordarkgrey));

                            inputData = response.body().getData();

                            Log.d(TAG ,response.body().getMessage());
                            sessionOtpFailed();

                            toast(OtpVerifyActivity.this,response.body().getMessage());


                        }else {
                            toast(OtpVerifyActivity.this,response.body().getMessage());
                            Log.d(TAG ,response.body().getMessage());
                        }

                    }else {
                        toast(OtpVerifyActivity.this,response.message());
                        Log.d(TAG ,response.message());
                    }
                }
            }

            @SuppressLint("LogConditional")
            @Override
            public void onFailure(Call<GetOtpResponse> call, Throwable t) {

                progressBar.setVisibility(View.GONE);

                toast(OtpVerifyActivity.this,"Something wents wrong");
                Log.d(TAG,t.getLocalizedMessage());
            }
        });


    }


    public void setupOtpInputs(){

        edtOtp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    edtOtp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtOtp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    edtOtp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtOtp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    edtOtp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtOtp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    edtOtp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtOtp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    edtOtp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtOtp6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    btnVerify.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void verifyOtpResponse(@NonNull String otp){

        Call<GetAdminStudentsResponse> otpVerifyCall = RetrofitClient.getInstance()
                .getapi()
                .otpVerify(inputData,otp);

        progressBar.setVisibility(View.VISIBLE);

        otpVerifyCall.clone().enqueue(new Callback<GetAdminStudentsResponse>() {
            @Override
            public void onResponse(Call<GetAdminStudentsResponse> call, Response<GetAdminStudentsResponse> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1){
                            toast(OtpVerifyActivity.this,response.body().getMessage());
                            countDownTimer.cancel();
                            getProfileDetails(response.body().getData().getAdmin());
                            getStudentsList(response.body().getData().getStudent());

                            Log.d(TAG,response.body().getMessage());
                        }else {
                            toast(OtpVerifyActivity.this,response.body().getMessage());
                            Log.d(TAG,response.body().getMessage());
                        }

                    }else {
                        toast(OtpVerifyActivity.this,response.message());
                        Log.d(TAG,response.message());
                    }
                }
            }

            @SuppressLint("LogConditional")
            @Override
            public void onFailure(Call<GetAdminStudentsResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                toast(OtpVerifyActivity.this,"Something wents wrong");
                Log.d(TAG,t.getLocalizedMessage());

            }
        });

    }

    public void getStudentsList(@NonNull ArrayList<GetAdminStudentsResponse.Student> studentArrayList){

        studentsListSession = new StudentsListSession(OtpVerifyActivity.this);

        studentsListSession.saveStudentsList(studentArrayList);

        startActivity(new Intent(OtpVerifyActivity.this,HomeActivity.class));

        finish();
    }

    public void getProfileDetails(@NonNull ArrayList<GetAdminStudentsResponse.Admin> profiles){

        loginSession = new LoginSession(OtpVerifyActivity.this);

        name = profiles.get(0).getName();
        email = profiles.get(0).getEmail();
        phone = profiles.get(0).getMobile();
        branch = profiles.get(0).getDepartmentName();
        branchId = profiles.get(0).getDeptId();
        adminId = profiles.get(0).getId();
        imageUrl = profiles.get(0).getImageUrl();
        status = profiles.get(0).getStatus();
        super_admin = profiles.get(0).getSuperAdmin();

        loginSession.createLoginSession(super_admin,name,email,phone,branch,imageUrl,branchId,status,adminId);

    }


    private void initViews() {

        toolbar = findViewById(R.id.otp_toolbar);

        progressBar = findViewById(R.id.otp_progressbar);

        btnVerify = findViewById(R.id.otp_btnverify);

        txtSend = findViewById(R.id.otp_txtsendotp);
        txtTime = findViewById(R.id.otp_txttime);
        txtEmail = findViewById(R.id.otp_txtemail);

        edtOtp1 = findViewById(R.id.otp_edtcode1);
        edtOtp2 = findViewById(R.id.otp_edtcode2);
        edtOtp3 = findViewById(R.id.otp_edtcode3);
        edtOtp4 = findViewById(R.id.otp_edtcode4);
        edtOtp5 = findViewById(R.id.otp_edtcode5);
        edtOtp6 = findViewById(R.id.otp_edtcode6);

    }
}