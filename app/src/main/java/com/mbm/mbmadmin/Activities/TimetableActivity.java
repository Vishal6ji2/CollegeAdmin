package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.mbm.mbmadmin.Adapters.TimetableAdapter;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.TimetableSuitcase;

import java.util.ArrayList;

public class TimetableActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    MaterialToolbar toolbar;

    ImageView backimg;

    ArrayList<TimetableSuitcase> arrtimelist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        initviews();

        setSupportActionBar(toolbar);

        addData("",R.drawable.picone);
        addData("",R.drawable.picone);
        addData("",R.drawable.picone);
        addData("",R.drawable.picone);
        addData("",R.drawable.picone);
        addData("",R.drawable.picone);
        addData("",R.drawable.picone);
        addData("",R.drawable.picone);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TimetableAdapter(this,arrtimelist));
    }

    private void addData(String strsem,int semimg) {

        TimetableSuitcase timetableSuitcase = new TimetableSuitcase();
        timetableSuitcase.semimg =semimg;
        timetableSuitcase.txtsem =strsem;

        arrtimelist.add(timetableSuitcase);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menuadd,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuadditem){

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,10);

            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 10 && resultCode == RESULT_OK && data != null){

            Uri uri = data.getData();
            Toast.makeText(this, "Time table upload successfully", Toast.LENGTH_SHORT).show();

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initviews() {

        recyclerView = findViewById(R.id.tt_recyclerview);

        toolbar = findViewById(R.id.tt_toolbar);

        backimg = findViewById(R.id.tt_backimg);
    }
}