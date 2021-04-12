package com.mbm.mbmadmin;

import androidx.annotation.NonNull;

import com.mbm.mbmadmin.ModelResponse.DeptFetchResponse;
import com.mbm.mbmadmin.ModelResponse.EbookResponse;
import com.mbm.mbmadmin.ModelResponse.NewsFeedResponse;
import com.mbm.mbmadmin.ModelResponse.NoticeResponse;
import com.mbm.mbmadmin.ModelResponse.PlacementnewsResponse;
import com.mbm.mbmadmin.ModelResponse.StudentsExcelResponse;
import com.mbm.mbmadmin.ModelResponse.TimetableResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

   /* //register.php
    @FormUrlEncoded
    @POST("student_register.php")
    Call<RegisterResponse> register(
            @Field("stud_name") String stud_name,
            @Field("stud_email") String stud_email,
            @Field("stud_pass") String stud_pass,
            @Field("stud_mob") String stud_mob,
            @Field("stud_branch") String stud_branch
    );

    //login.php
    @FormUrlEncoded
    @POST("student_login.php")
    Call<LoginResponse> login(
            @Field("stud_email") String stud_email,
            @Field("stud_pass") String stud_pass
    );*/


    @NonNull
    @POST("new_feed.php")
    @Multipart
    Call<NewsFeedResponse> newsupload(  MultipartBody body
            /*@Part MultipartBody.Part news_image,
//            @Part("news_imagename") String news_imagename,
            @Part("news_title") RequestBody news_title,
            @Part("news_paragraph") RequestBody news_paragraph,
            @Part("admin_name") RequestBody admin_name,
            @Part("dept_id") RequestBody dept_id*/
    );

    @NonNull
    @FormUrlEncoded
    @POST("notice.php")
    Call<NoticeResponse> noticeupload(
            @Field("dept_id") String dept_id,
            @Field("notice_image_data") String imagedata,
            @Field("notice_image_name") String imagename

    );

    @NonNull
    @FormUrlEncoded
    @POST("adm_import_file_save.php")
    Call<StudentsExcelResponse> excelupload(
            @Field("file_data") String exceldata,
            @Field("file_name") String filename
    );

    @FormUrlEncoded
    @POST("placement_news.php")
    Call<PlacementnewsResponse> placementnewsupload(
            @Field("cmp_name") String cmpname,
            @Field("cmp_filedata") String cmpfiledata,
            @Field("cmp_filename") String cmpfilename,
            @Field("cmp_title") String cmptitle,
            @Field("cmp_news") String cmpnews,
            @Field("dept_id") String deptid,
            @Field("cmp_uploadeby") String uploadedby
    );

    /*private fun prepareFilePart(
        partName: String,
        fileUri: Uri
    ): MultipartBody.Part? {
        val file = File(RealPathUtils.getPath(context!!, fileUri))
        val requestBody: RequestBody =
            RequestBody.create(MediaType.parse(contentResolver.getType(fileUri)), file)
        return MultipartBody.Part.createFormData(partName, file.name, requestBody)
    }
@Multipart
    @POST("save-arrest")
    fun addArrest(
        @PartMap params: MutableMap<String, RequestBody?>,
        @Part file: List<MultipartBody.Part>
    ): Call<JsonObject>*/

    @NonNull
    @POST("ebook.php")
    @Multipart
    Call<EbookResponse> ebookupload(
//            @Part("name") @NonNull RequestBody bookname,
            @Part("book_showname") @NonNull String bookshowname,
            @Part("book_authorname") @NonNull String book_authorname,
            @Part("book_edition") @NonNull String bookedition,
            @Part @NonNull MultipartBody.Part bookpdf,
            @Part("dept_id") @NonNull String dept_id



    );

    @FormUrlEncoded
    @POST("timetable.php")
    Call<TimetableResponse> timetableupload(
            @Field("image_data") String ttimagedata,
            @Field("image_name") String ttimagename,
            @Field("dept_id") String dept_id
    );


    //DATA FETCH APIS
    @POST("depatmentfetch.php")
    Call<List<DeptFetchResponse>> fetchdeptdata();
}
