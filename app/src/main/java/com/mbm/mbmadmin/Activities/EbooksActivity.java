package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.mbm.mbmadmin.Adapters.EbookAdapter;
import com.mbm.mbmadmin.FileUtils;
import com.mbm.mbmadmin.ModelResponse.EbookResponse;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.RetrofitClient;
import com.mbm.mbmadmin.Suitcases.EbooksSuitcase;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mbm.mbmadmin.FileUtils.getFilePathFromURI;
import static com.mbm.mbmadmin.ViewUtils.toast;

public class EbooksActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int BUFFER_SIZE = 1024;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    ImageView backimg;

    RecyclerView recyclerView;

    MaterialToolbar toolbar;

    ArrayList<EbooksSuitcase> arrbookslist = new ArrayList<>();

    Uri pdfuri;

    //ebookbottomsheet Views

    BottomSheetDialog bottomSheetDialog;

    LinearLayout llfile;

    ProgressBar progressBar;

    ImageView cancelimg,addimg,bookimg;

    EditText edtbname,edtaname,edtedition;

    TextView txtbname,txtaname,txtfilename;

    MaterialButton btnupload;

    String encodedimage,encodedpdf,bookimagename;

    FloatingActionButton fab;


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks);

        requestPermission();

        initviews();

        setSupportActionBar(toolbar);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        addEbookData(R.drawable.designerimg,"C++ programming language","7th edition","Vishal kumavat");
        addEbookData(R.drawable.designerimg,"C++  language","7th edition","Vishal kumavat");
        addEbookData(R.drawable.designerimg,"C++  ","7th edition","Vishal kumavat");
        addEbookData(R.drawable.designerimg,"C++  ","7th edition","Vishal kumavat");
        addEbookData(R.drawable.designerimg,"C++  language","7th edition","Vishal kumavat");
        addEbookData(R.drawable.designerimg,"C++ programming language","7th edition","Vishal kumavat");
        addEbookData(R.drawable.designerimg,"C++  language","7th edition","Vishal kumavat");
        addEbookData(R.drawable.designerimg,"C++ programming ","7th edition","Vishal kumavat");
        addEbookData(R.drawable.designerimg,"C++  ","7th edition","Vishal kumavat");
        addEbookData(R.drawable.designerimg,"C++ programming language","7th edition","Vishal kumavat");


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new EbookAdapter(this,arrbookslist));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Log.d("permission",permissions.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menuaddwhite,menu);
        return true;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuadditemwhite){

            bottomSheetDialog = new BottomSheetDialog(EbooksActivity.this,R.style.BottomSheetDialogTheme);
            View bottomsheetview = LayoutInflater.from(this).inflate(R.layout.addebook_bottomsheet,(RelativeLayout)findViewById(R.id.addebook_parentlayout));
            addebookviews(bottomsheetview);

            cancelimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialog.dismiss();
                }
            });

            edtbname.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    int bnamecount = edtbname.getText().length();
                    txtbname.setText(bnamecount + "/30");
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtaname.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    int anamecount = edtaname.getText().length();
                    txtaname.setText(anamecount +"/30");
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent,10);
                }
            });

            addimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                    intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    intent.setType("application/pdf");
                    startActivityForResult(intent,20);
                }
            });

            btnupload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtbname.getText().toString().equals("")){
                    edtbname.setError("please enter book name");
                    } else if (edtaname.getText().toString().equals("")) {
                        edtaname.setError("please enter author name");
                    }else if (edtedition.getText().toString().equals("")){
                        edtedition.setError("please enter book edition");
                    }else if (encodedimage ==null){
                        toast(EbooksActivity.this,"Please select Book image");
                    }else if (encodedpdf == null){
                        toast(EbooksActivity.this,"Please select Book pdf");
                    }else {
                        uploadEbook();
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
        RequestBody requestBody = RequestBody.create(MediaType.parse(Objects.requireNonNull(getContentResolver().getType(fileURi))),file);
        return MultipartBody.Part.createFormData(partname,file.getName(),requestBody);
    }
    /*private fun prepareFilePart(
        partName: String,
        fileUri: Uri
    ): MultipartBody.Part? {
        val file = File(RealPathUtils.getPath(context!!, fileUri))
        val requestBody: RequestBody =
            RequestBody.create(MediaType.parse(contentResolver.getType(fileUri)), file)
        return MultipartBody.Part.createFormData(partName, file.name, requestBody)
    }*/



    public void uploadEbook() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.VISIBLE);

        MultipartBody.Part fileToSend = prepareFilePart("book_path",pdfuri);

        Call<EbookResponse> ebookResponseCall = RetrofitClient.getInstance()
                .getapi()
                .ebookupload(edtbname.getText().toString(),edtaname.getText().toString(),edtedition.getText().toString(),fileToSend,"1");


        ebookResponseCall.enqueue(new Callback<EbookResponse>() {
            @SuppressLint("LogConditional")
            @Override
            public void onResponse(@NotNull Call<EbookResponse> call, @NotNull Response<EbookResponse> response) {
                Log.d("ApiResponse>>","ApiCall>> "+call.request());
                Log.d("ApiResponse>>","ApiCall>> "+new Gson().toJson(call.request()));
                Log.d("ApiResponse>>","ApiResponse>> "+new Gson().toJson(call.request()));
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                if(response.isSuccessful()){
                    toast(EbooksActivity.this,"Ebook upload Successfully");
                }else{
                    toast(EbooksActivity.this,response.message());
                }
                bottomSheetDialog.dismiss();
            }

            @SuppressLint("LogConditional")
            @Override
            public void onFailure(@NotNull Call<EbookResponse> call, @NotNull Throwable t) {
                Log.d("ApiResponse>>","ApiCallFailed>> "+call.request());
                Log.d("ApiResponse>>","ApiCallFailed>> "+t.toString());
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Log.d("ebookfail",t.getMessage()+"\n"+new Gson().toJson(call.request().body())+"\n"+call.request().body());
                toast(EbooksActivity.this,"Ebook pdf upload failed");
                bottomSheetDialog.dismiss();
            }
        });
    }

    public void SetUtils(){

        edtaname.setText("");
        edtbname.setText("");
        edtedition.setText("");
        bookimg.setImageBitmap(null);
        txtfilename.setText("add an attachment");
        addimg.setImageResource(R.drawable.addicon);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 20 && resultCode == RESULT_OK && data != null){
            addimg.setImageResource(R.drawable.pdficon);

            pdfuri = data.getData();


            if (pdfuri != null) {
                txtfilename.setText(FileUtils.getfilename(EbooksActivity.this,pdfuri));
                Log.d("pdfuri",pdfuri.toString());
            }

            try {
                if (pdfuri != null) {
                    InputStream is = getContentResolver().openInputStream(pdfuri);
                    byte[] bytesArray;
                    if (is != null) {
                        bytesArray = new byte[is.available()];
                        is.read(bytesArray);

                        Log.d("ebookpdfdata", "bytearray->" + Arrays.toString(bytesArray));

                        encodedpdf = Base64.encodeToString(bytesArray, Base64.DEFAULT);
                        Log.d("ebookpdfdata", "Base 64 string->" + encodedpdf);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if (requestCode == 10 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            Glide.with(this).load(uri).into(bookimg);

            if (uri != null) {
                bookimagename = FileUtils.getfilename(EbooksActivity.this,uri);
            }

            try {
                if (uri != null) {
                    InputStream is = getContentResolver().openInputStream(uri);
                    byte[] bytesArray;
                    if (is != null) {
                        bytesArray = new byte[is.available()];
                        is.read(bytesArray);

                        Log.d("ebookimagedata", "bytearray->" + Arrays.toString(bytesArray));

                        encodedimage = Base64.encodeToString(bytesArray, Base64.DEFAULT);
                        Log.d("ebookimagedata", "Base 64 string->" + encodedimage);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public static void copy(@NonNull Context context, @NonNull Uri srcUri, @NonNull File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copystream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copystream(@NonNull InputStream input, @NonNull OutputStream output) throws Exception {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int n;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
    }

    private void addebookviews(View bottomsheetview) {

        fab = bottomsheetview.findViewById(R.id.addebook_fabcam);
        bookimg = bottomsheetview.findViewById(R.id.addebook_bookimg);

        llfile = bottomsheetview.findViewById(R.id.addebook_llfile);

        cancelimg = bottomsheetview.findViewById(R.id.addebook_backimg);
        addimg = bottomsheetview.findViewById(R.id.addebook_fileimg);

        edtbname = bottomsheetview.findViewById(R.id.addebook_edtbname);
        edtaname = bottomsheetview.findViewById(R.id.addebook_edtaname);
        edtedition = bottomsheetview.findViewById(R.id.addebook_edtbedition);

        txtfilename = bottomsheetview.findViewById(R.id.addebook_txtfilename);
        txtbname = bottomsheetview.findViewById(R.id.addebook_txtbname);
        txtaname = bottomsheetview.findViewById(R.id.addebook_txtaname);

        btnupload = bottomsheetview.findViewById(R.id.addebook_btnupload);

        progressBar = bottomsheetview.findViewById(R.id.addebook_progressbar);

    }

    public void addEbookData(int bookimg, @NonNull String bookname, @NonNull String bookedition, @NonNull String bookauthor){

        EbooksSuitcase ebooksSuitcase = new EbooksSuitcase();

        ebooksSuitcase.bookimg = bookimg;
        ebooksSuitcase.bookname = bookname;
        ebooksSuitcase.bookauthorname = bookauthor;
        ebooksSuitcase.bookedition = bookedition;

        arrbookslist.add(ebooksSuitcase);
    }

    private void initviews() {

        backimg = findViewById(R.id.ebook_backimg);

        toolbar = findViewById(R.id.ebook_toolbar);

        recyclerView = findViewById(R.id.ebook_recyclerview);

    }
}