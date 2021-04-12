package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import com.google.android.material.tabs.TabLayout;
import com.mbm.mbmadmin.Adapters.PaperAdapter;
import com.mbm.mbmadmin.Adapters.TabsViewpagerAdapter;
import com.mbm.mbmadmin.Fragments.PapersFragment;
import com.mbm.mbmadmin.Fragments.SyllabusFragment;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.PaperSuitcase;

import java.io.File;
import java.util.ArrayList;

public class PapersActivity extends AppCompatActivity {

    MaterialToolbar toolbar;

    ImageView backimg;


    ViewPager viewPager;

    TabLayout tabLayout;


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

        TabsViewpagerAdapter viewpagerAdapter = new TabsViewpagerAdapter(getSupportFragmentManager());

        viewpagerAdapter.addFragment(new PapersFragment(),"Past papers");
        viewpagerAdapter.addFragment(new SyllabusFragment(),"Syllabus");

        viewPager.setAdapter(viewpagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void initviews() {

        tabLayout = findViewById(R.id.paper_tablayout);
        viewPager = findViewById(R.id.paper_viewpager);

        toolbar = findViewById(R.id.paper_toolbar);

        backimg = findViewById(R.id.paper_backimg);

    }
}