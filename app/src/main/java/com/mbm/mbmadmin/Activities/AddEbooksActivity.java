package com.mbm.mbmadmin.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
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
import com.mbm.mbmadmin.R;

public class AddEbooksActivity extends AppCompatActivity {

    MaterialToolbar toolbar;

    ImageView backimg,addimg;

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

        edtbname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int bnamecount = edtbname.getText().length();
                txtbname.setText(bnamecount +"/30");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtaname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int anamecount = edtaname.getText().length();
                txtaname.setText(anamecount +"/30");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent,20);
            }
        });

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addimg.getDrawable().equals(ResourcesCompat.getDrawable(getResources(),R.drawable.addicon,getTheme()))){
                    Toast.makeText(AddEbooksActivity.this, "Please select ebook", Toast.LENGTH_SHORT).show();
                }else if (edtaname.getText().toString().equals("")){
                    edtaname.setError("please enter author name");
                }else if (edtbname.getText().toString().equals("")){
                    edtbname.setError("please enter book name");
                }else if (edtedition.getText().toString().equals("")){
                    edtedition.setError("please enter book edition");
                }else {
                    Toast.makeText(AddEbooksActivity.this, "Ebook upload successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddEbooksActivity.this,EbooksActivity.class));
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 20 && resultCode == RESULT_OK && data != null){
            addimg.setImageResource(R.drawable.pdficon);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initviews() {

        toolbar = findViewById(R.id.addebook_toolbar);

        backimg = findViewById(R.id.addebook_backimg);
        addimg = findViewById(R.id.addebook_addimg);

        edtbname = findViewById(R.id.addebook_edtbname);
        edtaname = findViewById(R.id.addebook_edtaname);
        edtedition = findViewById(R.id.addebook_edtbedition);

        txtbname = findViewById(R.id.addebook_txtbname);
        txtaname = findViewById(R.id.addebook_txtaname);

        btnupload = findViewById(R.id.addebook_btnupload);

    }
}