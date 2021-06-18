package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.mbm.mbmadmin.Adapters.LinksAdapter;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.LinksSuitcase;
import java.util.ArrayList;
import static com.mbm.mbmadmin.ViewUtils.toast;


public class LinksActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    MaterialToolbar toolbar;

    ImageView backimg;

    ArrayList<LinksSuitcase> arrlinkslist = new ArrayList<>();

    //add link bottomsheetviews

    BottomSheetDialog bottomSheetDialog;

    ProgressBar progressBar;

    EditText edtweblink, edtlinkname;

    MaterialButton btnupload;

    ImageView cancelimg;


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_links);

        initviews();

        setSupportActionBar(toolbar);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        addDataLinks("https://www.youtube.com/", "Youtube link");
        addDataLinks("https://www.youtube.com/", "Continuity form(2020-2021)");
        addDataLinks("https://www.youtube.com/watch?v=q3_-XYOYQQA", "The Falcon and The Winter soldier");
        addDataLinks("https://mail.google.com/mail/u/0/#inbox", "Gmail form");


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new LinksAdapter(this, arrlinkslist));

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menuaddwhite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuadditemwhite) {
            bottomSheetDialog = new BottomSheetDialog(LinksActivity.this, R.style.BottomSheetDialogTheme);
            View bottomsheetview = LayoutInflater.from(this).inflate(R.layout.addlinks_bottomsheet, (RelativeLayout) findViewById(R.id.addlink_parentlayout));
            addebookviews(bottomsheetview);

            cancelimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialog.dismiss();
                }
            });

            btnupload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtlinkname.getText().toString().equals("")){
                        toast(LinksActivity.this,"Please enter link name");
                    }else if(edtweblink.getText().toString().equals("")){
                        toast(LinksActivity.this,"Please enter your web link");
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                        uploadLink();
                    }
                }
            });

            bottomSheetDialog.setContentView(bottomsheetview);
            bottomSheetDialog.show();

            return true;
        }
        return false;
    }

    void uploadLink() {

        toast(LinksActivity.this,"Link upload successfully");
        progressBar.setVisibility(View.GONE);
        bottomSheetDialog.dismiss();
    }


    private void addebookviews(View bottomsheetview) {

        cancelimg = bottomsheetview.findViewById(R.id.addlink_backimg);

        edtweblink = bottomsheetview.findViewById(R.id.addlink_edtbweblink);
        edtlinkname = bottomsheetview.findViewById(R.id.addlink_edtlname);

        btnupload = bottomsheetview.findViewById(R.id.addelink_btnupload);

        progressBar = bottomsheetview.findViewById(R.id.addlink_progressbar);

    }


    private void addDataLinks(String weblink, String linkname) {

        LinksSuitcase linksSuitcase = new LinksSuitcase();
        linksSuitcase.linkname = linkname;
        linksSuitcase.weblink = weblink;

        arrlinkslist.add(linksSuitcase);
    }

    private void initviews() {

        recyclerView = findViewById(R.id.links_recyclerview);

        toolbar = findViewById(R.id.links_toolbar);

        backimg = findViewById(R.id.links_backimg);
    }

}