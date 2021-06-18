package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mbm.mbmadmin.Adapters.NewsPostAdapter;
import com.mbm.mbmadmin.FileUtils;
import com.mbm.mbmadmin.ModelResponse.NewsFeedResponse;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.RetrofitClient;
import com.mbm.mbmadmin.Suitcases.NewsFetchResponse;
import com.mbm.mbmadmin.Suitcases.NewsPostSuitcase;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mbm.mbmadmin.Networks.CheckInternet.isConnected;
import static com.mbm.mbmadmin.ViewUtils.toast;

public class NewsfeedActivity extends AppCompatActivity {

    MaterialToolbar toolbar;

    ImageView backimg;

    RecyclerView recyclerView;

    ShimmerFrameLayout shimmerFrameLayout;

    ArrayList<NewsFetchResponse.Newsfeed> arrNewslist = new ArrayList<>();

    ArrayList<NewsFetchResponse.Newsfeed> arrayPrevList = new ArrayList<>();

    BottomSheetDialog bottomSheetDialog;

    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;

    Uri uri;

    TextView txtUpdate;

    CardView cardUpdate;

    int currcount = 0;
    int prevcount,flag = 0;

    //addnews bottomsheetviews

    MaterialToolbar addtoolbar;

    ImageView cancelimg,newsimg,okimg;

    MaterialButton btnpost;

    FloatingActionButton fabcam;

    TextView txttitle,txtimg;

    ProgressBar progressBar;

    EditText edttitle, edtnews;

    String imagename;


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        initviews();

        setSupportActionBar(toolbar);

        cardUpdate.setVisibility(View.GONE);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        arrNewslist.clear();

        if (isConnected(this)){
//            addPostData();
        }else {
            
            showNetworkDialog();
        }

        cardUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recreate();
                cardUpdate.setVisibility(View.GONE);
                flag = 0;
            }
        });

    }

    private void showNetworkDialog() {
    }
/*

    public void addPostData(){

        recyclerView.setVisibility(View.GONE);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewsfeedActivity.this);
        linearLayoutManager.setStackFromEnd(false);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        Call<NewsFetchResponse> responseCall = RetrofitClient.getInstance().getapi().fetchNewsData();

        responseCall.enqueue(new Callback<NewsFetchResponse>() {
            @Override
            public void onResponse(Call<NewsFetchResponse> call, Response<NewsFetchResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        arrayPrevList.clear();

                        arrNewslist = response.body().getNewsfeeds();

                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        currcount = arrNewslist.size();

                        sharedPreferences = getSharedPreferences("newsfeed",MODE_PRIVATE);

                        prevcount = sharedPreferences.getInt("newscount",0);

//                        if (prevcount==0){
                            recyclerView.setAdapter(new NewsPostAdapter(NewsfeedActivity.this,arrNewslist));
//                        }else if(prevcount>currcount || prevcount<currcount){
//                            for (int i=0; i<prevcount;i++){
//                                arrayPrevList.add(arrNewslist.get(i));
//                            }
//                            recyclerView.setAdapter(new NewsPostAdapter(NewsfeedActivity.this, arrayPrevList));
//                        }



                        checkNewPosts(currcount);

                    } else {
                        Log.d("newsfetchfail",response.message());
                        toast(NewsfeedActivity.this,response.message());
                    }
                }else {
                    toast(NewsfeedActivity.this,response.message());
                    Log.d("newsfetchfail",response.message());
                }
            }

            @SuppressLint("LogConditional")
            @Override
            public void onFailure(Call<NewsFetchResponse> call, Throwable t) {

                toast(NewsfeedActivity.this, Objects.requireNonNull(t.getLocalizedMessage()));
                Log.d("newsfetchfail",t.getMessage());

            }
        });

    }

*/
    void checkNewPosts(int currcount) {

        if (currcount>prevcount){
            cardUpdate.setVisibility(View.VISIBLE);
            flag = 1;
        }else {
            flag = 0;
        }

        editor = sharedPreferences.edit();
        editor.putInt("newscount",currcount);
        editor.apply();
    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

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
//                        uploadpost();
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


        File file = new File(FileUtils.getFilePathFromURI(this, uri));



        Log.d("okhttpclient",uri.toString());

        MultipartBody.Part imagepart = MultipartBody.Part.createFormData("news_imagepath",file.getName(),RequestBody.create(MediaType.parse("*/*"),file));

        Log.d("okhttpclient", file.getName());


       /* Call<NewsFeedResponse> newsFeedResponseCall = RetrofitClient.getInstance()
                .getapi()
                .newsupload(imagepart,edttitle.getText().toString(),edtnews.getText().toString(),"vishal kumavat","cse");
*/
      /*  newsFeedResponseCall.enqueue(new Callback<NewsFeedResponse>() {
            @Override
            public void onResponse(Call<NewsFeedResponse> call, Response<NewsFeedResponse> response) {

                progressBar.setVisibility(View.GONE);
                bottomSheetDialog.dismiss();

                NewsFeedResponse newsFeedResponse = response.body();

                if (newsFeedResponse != null) {
                    if (response.isSuccessful()) {
                        toast(NewsfeedActivity.this, newsFeedResponse.getMessage());
                    } else {
                        toast(NewsfeedActivity.this, newsFeedResponse.getMessage());
                    }
                }
                Log.d("OnResponse",response.message());
            }

            @Override
            public void onFailure(Call<NewsFeedResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                bottomSheetDialog.dismiss();
                Log.d("OnFailure",String.valueOf(t.getMessage()));
                toast(NewsfeedActivity.this,String.valueOf(t.getLocalizedMessage()));

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

    @NonNull
    public String getimagename(@NonNull Uri uri, Intent data){
        String filename = null;
        if (data != null) {
            uri = data.getData();

            String uristring = uri.toString();
            File file = new File(uristring);
            String mypath = file.getAbsolutePath();


            if (uristring.startsWith("content://")) {

                try (Cursor cursor = this.getContentResolver().query(uri, null, null, null, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        filename = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("filename", filename);
                    }

                }
            } else if (uristring.startsWith("file://")) {
                filename = file.getName();
                Log.d("filename",filename);
            }
        }
        return filename;
    }

    private void initviews() {

        txtUpdate = findViewById(R.id.newsfeed_txtupdate);

        cardUpdate = findViewById(R.id.newsfeed_updatecard);

        toolbar = findViewById(R.id.newsfeed_toolbar);

        backimg = findViewById(R.id.newsfeed_backimg);

        shimmerFrameLayout = findViewById(R.id.newsfeed_shimmerlayout);

        recyclerView = findViewById(R.id.newsfeed_recyclerview);
    }
}