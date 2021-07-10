package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.NoticeFetchResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class NoticeActivity extends AppCompatActivity {

    MaterialToolbar toolbar;

    RecyclerView recyclerView;

    ShimmerFrameLayout shimmerFrameLayout;

    ImageView backimg;

    ArrayList<NoticeFetchResponse.Noticetable> arrnoticelist = new ArrayList<>();

    String imagename,encodedimage;
    Bitmap bitmap;

    ProgressBar progressBar;


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        initviews();

        setSupportActionBar(toolbar);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        addNoticeData();

    }
/*

    private void addNoticeData() {

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
        recyclerView.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Call<NoticeFetchResponse> responseCall = RetrofitClient.getInstance().getapi().fetchNoticeData();
        responseCall.enqueue(new Callback<NoticeFetchResponse>() {
            @Override
            public void onResponse(Call<NoticeFetchResponse> call, Response<NoticeFetchResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        arrnoticelist = response.body().getNoticetable();

                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        recyclerView.setAdapter(new NoticeAdapter(NoticeActivity.this, arrnoticelist));

                    } else {
                        toast(NoticeActivity.this,response.message());
                    }
                }else {
                    toast(NoticeActivity.this,response.message());
                }
            }

            @SuppressLint("LogConditional")
            @Override
            public void onFailure(Call<NoticeFetchResponse> call, Throwable t) {
                toast(NoticeActivity.this, Objects.requireNonNull(t.getLocalizedMessage()));
                Log.d("noticefetchfail",t.getMessage());

            }
        });
    }

*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menuaddwhite,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuadditemwhite){

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,10);

            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            imagename = getimagename(uri, data);
            Log.d("Image", "imageuri->" + uri);
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), uri));
                } else {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] byteimg = baos.toByteArray();
                Log.d("noticeImage", "bitmap->" + bitmap.toString());
                Log.d("noticeImage", "bytearray->" + byteimg.toString());

                encodedimage = Base64.encodeToString(byteimg, Base64.DEFAULT);
                Log.d("noticeImage", "Base 64 string->" + encodedimage);

//                uploadnoticepost();

            } catch (IOException e) {
                e.printStackTrace();
            }

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initviews () {

        shimmerFrameLayout = findViewById(R.id.notice_shimmerlayout);

        progressBar = findViewById(R.id.notice_progressbar);

        toolbar = findViewById(R.id.notice_toolbar);

        backimg = findViewById(R.id.notice_backimg);

        recyclerView = findViewById(R.id.notice_recyclerview);
    }

/*
    private void uploadnoticepost() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.VISIBLE);

        final Call<NoticeResponse> noticeResponseCall = RetrofitClient.getInstance()
                .getapi()
                .noticeupload("1",encodedimage,imagename);

        Log.d("imagename",imagename);

        noticeResponseCall.enqueue(new Callback<NoticeResponse>() {
            @Override
            public void onResponse(Call<NoticeResponse> call, Response<NoticeResponse> response) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                NoticeResponse noticeResponse = response.body();
                if (noticeResponse != null && response.isSuccessful()) {
                    Toast.makeText(NoticeActivity.this, noticeResponse.getMessage(), Toast.LENGTH_SHORT).show();


                    if (noticeResponse.getMessage().equals("Notice Uploaded")){
                        Log.d("noticedata",noticeResponse.getMessage());
                    }else {
                        Log.d("noticedata","\n"+noticeResponse.getError());
                    }

                }else{
                    Toast.makeText(NoticeActivity.this,noticeResponse.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }

            @SuppressLint("LogConditional")
            @Override
            public void onFailure(Call<NoticeResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Log.d("noticedatafail",t.getMessage()+"\n"+new Gson().toJson(call.request().body())+"\n"+call.request().body());
                Toast.makeText(NoticeActivity.this, "Notice upload failed", Toast.LENGTH_SHORT).show();

            }
        });


    }

*/

        public String getimagename (Uri uri, Intent data){
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
                            Log.d("filename", filename);
                        }

                    } finally {
                        cursor.close();
                    }
                } else if (uristring.startsWith("file://")) {
                    filename = file.getName();
                    Log.d("filename", filename);
                }
            }
            return filename;
        }

    }
