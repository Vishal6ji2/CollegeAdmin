package com.mbm.mbmadmin;

import androidx.annotation.NonNull;

import com.mbm.mbmadmin.ModelResponse.AddResponses.AddAdminResponse;
import com.mbm.mbmadmin.ModelResponse.AddResponses.AddLinkResponse;
import com.mbm.mbmadmin.ModelResponse.AddResponses.AddNewsFeedResponse;
import com.mbm.mbmadmin.ModelResponse.AddResponses.AddNoticeResponse;
import com.mbm.mbmadmin.ModelResponse.AddResponses.AddPaperResponse;
import com.mbm.mbmadmin.ModelResponse.AddResponses.AddPlacementNewsResponse;
import com.mbm.mbmadmin.ModelResponse.AddResponses.AddSubjectResponse;
import com.mbm.mbmadmin.ModelResponse.AddResponses.AddSyllabusResponse;
import com.mbm.mbmadmin.ModelResponse.AddResponses.AddTimetableResponse;
import com.mbm.mbmadmin.ModelResponse.Login.GetOtpResponse;
import com.mbm.mbmadmin.ModelResponse.AddResponses.AddStudentResponse;
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
    Call<AddAdminResponse> addAdmin(
            @Field("email") String admin_email,
            @Field("mobile") String admin_mobile,
            @Field("name") String admin_name,
            @Field("dept_id") String dept_id
    );


    //add Student
    @NonNull
    @FormUrlEncoded
    @POST("student")
    Call<AddStudentResponse> addStudent(
            @Field("email") String student_email,
            @Field("mobile") String student_mobile,
            @Field("name") String student_name,
            @Field("reg_no") String student_regno,
            @Field("dept_id") String dept_id
    );

    //add Links
    @NonNull
    @FormUrlEncoded
    @POST("admin/link")
    Call<AddLinkResponse> addLink(
            @Field("name") String link_name,
            @Field("url") String link_url,
            @Field("admin_id") String admin_id

    );

    //update Links
    @NonNull
    @FormUrlEncoded
    @POST("admin/link")
    Call<AddLinkResponse> updateLink(
            @Field("id") String link_id,
            @Field("name") String link_name,
            @Field("url") String link_url,
            @Field("admin_id") String admin_id

    );


    //add Ebook
    @NonNull
    @FormUrlEncoded
    @POST("admin/ebook")
    Call<AddLinkResponse> addEbook(
            @Field("file_name") String book_pdf,
            @Field("author_name") String book_author,
            @Field("edition") String book_edition,
            @Field("name") String book_name,
            @Field("dept_id") String dept_id

    );

    //update Ebook
    @NonNull
    @FormUrlEncoded
    @POST("admin/ebook")
    Call<AddLinkResponse> updateEbook(
            @Field("id") String book_id,
            @Field("file_name") String book_pdf,
            @Field("author_name") String book_author,
            @Field("edition") String book_edition,
            @Field("name") String book_name,
            @Field("dept_id") String dept_id

    );


    //add NewsFeed
    @NonNull
    @FormUrlEncoded
    @POST("admin/newsfeed")
    Call<AddNewsFeedResponse> addNewsFeed(
            @Field("file_name") String news_images,
            @Field("title") String news_title,
            @Field("description") String news_description,
            @Field("admin_id") String admin_id,
            @Field("dept_id") String dept_id

    );

    //update NewsFeed
    @NonNull
    @FormUrlEncoded
    @POST("admin/newsfeed")
    Call<AddNewsFeedResponse> updateNewsFeed(
            @Field("id") String news_id,
            @Field("file_name") String news_images,
            @Field("title") String news_title,
            @Field("description") String news_description,
            @Field("admin_id") String admin_id,
            @Field("dept_id") String dept_id

    );


    //add Notice
    @NonNull
    @FormUrlEncoded
    @POST("admin/notice")
    Call<AddNoticeResponse> addNotice(
            @Field("file_name") String notice_images,
            @Field("title") String notice_title,
            @Field("description") String notice_description,
            @Field("admin_id") String admin_id,
            @Field("dept_id") String dept_id

    );

    //update Notice
    @NonNull
    @FormUrlEncoded
    @POST("admin/notice")
    Call<AddNoticeResponse> updateNotice(
            @Field("id") String notice_id,
            @Field("file_name") String notice_images,
            @Field("title") String notice_title,
            @Field("description") String notice_description,
            @Field("admin_id") String admin_id,
            @Field("dept_id") String dept_id

    );


    //add Placement News
    @NonNull
    @FormUrlEncoded
    @POST("admin/newsplacement")
    Call<AddPlacementNewsResponse> addPlacementNews(
            @Field("file_name") String placement_file,
            @Field("name") String placement_cmpname,
            @Field("title") String placement_title,
            @Field("news") String placement_description,
            @Field("admin_id") String admin_id,
            @Field("dept_id") String dept_id

    );

    //update Placement News
    @NonNull
    @FormUrlEncoded
    @POST("admin/notice")
    Call<AddPlacementNewsResponse> updatePlacementNews(
            @Field("id") String placement_id,
            @Field("file_name") String placement_images,
            @Field("name") String placement_cmpname,
            @Field("title") String placement_title,
            @Field("news") String placement_description,
            @Field("admin_id") String admin_id,
            @Field("dept_id") String dept_id

    );


//    add Timetable
    @NonNull
    @FormUrlEncoded
    @POST("admin/schedule")
    Call<AddTimetableResponse> addTimetable(
            @Field("file_name") String schedule_images,
            @Field("dept_id") String dept_id

    );

//    update Timetable
    @NonNull
    @FormUrlEncoded
    @POST("admin/schedule")
    Call<AddTimetableResponse> updateTimetable(
            @Field("id") String schedule_id,
            @Field("file_name") String schedule_images,
            @Field("dept_id") String dept_id

    );


//    add Subject
    @NonNull
    @FormUrlEncoded
    @POST("admin/subject")
    Call<AddSubjectResponse> addSubject(
            @Field("name") String subject_name,
            @Field("dept_id") String dept_id

    );

//    update Subject
    @NonNull
    @FormUrlEncoded
    @POST("admin/subject")
    Call<AddSubjectResponse> updateSubject(
            @Field("id") String subject_id,
            @Field("name") String subject_name,
            @Field("dept_id") String dept_id

    );



    //    add Syllabus
    @NonNull
    @FormUrlEncoded
    @POST("admin/syllabus")
    Call<AddSyllabusResponse> addSyllabus(
            @Field("name") String syllabus_name,
            @Field("file_name") String syllabus_file,
            @Field("dept_id") String dept_id

    );

    //    update Syllabus
    @NonNull
    @FormUrlEncoded
    @POST("admin/syllabus")
    Call<AddSyllabusResponse> updateSyllabus(
            @Field("id") String syllabus_id,
            @Field("name") String syllabus_name,
            @Field("file_name") String syllabus_file,
            @Field("dept_id") String dept_id

    );


    //    add Papers
    @NonNull
    @FormUrlEncoded
    @POST("admin/paper")
    Call<AddPaperResponse> addPaper(
            @Field("name") String paper_name,
            @Field("file_name") String paper_file,
            @Field("subject_id") String paper_subject_id,
            @Field("dept_id") String dept_id

    );

    //    update Paper
    @NonNull
    @FormUrlEncoded
    @POST("admin/paper")
    Call<AddPaperResponse> updatePaper(
            @Field("id") String paper_id,
            @Field("name") String paper_name,
            @Field("file_name") String paper_file,
            @Field("subject_id") String paper_subject_id,
            @Field("dept_id") String dept_id

    );






}
