package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.mbm.mbmadmin.Adapters.EbookAdapter;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.EbooksSuitcase;

import java.util.ArrayList;

public class EbooksActivity extends AppCompatActivity {

    ImageView backimg;

    RecyclerView recyclerView;

    MaterialToolbar toolbar;

    ArrayList<EbooksSuitcase> arrbookslist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuaddwhite,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuadditemwhite){

            startActivity(new Intent(EbooksActivity.this,AddEbooksActivity.class));

            return true;
        }
        return false;
    }

    public void addEbookData(int bookimg, String bookname, String bookedition, String bookauthor){

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