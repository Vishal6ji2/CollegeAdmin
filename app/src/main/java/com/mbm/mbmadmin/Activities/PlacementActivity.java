package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mbm.mbmadmin.Adapters.PlacementAdapter;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.PlacementNewsFetchResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PlacementActivity extends AppCompatActivity {


    MaterialToolbar toolbar;

    RecyclerView recyclerView;

    ShimmerFrameLayout shimmerFrameLayout;

    ArrayList<PlacementNewsFetchResponse.Placementnews> arrPlacementNewslist = new ArrayList<>();

    ImageView backimg;

    BottomSheetDialog bottomSheetDialog;

    //bottomsheet views

    LinearLayout llfile;

    String filename,encodedfile;

    ImageView cancelimg,okimg,fileimg;

    EditText edtcmpname,edttitle,edtnews,edtbyname;

    MaterialButton btnpost;

    FloatingActionButton fabcam;

    TextView txtname,txttitle,txtfilename;

    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement);

        initviews();

        setSupportActionBar(toolbar);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        addPlacementNewsData();

    }
/*

    public void addPlacementNewsData(){

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();

        recyclerView.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(PlacementActivity.this,DividerItemDecoration.VERTICAL));

//        Call<PlacementNewsFetchResponse> responseCall = RetrofitClient.getInstance().getapi().fetchPlacementNewsData();

        responseCall.enqueue(new Callback<PlacementNewsFetchResponse>() {
            @Override
            public void onResponse(Call<PlacementNewsFetchResponse> call, Response<PlacementNewsFetchResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        arrPlacementNewslist = response.body().getPlacementnews();

                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        recyclerView.setAdapter(new PlacementAdapter(PlacementActivity.this, arrPlacementNewslist));

                    } else {
                        toast(PlacementActivity.this,response.message());
                    }
                }else {
                    toast(PlacementActivity.this,response.message());
                }
            }

            @SuppressLint("LogConditional")
            @Override
            public void onFailure(Call<PlacementNewsFetchResponse> call, Throwable t) {
                toast(PlacementActivity.this, Objects.requireNonNull(t.getLocalizedMessage()));
                Log.d("placementnewsfetchfail",t.getMessage());

            }
        });
    }

*/
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        getMenuInflater().inflate(R.menu.menuaddwhite,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuadditemwhite){

            bottomSheetDialog = new BottomSheetDialog(PlacementActivity.this,R.style.BottomSheetDialogTheme);
            View bottomsheetview = LayoutInflater.from(this).inflate(R.layout.addplacement_bottomsheet,(RelativeLayout)findViewById(R.id.addplacement_parentlayout));
            addplacementviews(bottomsheetview);

            cancelimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialog.dismiss();
                }
            });


            edtcmpname.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    int cmpcount = edtcmpname.getText().length();
                    txtname.setText(cmpcount+"/15");
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edttitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    int titlecount = edttitle.getText().length();
                    txttitle.setText(titlecount+"/30");

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            llfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    startActivityForResult(intent,500);
                }
            });

            btnpost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtcmpname.getText().toString().isEmpty() && edttitle.getText().toString().isEmpty() && edtnews.getText().toString().isEmpty() && edtbyname.getText().toString().isEmpty()){

                        Toast.makeText(PlacementActivity.this, "Fill all Placement details", Toast.LENGTH_SHORT).show();

                    }else {
//                        uploadplacementnews();
                    }
                }
            });
            bottomSheetDialog.setContentView(bottomsheetview);
            bottomSheetDialog.show();
        return true;
        }
        return false;
    }
