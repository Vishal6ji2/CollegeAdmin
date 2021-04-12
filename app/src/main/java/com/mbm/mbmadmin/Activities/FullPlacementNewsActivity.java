package com.mbm.mbmadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.mbm.mbmadmin.R;

public class FullPlacementNewsActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    ImageView backimg;
    TextView txtnews,txtcmptitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_placement_news);

        initviews();

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initviews() {

        toolbar = findViewById(R.id.fplacement_toolbar);

        backimg = findViewById(R.id.fplacement_backimg);

        txtcmptitle = findViewById(R.id.fplacement_txtcmptitle);
        txtnews = findViewById(R.id.fplacement_txtnews);

    }

}