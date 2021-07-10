package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mbm.mbmadmin.ModelResponse.Login.GetOtpResponse;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mbm.mbmadmin.ViewUtils.toast;

public class EmailVerifyActivity extends AppCompatActivity {


    public static final String TAG = "EmailVerifyActivity";

    TextInputEditText edtEmail;

    MaterialButton btnGetOtp;

    ProgressBar progressBar;


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verify);


        initViews();

        progressBar.setVisibility(View.GONE);

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!edtEmail.getText().toString().equals("")){
                    btnGetOtp.setEnabled(true);
                }else {
                    btnGetOtp.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtEmail.getText().toString().equals("")){
                    edtEmail.setError("Please Enter Email id");
                }else {
                    sendOtpToEmail();
                }
            }
        });

    }


    public void sendOtpToEmail(){

        progressBar.setVisibility(View.VISIBLE);
        Call<GetOtpResponse> getOtpResponseCall = RetrofitClient.getInstance().getapi().sendOtp(edtEmail.getText().toString());

        getOtpResponseCall.enqueue(new Callback<GetOtpResponse>() {
            @Override
            public void onResponse(Call<GetOtpResponse> call, Response<GetOtpResponse> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()){
                    if (response.body() != null){
                       if (response.body().getStatus() == 1){

                           Log.d(TAG ,response.body().getMessage());

                           toast(EmailVerifyActivity.this,response.body().getMessage());
                           Intent intent = new Intent(EmailVerifyActivity.this, OtpVerifyActivity.class);
                           intent.putExtra("email",edtEmail.getText().toString());
                           intent.putExtra("data",response.body().getData());
                           startActivity(intent);

                       }else {
                           edtEmail.setError(response.body().getMessage());
                           toast(EmailVerifyActivity.this,response.message());
                           Log.d(TAG ,response.body().getMessage());
                       }

                    }else {
                        toast(EmailVerifyActivity.this,response.message());
                        Log.d(TAG ,response.message());
                    }
                }
            }

            @SuppressLint("LogConditional")
            @Override
            public void onFailure(Call<GetOtpResponse> call, Throwable t) {

                progressBar.setVisibility(View.GONE);

                toast(EmailVerifyActivity.this,"Something wents wrong");
                Log.d(TAG,t.getLocalizedMessage());
            }
        });


    }

    private void initViews() {

        edtEmail = findViewById(R.id.email_edtemailid);

        btnGetOtp = findViewById(R.id.email_btnget);

        progressBar = findViewById(R.id.email_progressbar);

    }
}