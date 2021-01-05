package com.mbm.mbmadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.mbm.mbmadmin.R;

public class AddEbooksActivity extends AppCompatActivity {

    MaterialToolbar toolbar;

    ImageView backimg;

    EditText edtbname,edtaname,edtedition;

    TextView txtbname,txtaname;

    MaterialButton btnupload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ebooks);

        initviews();

        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void initviews() {

        toolbar = findViewById(R.id.addebook_toolbar);

        backimg = findViewById(R.id.addebook_backimg);

        edtbname = findViewById(R.id.addebook_edtbname);
        edtaname = findViewById(R.id.addebook_edtaname);
        edtedition = findViewById(R.id.addebook_edtbedition);

        txtbname = findViewById(R.id.addebook_txtbname);
        txtaname = findViewById(R.id.addebook_txtaname);

        btnupload = findViewById(R.id.addebook_btnupload);

    }
}