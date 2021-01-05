package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.mbm.mbmadmin.Adapters.PaperAdapter;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.PaperSuitcase;

import java.io.File;
import java.util.ArrayList;

public class PapersActivity extends AppCompatActivity {

    MaterialToolbar toolbar;

    ImageView backimg;

    RecyclerView recyclerView;

    AppCompatSpinner spinbranch;

    ArrayList<PaperSuitcase> arrpaperlist = new ArrayList<>();

    ArrayList<String> arrsubjects = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_papers);

        initviews();

        setSupportActionBar(toolbar);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        addData("2014");
        addData("2015");
        addData("2016");
        addData("2017");
        addData("2018");
        addData("2019");
        addData("2020");
        addData("2013");
        addData("2012");

        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(new PaperAdapter(this,arrpaperlist));


        arrsubjects.add("C++");
        arrsubjects.add("Data structure & Algorithm");
        arrsubjects.add("Discrete Structures");
        arrsubjects.add("Python programming");
        arrsubjects.add("Computer Networks");

        ArrayAdapter<String> subAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,arrsubjects);

        spinbranch.setAdapter(subAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuadd,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuadditem){
            Toast.makeText(this, "paper clicked", Toast.LENGTH_SHORT).show();

            Intent intent =  new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent,10);


            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode ==10 && resultCode ==RESULT_OK) {

            Uri uri;
            if (data != null) {
                uri = data.getData();

                String uristring = uri.toString();
                File file = new File(uristring);
                String mypath = file.getAbsolutePath();
                String filename = null;

                if (uristring.startsWith("content://")) {
                    Cursor cursor = null;

                    try {
                        cursor = this.getContentResolver().query(uri, null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            filename = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            Toast.makeText(this, "Successfully ="+filename, Toast.LENGTH_SHORT).show();
                        }

                    } finally {
                        cursor.close();
                    }
                } else if (uristring.startsWith("file://")) {
                    filename = file.getName();
                    Toast.makeText(this, "Successfully ="+filename, Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    private void addData(String pdftime) {
        PaperSuitcase paperSuitcase = new PaperSuitcase();
        paperSuitcase.pdftime = pdftime;

        arrpaperlist.add(paperSuitcase);
    }

    private void initviews() {

        toolbar = findViewById(R.id.paper_toolbar);

        backimg = findViewById(R.id.paper_backimg);

        spinbranch = findViewById(R.id.paper_spinsubject);

        recyclerView = findViewById(R.id.paper_recyclerview);
    }
}