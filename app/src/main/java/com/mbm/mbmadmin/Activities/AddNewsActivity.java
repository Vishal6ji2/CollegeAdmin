package com.mbm.mbmadmin.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mbm.mbmadmin.R;

public class AddNewsActivity extends AppCompatActivity {

    MaterialToolbar toolbar;

    ImageView backimg, newsimg;

    EditText edttitle, edtnews;

    MaterialButton btnpost;

    FloatingActionButton fabcam;

    TextView txttitle;

    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        initviews();

        setSupportActionBar(toolbar);

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fabcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        edttitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int cmpcount = edttitle.getText().length();
                txttitle.setText(cmpcount +"/30");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();

            newsimg.setImageURI(uri);


        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initviews() {

        toolbar = findViewById(R.id.addnews_toolbar);

        backimg = findViewById(R.id.addnews_backimg);

        btnpost = findViewById(R.id.addnews_btnpost);

        fabcam = findViewById(R.id.addnews_fabcam);

        edttitle = findViewById(R.id.addnews_edttitle);
        edtnews = findViewById(R.id.addnews_edtnews);

        txttitle = findViewById(R.id.addnews_txttitle);

    }

}