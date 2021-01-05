package com.mbm.mbmadmin.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
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

public class AddPlacementnewsActivity extends AppCompatActivity {

    MaterialToolbar toolbar;

    ImageView backimg,cmpimg;

    EditText edtcmpname,edttitle,edtnews,edtbyname,edtlink;

    MaterialButton btnpost;

    FloatingActionButton fabcam;

    TextView txtname,txttitle;

    Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_placementnews);

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
                startActivityForResult(intent,10);
            }
        });

        edtcmpname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int cmpcount = edtcmpname.getText().length();
                txtname.setText(cmpcount);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edttitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int titlecount = edttitle.getText().length();
                txttitle.setText(titlecount);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 10 && resultCode == RESULT_OK && data != null){

            Uri uri = data.getData();
            cmpimg.setImageURI(uri);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initviews() {

        toolbar = findViewById(R.id.addplacement_toolbar);

        backimg = findViewById(R.id.addplacement_backimg);
        cmpimg = findViewById(R.id.addplacement_cmpimg);

        btnpost = findViewById(R.id.addplacement_btnpost);

        fabcam = findViewById(R.id.addplacement_fabcam);

        edtcmpname = findViewById(R.id.addplacement_edtcmpname);
        edtbyname = findViewById(R.id.addplacement_edtbyname);
        edtlink = findViewById(R.id.addplacement_edtlink);
        edttitle = findViewById(R.id.addplacement_edttitle);
        edtnews = findViewById(R.id.addplacement_edtnews);

        txtname = findViewById(R.id.addplacement_txtcmpname);
        txttitle = findViewById(R.id.addplacement_txttitle);

    }
}