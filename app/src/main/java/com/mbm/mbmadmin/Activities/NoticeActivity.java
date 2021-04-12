package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.mbm.mbmadmin.Adapters.NoticeAdapter;
import com.mbm.mbmadmin.ModelResponse.NewsFeedResponse;
import com.mbm.mbmadmin.ModelResponse.NoticeResponse;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.RetrofitClient;
import com.mbm.mbmadmin.Suitcases.NoticeSuitcase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeActivity extends AppCompatActivity {

    MaterialToolbar toolbar;

    RecyclerView recyclerView;

    ImageView backimg;

    ArrayList<NoticeSuitcase> arrnoticelist = new ArrayList<>();

    String imagename,encodedimage;
    Bitmap bitmap;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        initviews();


        setSupportActionBar(toolbar);


        addNoticeData(R.drawable.picone,"Paragraph Writing (अनुच्छेद-लेखन) - इस लेख में हम अनुच्छेद-लेखन के बारे में जानेंगे। अनुच्छेद-लेखन होता क्या है? अनुच्छेद लिखते समय किन-किन बातों का ध्यान रखना चाहिए? अनुच्छेद की प्रमुख विशेषताएँ कौन-कौन से हैं? और साथ ही इस लेख में हम कुछ अनुच्छेद अदाहरण के रूप में भी दे रहे हैं -");
        addNoticeData(R.drawable.pictwo,"Paragraph Writing (अनुच्छेद-लेखन) - इस लेख में हम अनुच्छेद-लेखन के बारे में जानेंगे। अनुच्छेद-लेखन होता क्या है? अनुच्छेद लिखते समय किन-किन बातों का ध्यान रखना चाहिए? अनुच्छेद की प्रमुख विशेषताएँ कौन-कौन से हैं? और साथ ही इस लेख में हम कुछ अनुच्छेद अदाहरण के रूप में भी दे रहे हैं -");
        addNoticeData(R.drawable.designerimg,"किसी एक भाव या विचार को व्यक्त करने के लिए लिखे गये सम्बद्ध और लघु वाक्य-समूह को अनुच्छेद-लेखन कहते हैं।\n" +
                "दूसरे शब्दों में - किसी घटना, दृश्य अथवा विषय को संक्षिप्त (कम शब्दों में) किन्तु सारगर्भित (अर्थपूर्ण) ढंग से जिस लेखन-शैली में प्रस्तुत किया जाता है, उसे अनुच्छेद-लेखन कहते हैं।\n" +
                "'अनुच्छेद' शब्द अंग्रेजी भाषा के 'Paragraph' शब्द का हिंदी पर्याय है। अनुच्छेद 'निबंध' का संक्षिप्त रूप होता है। इसमें किसी विषय के किसी एक पक्ष पर 80 से 100 शब्दों में अपने विचार व्यक्त किए जाते हैं।\n" +
                "अनुच्छेद अपने-आप में स्वतन्त्र और पूर्ण होते हैं। अनुच्छेद का मुख्य विचार या भाव की कुंजी या तो आरम्भ में रहती है या अन्त में। एक अच्छे अनुच्छेद-लेखन में मुख्य विचार अन्त में दिया जाता है।");
        addNoticeData(R.drawable.directorimg,"किसी एक भाव या विचार को व्यक्त करने के लिए लिखे गये सम्बद्ध और लघु वाक्य-समूह को अनुच्छेद-लेखन कहते हैं।\n" +
                "दूसरे शब्दों में - किसी घटना, दृश्य अथवा विषय को संक्षिप्त (कम शब्दों में) किन्तु सारगर्भित (अर्थपूर्ण) ढंग से जिस लेखन-शैली में प्रस्तुत किया जाता है, उसे अनुच्छेद-लेखन कहते हैं।\n" +
                "'अनुच्छेद' शब्द अंग्रेजी भाषा के 'Paragraph' शब्द का हिंदी पर्याय है। अनुच्छेद 'निबंध' का संक्षिप्त रूप होता है। इसमें किसी विषय के किसी एक पक्ष पर 80 से 100 शब्दों में अपने विचार व्यक्त किए जाते हैं।\n" +
                "अनुच्छेद अपने-आप में स्वतन्त्र और पूर्ण होते हैं। अनुच्छेद का मुख्य विचार या भाव की कुंजी या तो आरम्भ में रहती है या अन्त में। एक अच्छे अनुच्छेद-लेखन में मुख्य विचार अन्त में दिया जाता है।");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NoticeAdapter(this,arrnoticelist));


        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void addNoticeData(int noticeimg,String text) {

        NoticeSuitcase noticeSuitcase = new NoticeSuitcase();
        noticeSuitcase.img = noticeimg;
        noticeSuitcase.text = text;

        arrnoticelist.add(noticeSuitcase);
    }

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

                uploadnoticepost();

            } catch (IOException e) {
                e.printStackTrace();
            }

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

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

    private void initviews () {

        progressBar = findViewById(R.id.notice_progressbar);

        toolbar = findViewById(R.id.notice_toolbar);

        backimg = findViewById(R.id.notice_backimg);

        recyclerView = findViewById(R.id.notice_recyclerview);
    }


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
