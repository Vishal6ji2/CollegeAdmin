package com.mbm.mbmadmin;

import androidx.annotation.NonNull;

import com.mbm.mbmadmin.ModelResponse.AdminAddResponse;
import com.mbm.mbmadmin.ModelResponse.GetOtpResponse;
import com.mbm.mbmadmin.ModelResponse.StudentAddResponse;
import com.mbm.mbmadmin.Suitcases.GetAdminStudentsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    //login.php

    @NonNull
    @FormUrlEncoded
    @POST("admin/sendotp")
    Call<GetOtpResponse> sendOtp(
            @Field("email") String admin_email
    );

    @NonNull
    @FormUrlEncoded
    @POST("admin/verifyotp")
    Call<GetAdminStudentsResponse> otpVerify(
            @Field("id") int admin_id,
            @Field("otp") String admin_otp
    );


    //add Admin
    @NonNull
    @FormUrlEncoded
    @POST("admin")
    Call<AdminAddResponse> addAdmin(
            @Field("email") String admin_email,
            @Field("mobile") String admin_mobile,
            @Field("name") String admin_name,
            @Field("dept_id") String dept_id
    );


    //add Student
    @NonNull
    @FormUrlEncoded
    @POST("student")
    Call<StudentAddResponse> addStudent(
            @Field("email") String student_email,
            @Field("mobile") String student_mobile,
            @Field("name") String student_name,
            @Field("reg_no") String student_regno,
            @Field("dept_id") String dept_id
    );

}
