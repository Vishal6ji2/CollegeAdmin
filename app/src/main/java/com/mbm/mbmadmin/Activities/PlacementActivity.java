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
import android.widget.ImageView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mbm.mbmadmin.Adapters.PlacementAdapter;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.PlacementSuitcase;

import java.util.ArrayList;

public class PlacementActivity extends AppCompatActivity {


    MaterialToolbar toolbar;

    RecyclerView recyclerView;

    ArrayList<PlacementSuitcase> arrplacementlist = new ArrayList<>();

    ImageView backimg;


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

        addData(R.drawable.directorimg,"TCS","Off campus recruitment","12:11,12nov.2020","All the students of batch who are placed via off campus recruitment are requested to fill the given google form.");
        addData(R.drawable.directorimg,"TCS","Off campus recruitment","12:11,12nov.2020","All the students of batch who are placed via off campus recruitment are requested to fill the given google form.");
        addData(R.drawable.editorimg,"TCS","Off campus recruitment","12:11,12nov.2020","All the students of batch who are placed via off campus recruitment are requested to fill the given google form.");
        addData(R.drawable.designerimg,"TCS","Off campus recruitment","12:11,12nov.2020","All the students of batch who are placed via off campus recruitment are requested to fill the given google form.");
        addData(R.drawable.pictwo,"TCS","Off campus recruitment","12:11,12nov.2020","All the students of batch who are placed via off campus recruitment are requested to fill the given google form.");
        addData(R.drawable.directorimg,"TCS","Off campus recruitment","12:11,12nov.2020","All the students of batch who are placed via off campus recruitment are requested to fill the given google form.");
        addData(R.drawable.picone,"TCS","Off campus recruitment","12:11,12nov.2020","All the students of batch who are placed via off campus recruitment are requested to fill the given google form.");
        addData(R.drawable.editorimg,"TCS","Off campus recruitment","12:11,12nov.2020","All the students of batch who are placed via off campus recruitment are requested to fill the given google form.");
        addData(R.drawable.designerimg,"TCS","Off campus recruitment","12:11,12nov.2020","All the students of batch who are placed via off campus recruitment are requested to fill the given google form.");
        addData(R.drawable.directorimg,"TCS","Off campus recruitment","12:11,12nov.2020","All the students of batch who are placed via off campus recruitment are requested to fill the given google form.");


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PlacementAdapter(this,arrplacementlist));

    }

    private void addData(int cmpimg,String cmpname,String cmptitle,String cmptime,String cmpnews) {

        PlacementSuitcase placementSuitcase = new PlacementSuitcase();
        placementSuitcase.companyname = cmpname;
        placementSuitcase.cmptime = cmptime;
        placementSuitcase.companyimg = cmpimg;
        placementSuitcase.placementtitle = cmptitle;
        placementSuitcase.placementnews = cmpnews;


        arrplacementlist.add(placementSuitcase);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menuadd,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuadditem){
            startActivity(new Intent(PlacementActivity.this,AddPlacementnewsActivity.class));
            return true;
        }
        return false;
    }

    private void initviews() {

        toolbar = findViewById(R.id.placement_toolbar);

        recyclerView = findViewById(R.id.placement_recyclerview);

        backimg = findViewById(R.id.placement_backimg);

    }
}