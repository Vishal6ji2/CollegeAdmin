package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.mbm.mbmadmin.Adapters.PaperAdapter;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.PaperSuitcase;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.sql.Time;

public class HomeActivity extends AppCompatActivity {

    MaterialToolbar toolbar;

    CircularImageView profileimg;

    TextView txtname;

    RelativeLayout newslayout,noticelayout,placementlayout,ttlayout,paperlayout,syllabuslayout,ebooklayout,studentslayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initviews();

        setSupportActionBar(toolbar);


        studentslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,StudentsListActivity.class));
            }
        });

        newslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,NewsfeedActivity.class));
            }
        });

        ebooklayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,EbooksActivity.class));
            }
        });


        noticelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,NoticeActivity.class));
            }
        });

        placementlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,PlacementActivity.class));
            }
        });

        paperlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, PapersActivity.class));
            }
        });

      /*  syllabuslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,SyllabusActivity.class));
            }
        });
*/
        ttlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, TimetableActivity.class));
            }
        });

        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ProfileActivity.class);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this,profileimg, ViewCompat.getTransitionName(profileimg));

                startActivity(intent,optionsCompat.toBundle());
            }
        });

        txtname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu){
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            finish();
        }
        return true;
    }

    private void initviews() {

        toolbar = findViewById(R.id.home_toolbar);

        profileimg = findViewById(R.id.home_profileimg);

        txtname = findViewById(R.id.home_txtname);

        ebooklayout = findViewById(R.id.home_ebooklayout);
        newslayout = findViewById(R.id.home_newsfeedlayout);
        noticelayout = findViewById(R.id.home_noticelayout);
        placementlayout = findViewById(R.id.home_placementlayout);
        ttlayout = findViewById(R.id.home_ttlayout);
        paperlayout = findViewById(R.id.home_paperslayout);
        studentslayout = findViewById(R.id.home_studentslayout);

    }
}