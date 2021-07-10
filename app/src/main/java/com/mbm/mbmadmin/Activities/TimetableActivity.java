package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.TimetableFetchResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class TimetableActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    MaterialToolbar toolbar;

    ProgressBar progressBar;

    ImageView backimg;

    ShimmerFrameLayout shimmerFrameLayout;

    ArrayList<TimetableFetchResponse.Timetable> arrtimelist = new ArrayList<>();

    String encodedimage;


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        initviews();

        setSupportActionBar(toolbar);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        addTimetableData();

    }
/*

    private void addTimetableData() {

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();

        recyclerView.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Call<TimetableFetchResponse> responseCall = RetrofitClient.getInstance().getapi().fetchTimetableData();

        responseCall.enqueue(new Callback<TimetableFetchResponse>() {
            @Override
            public void onResponse(Call<TimetableFetchResponse> call, Response<TimetableFetchResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        arrtimelist = response.body().getTimetables();

                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        recyclerView.setAdapter(new TimetableAdapter(TimetableActivity.this, arrtimelist));

                    } else {
                        toast(TimetableActivity.this,response.message());
                    }
                }else {
                    toast(TimetableActivity.this,response.message());
                }
            }

            @SuppressLint("LogConditional")
            @Override
            public void onFailure(Call<TimetableFetchResponse> call, Throwable t) {
                toast(TimetableActivity.this, Objects.requireNonNull(t.getLocalizedMessage()));
                Log.d("timetablefetchfail",t.getMessage());

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.VISIBLE);

        if (requestCode == 10 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                if (uri != null) {
                    InputStream is = getContentResolver().openInputStream(uri);
                    byte[] bytesArray;
                    if (is != null) {
                        bytesArray = new byte[is.available()];
                        is.read(bytesArray);

                        Log.d("ttimagedata", "bytearray->" + bytesArray.toString());

                        encodedimage = Base64.encodeToString(bytesArray, Base64.DEFAULT);
                        Log.d("ttimagedata", "\nBase 64 string->" + encodedimage);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

//            uploadTimetable(encodedimage,data);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
/*
    private void uploadTimetable(String encodedimage,Intent data) {

        Call<TimetableResponse> timetableResponseCall = RetrofitClient.getInstance()
                .getapi()
                .timetableupload(encodedimage,getimagename(data.getData(),data),"1");

        timetableResponseCall.enqueue(new Callback<TimetableResponse>() {
            @Override
            public void onResponse(Call<TimetableResponse> call, Response<TimetableResponse> response) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                TimetableResponse timetableResponse = response.body();
                if (timetableResponse != null && response.isSuccessful()) {
                    Toast.makeText(TimetableActivity.this, timetableResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("ttdata",timetableResponse.getMessage());

                   *//* if (timetableResponse.getMessage().equals("Timetable image Uploaded Successfully")){

                    }else {
                        Log.d("ttdata","\n"+timetableResponse.getError());
                    }*//*
                }*//*else{
                    Toast.makeText(TimetableActivity.this,timetableResponse.getMessage(),Toast.LENGTH_SHORT).show();

                }*//*
            }

            @SuppressLint("LogConditional")
            @Override
            public void onFailure(@NotNull Call<TimetableResponse> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Log.d("ttfail",t.getMessage()+"\n"+new Gson().toJson(call.request().body())+"\n"+call.request().body());
                Toast.makeText(TimetableActivity.this, "Timetable image upload failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

   */ public String getimagename(Uri uri, Intent data){
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

        shimmerFrameLayout = findViewById(R.id.tt_shimmerlayout);

        recyclerView = findViewById(R.id.tt_recyclerview);

        toolbar = findViewById(R.id.tt_toolbar);

        progressBar = findViewById(R.id.tt_progressbar);

        backimg = findViewById(R.id.tt_backimg);
    }
}