/*

     public void uploadplacementnews() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.VISIBLE);

        Call<PlacementnewsResponse> placementnewsResponseCall = RetrofitClient.getInstance()
                .getapi()
                .placementnewsupload(edtcmpname.getText().toString(),encodedfile,filename,edttitle.getText().toString(),edtnews.getText().toString(),"1","Vishal Kumavat");

        placementnewsResponseCall.enqueue(new Callback<PlacementnewsResponse>() {
            @Override
            public void onResponse(@NotNull Call<PlacementnewsResponse> call, @NotNull Response<PlacementnewsResponse> response) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                PlacementnewsResponse placementnewsResponse = response.body();
                if (placementnewsResponse != null && response.isSuccessful()) {
                    Toast.makeText(PlacementActivity.this, placementnewsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    SetUtils();

                    if (placementnewsResponse.getMessage().equals("Placement news Uploaded Successfully")){
                        Log.d("placementdata",placementnewsResponse.getMessage());
                    }else {
                        Log.d("placementdata","\n"+placementnewsResponse.getError());
                    }
                    bottomSheetDialog.dismiss();
                }else{
                    Toast.makeText(PlacementActivity.this,placementnewsResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    bottomSheetDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NotNull Call<PlacementnewsResponse> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Log.d("newsdatafail",t.getMessage()+"\n"+new Gson().toJson(call.request().body())+"\n"+call.request().body());
                Toast.makeText(PlacementActivity.this, "Placement news upload failed", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });

    }

*/
    public void SetUtils(){

        edtnews.setText("");
        edttitle.setText("");
        edtbyname.setText("");
        edtcmpname.setText("");
    }


    public void addplacementviews(View bottomsheetview){

        llfile = bottomsheetview.findViewById(R.id.addplacement_llfile);

        txtfilename = bottomsheetview.findViewById(R.id.addplacement_txtfilename);

        fileimg = bottomsheetview.findViewById(R.id.addplacement_fileimg);

        progressBar = bottomsheetview.findViewById(R.id.addplacement_progressbar);

        btnpost = bottomsheetview.findViewById(R.id.addplacement_btnpost);

        cancelimg = bottomsheetview.findViewById(R.id.addplacement_backimg);

        btnpost = bottomsheetview.findViewById(R.id.addplacement_btnpost);


        edtcmpname = bottomsheetview.findViewById(R.id.addplacement_edtcmpname);
        edtbyname = bottomsheetview.findViewById(R.id.addplacement_edtbyname);
        edttitle = bottomsheetview.findViewById(R.id.addplacement_edttitle);
        edtnews = bottomsheetview.findViewById(R.id.addplacement_edtnews);

        txtname = bottomsheetview.findViewById(R.id.addplacement_txtcmpname);
        txttitle = bottomsheetview.findViewById(R.id.addplacement_txttitle);
    }

    private void initviews() {

        shimmerFrameLayout = findViewById(R.id.placement_shimmerlayout);

        toolbar = findViewById(R.id.placement_toolbar);

        recyclerView = findViewById(R.id.placement_recyclerview);

        backimg = findViewById(R.id.placement_backimg);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 500 && resultCode == RESULT_OK && data!=null) {

            Uri uri = data.getData();
            filename = getfilename(data);
//            txtfilename.setText(filename);
            if (filename.endsWith(".xlsx")) {
                fileimg.setBackground(getResources().getDrawable(R.drawable.xlsicon));
                txtfilename.setText(filename);
            } else if (filename.endsWith(".txt")) {
                fileimg.setBackground(getResources().getDrawable(R.drawable.txticon));
                txtfilename.setText(filename);
            } else if (filename.endsWith(".pdf")) {
                fileimg.setBackground(getResources().getDrawable(R.drawable.pdficon));
                txtfilename.setText(filename);
            } else if (filename.endsWith(".xls")) {
                fileimg.setBackground(getResources().getDrawable(R.drawable.xlsicon));
                txtfilename.setText(filename);
            } else if (filename.endsWith(".doc")) {
                fileimg.setBackground(getResources().getDrawable(R.drawable.docfileicon));
                txtfilename.setText(filename);
            } else if (filename.endsWith(".docx")) {
                fileimg.setBackground(getResources().getDrawable(R.drawable.docfileicon));
                txtfilename.setText(filename);
            } else {
                Toast.makeText(this, "This type of file is not allowed to upload", Toast.LENGTH_SHORT).show();
            }

            try {
                if (uri != null) {
                    InputStream is = getContentResolver().openInputStream(uri);
                    byte[] bytesArray;
                    if (is != null) {
                        bytesArray = new byte[is.available()];
                        is.read(bytesArray);

                        Log.d("pdfdata", "bytearray->" + bytesArray.toString());

                        encodedfile = Base64.encodeToString(bytesArray, Base64.DEFAULT);
                        Log.d("pdfdata", "Base 64 string->" + encodedfile);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getfilename(Intent data){
        String filename = "";
        if (data != null) {
            Uri uri = data.getData();

            String uristring = uri.toString();
            File file = new File(uristring);
//            String mypath = file.getAbsolutePath();


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
}