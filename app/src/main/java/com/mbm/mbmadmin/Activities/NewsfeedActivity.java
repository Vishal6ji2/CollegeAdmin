package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.mbm.mbmadmin.Adapters.NewsPostAdapter;
import com.mbm.mbmadmin.FileUtils;
import com.mbm.mbmadmin.ModelResponse.NewsFeedResponse;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.RetrofitClient;
import com.mbm.mbmadmin.Suitcases.NewsPostSuitcase;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Multipart;

import static com.mbm.mbmadmin.ViewUtils.toast;

public class NewsfeedActivity extends AppCompatActivity {

    MaterialToolbar toolbar;

    ImageView backimg;

    RecyclerView recyclerView;

    ArrayList<NewsPostSuitcase> arrpostlist = new ArrayList<>();

    BottomSheetDialog bottomSheetDialog;

    OkHttpClient okHttpClient;

    Request request;

    Uri uri;
    //addnews views

    MaterialToolbar addtoolbar;
    ImageView cancelimg,newsimg,okimg;
    MaterialButton btnpost;

    FloatingActionButton fabcam;

    TextView txttitle,txtimg;
    ProgressBar progressBar;

    EditText edttitle, edtnews;
    String imagename,encodedimage;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        initviews();

        setSupportActionBar(toolbar);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        addPostData("Mukesh Singhavi","2h ago","26/10/2020","About Exam Scheduling","Our https://simpletexting.com/how-to-create-a-link-that-sends-an-sms-text-message/ exam is scheduled on 23 nov. 2020 where we'll start your exam at the center which mbm south campus in mbm of jodhpur ,rajasthan.",R.drawable.mbmlogo,0);

        addPostData("N.C. Barwar","5h ago","25/10/2020","About Timetable",null,R.drawable.profilefive,R.drawable.directorimg);

        addPostData("Simran choudhary","2d ago","10/10/2020","About Practical exam","Our exam is scheduled https://simpletexting.com/how-to-create-a-link-that-sends-an-sms-text-message/ on 23 nov. 2020 where we'll start your exam at the center which mbm south campus in mbm of jodhpur ,rajasthan.",R.drawable.profilefour,R.drawable.designerimg);

        addPostData("Aditya sawant","5d ago","12/10/2020","About Seminar","Our exam is scheduled on 23 nov. 2020 where we'll start your exam at the center which mbm south campus in mbm of jodhpur ,rajasthan.",R.drawable.mbmlogo,R.drawable.pictwo);

        addPostData("Anil gupta","7d ago","10/12/2020","About Mid-Term","Our exam is scheduled on 23 nov. 2020 where we'll start your exam at the center which mbm south campus in mbm of jodhpur ,rajasthan.",R.drawable.profilefour,R.drawable.editorimg);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NewsPostAdapter(this,arrpostlist));

    }


    public void addPostData(String profilename,String timeago,String datetime,String txtheading,String txtdetails, int profileimg, int postimg){

        NewsPostSuitcase newsPostSuitcase = new NewsPostSuitcase();

        newsPostSuitcase.profileimg = profileimg;
        newsPostSuitcase.postimg = postimg;

        newsPostSuitcase.profilename = profilename;
        newsPostSuitcase.datetime = datetime;
        newsPostSuitcase.timeago = timeago;
        newsPostSuitcase.txtheading = txtheading;
        newsPostSuitcase.txtdetails = txtdetails;

        arrpostlist.add(newsPostSuitcase);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menuaddwhite,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuadditemwhite){

            bottomSheetDialog = new BottomSheetDialog(NewsfeedActivity.this,R.style.BottomSheetDialogTheme);
            View bottomsheetview = LayoutInflater.from(NewsfeedActivity.this).inflate(R.layout.addnews_bottomsheet,(LinearLayout)findViewById(R.id.addnews_bottomsheetlayout));
            addnewsviews(bottomsheetview);


            cancelimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialog.dismiss();
                }
            });

            fabcam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, 10);
                }
            });

            edttitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    int cmpcount = edttitle.getText().length();
                    txttitle.setText(cmpcount +"/30");
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            btnpost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtnews.getText().toString().isEmpty()){
                        Toast.makeText(NewsfeedActivity.this,"Enter Post Details",Toast.LENGTH_SHORT).show();
                    } else {
                        uploadpost();
                    }
                }
            });

            bottomSheetDialog.setContentView(bottomsheetview);
            bottomSheetDialog.show();
            return true;
        }
        return false;
    }

    @NonNull
    public MultipartBody.Part prepareFilePart(@NonNull String partname, @NonNull Uri fileURi){
        File file=new File(FileUtils.getFilePathFromURI(this,fileURi));
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("news_imagepath", file.getName(), requestBody);

        RequestBody newstitle = RequestBody.create(MediaType.parse("text/plain"), edttitle.getText().toString());
        RequestBody newsfeed = RequestBody.create(MediaType.parse("text/plain"), edtnews.getText().toString());
        RequestBody adminname = RequestBody.create(MediaType.parse("text/plain"), "vishal");
        RequestBody deptid = RequestBody.create(MediaType.parse("text/plain"), "1");

//        RequestBody requestBody = RequestBody.create(MediaType.parse(Objects.requireNonNull(getContentResolver().getType(fileURi))),file);
        return MultipartBody.Part.createFormData(partname,file.getName(),requestBody);
    }

    @SuppressLint("LogConditional")
    void uploadpost() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.VISIBLE);

        okHttpClient = new OkHttpClient();

        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (uri != null) {
            File file = new File(FileUtils.getFilePathFromURI(this, uri));


            Log.d("okhttpclient", "called actual request");


            builder.setType(MultipartBody.FORM);

            builder.addFormDataPart("news_imagepath",file.getName(), RequestBody.create(MediaType.parse("image/jpg"),file));
        }else{
            builder.addFormDataPart("news_imagepath",null);
        }
        builder.addFormDataPart("news_title", edttitle.getText().toString());
        builder.addFormDataPart("news_paragraph", edtnews.getText().toString());
        builder.addFormDataPart("admin_name", "Vishal Kumavat");
        builder.addFormDataPart("dept_id", "2");
        builder.build();

        Log.d("okhttpclient", "request body generated");

        request = new Request.Builder()
                .url("https://mbmvishal.000webhostapp.com/new_feed.php")
                .post(builder.build())
                .build();

        Log.d("okhttpclient", "called request");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    assert response.body() != null;
                    Log.d("okhttpclient", response.body().string());
