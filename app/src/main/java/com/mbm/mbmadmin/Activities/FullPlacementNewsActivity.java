package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.material.appbar.MaterialToolbar;
import com.mbm.mbmadmin.R;

import static com.mbm.mbmadmin.ViewUtils.toast;

public class FullPlacementNewsActivity extends AppCompatActivity {

    MaterialToolbar toolbar;

    ImageView backimg;

    TextView txtcmpnews,txtcmptitle,txtfilename,txtline,txtcmpname,txtuploadedby;

    ImageView fileimg;

    RelativeLayout filelayout;

    String filepath;

    Uri fileuri;

    Intent intent;


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_placement_news);

        initviews();

        setSupportActionBar(toolbar);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtuploadedby.setPaintFlags(txtuploadedby.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtcmptitle.setPaintFlags(txtcmptitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        intent = getIntent();
        if (intent != null) {

            txtcmptitle.setText(intent.getStringExtra("cmptitle"));
            txtcmpname.setText(intent.getStringExtra("cmpname"));
            txtcmpnews.setText(intent.getStringExtra("cmpnews"));
            txtuploadedby.setText(String.format("By - %s", intent.getStringExtra("cmpuploadedby")));


            filepath = intent.getStringExtra("cmpfilepath");
            if (filepath != null && !filepath.equals("")) {
                fileuri = Uri.parse(filepath);
                if (fileuri != null && !fileuri.toString().equals("")) {

                        filelayout.setVisibility(View.VISIBLE);
                        txtline.setVisibility(View.VISIBLE);
                        if (filepath.endsWith(".xlsx") || filepath.endsWith(".xls")){
                            fileimg.setImageDrawable(ContextCompat.getDrawable(FullPlacementNewsActivity.this, R.drawable.xlsicon));
                        }else if (filepath.endsWith(".pdf")){
                            fileimg.setImageDrawable(ContextCompat.getDrawable(FullPlacementNewsActivity.this, R.drawable.pdficon));
                        }else if (filepath.endsWith(".doc") || filepath.endsWith(".docx")){
                            fileimg.setImageDrawable(ContextCompat.getDrawable(FullPlacementNewsActivity.this, R.drawable.docfileicon));
                        }else if (filepath.endsWith(".txt")){
                            fileimg.setImageDrawable(ContextCompat.getDrawable(FullPlacementNewsActivity.this, R.drawable.txticon));
                        }else {
                            fileimg.setImageDrawable(ContextCompat.getDrawable(FullPlacementNewsActivity.this, R.drawable.fileicon));
                        }

                        txtfilename.setText(intent.getStringExtra("cmpfilename"));

                        filelayout.setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("LogConditional")
                            @Override
                            public void onClick(View v) {

                              downloadfile(fileuri);
                            }
                        });
                }
            }else {
                filelayout.setVisibility(View.GONE);
                txtline.setVisibility(View.GONE);
            }
        }
    }

    void downloadfile(Uri fileuri) {

        DownloadManager.Request request = new DownloadManager.Request(fileuri);
        String title = URLUtil.guessFileName(filepath,null,null);
        request.setTitle(title);
        request.setDescription("Downloading file please wait....");
        String cookie = CookieManager.getInstance().getCookie(filepath);
        request.addRequestHeader("cookie",cookie);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS,title);

        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);

        toast(FullPlacementNewsActivity.this,"Downloading started...");

    }

    private void initviews() {

        filelayout = findViewById(R.id.fplacement_filelayout);

        toolbar = findViewById(R.id.fplacement_toolbar);

        backimg = findViewById(R.id.fplacement_backimg);
        fileimg = findViewById(R.id.fplacement_fileicon);

        txtcmpname = findViewById(R.id.fplacement_txtcmpname);
        txtline = findViewById(R.id.fplacement_lineone);
        txtfilename = findViewById(R.id.fplacement_filename);
        txtcmptitle = findViewById(R.id.fplacement_txtcmptitle);
        txtcmpnews = findViewById(R.id.fplacement_txtnews);
        txtuploadedby = findViewById(R.id.fplacement_txtuploadedby);

    }

}