//                            progressBar.setVisibility(View.GONE);
                } catch (IOException e) {
                    Log.d("okhttpclient", "Exception occured" + e.toString());
//                            progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        }).start();

        progressBar.setVisibility(View.GONE);

       /* RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("news_imagepath", file.getName(), requestBody);

        RequestBody newstitle = RequestBody.create(MediaType.parse("text/plain"), edttitle.getText().toString());
        RequestBody newsfeed = RequestBody.create(MediaType.parse("text/plain"), edtnews.getText().toString());
        RequestBody adminname = RequestBody.create(MediaType.parse("text/plain"), "vishal");
        RequestBody deptid = RequestBody.create(MediaType.parse("text/plain"), "1");
*/


//        Call<NewsFeedResponse> newsFeedResponseCall = RetrofitClient.getInstance()
//                .getapi()
//                .newsupload(fileToUpload,newstitle,newsfeed,adminname,deptid);

        /*newsFeedResponseCall.enqueue(new Callback<NewsFeedResponse>() {
            @Override
            public void onResponse(@NotNull Call<NewsFeedResponse> call, @NotNull Response<NewsFeedResponse> response) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                NewsFeedResponse newsFeedResponse = response.body();
                if (newsFeedResponse != null) {
                    if (!newsFeedResponse.getError()) {
                        toast(NewsfeedActivity.this, newsFeedResponse.getMessage());
                    } else {
                        toast(NewsfeedActivity.this, newsFeedResponse.getMessage());
                    }
                }
            }

            @SuppressLint("LogConditional")
            @Override
            public void onFailure(@NotNull Call<NewsFeedResponse> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("ApiResponse>>","ApiCall>> "+call.request());
//                Log.d("ApiResponse>>","ApiCall>> "+new Gson().toJson(call.request()));
                Log.d("ApiResponse>>","ApiResponse>> "+new Gson().toJson(call.request()));
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                Log.d("newsdatafail",t.getMessage()+"\n"+new Gson().toJson(call.request().body())+"\n"+call.request().body());
                Toast.makeText(NewsfeedActivity.this, "News post upload failed", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });
*/
    }

    public void addnewsviews(@NonNull View bottomsheetview){

        addtoolbar = bottomsheetview.findViewById(R.id.addnews_toolbar);

        btnpost = bottomsheetview.findViewById(R.id.addnews_btnpost);

        cancelimg = bottomsheetview.findViewById(R.id.addnews_backimg);
        newsimg = bottomsheetview.findViewById(R.id.addnews_newsimg);
        okimg  = bottomsheetview.findViewById(R.id.addnews_okimg);

        edttitle = bottomsheetview.findViewById(R.id.addnews_edttitle);
        edtnews = bottomsheetview.findViewById(R.id.addnews_edtnews);

        fabcam = bottomsheetview.findViewById(R.id.addnews_fabcam);

        txtimg = bottomsheetview.findViewById(R.id.addnews_txtimg);
        txttitle = bottomsheetview.findViewById(R.id.addnews_txttitle);
        progressBar = bottomsheetview.findViewById(R.id.addnews_progressbar);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {

            uri = data.getData();

            imagename =  getimagename(uri,data);
            Log.d("Image","imageuri->"+ uri);
            /*try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), uri));
                }else {
                    bitmap =  MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                }*/
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
//                byte[] byteimg = baos.toByteArray();
//                Log.d("Image","bitmap->"+bitmap.toString());
//                Log.d("Image", "bytearray->"+ byteimg.toString());

//                encodedimage = getRealPathFromURI(this,uri);
//                encodedimage = Base64.encodeToString(byteimg,Base64.DEFAULT);
//                Log.d("Image","Base 64 string->"+encodedimage);
//                newsimg.setImageBitmap(bitmap);
                txtimg.setVisibility(View.GONE);
//                Uri urii = Uri.parse(url);
                Glide.with(this).load(uri).into(newsimg);

              /*} catch (IOException e) {
                  e.printStackTrace();
              }*/
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void SetUtils(){

        edtnews.setText("");
        edttitle.setText("");
        newsimg.setImageBitmap(null);

    }

    public String getimagename(Uri uri, Intent data){
        String filename = null;
        if (data != null) {
            uri = data.getData();

            String uristring = uri.toString();
            File file = new File(uristring);
            String mypath = file.getAbsolutePath();


            if (uristring.startsWith("content://")) {
                Cursor cursor = null;

                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        filename = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("filename",filename);
                    }

                } finally {
                    cursor.close();
                }
            } else if (uristring.startsWith("file://")) {
                filename = file.getName();
                Log.d("filename",filename);
            }
        }
        return filename;
    }

    private void initviews() {

        toolbar = findViewById(R.id.newsfeed_toolbar);

        backimg = findViewById(R.id.newsfeed_backimg);

        recyclerView = findViewById(R.id.newsfeed_recyclerview);
    }
}