package com.mbm.mbmadmin.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mbm.mbmadmin.Adapters.SyllabusAdapter;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.SyllabusSuitcase;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class SyllabusFragment extends Fragment {

    RecyclerView recyclerView;

    EditText edtsearch;

    ArrayList<SyllabusSuitcase> arrsyllabuslist = new ArrayList<>();

    SyllabusAdapter syllabusAdapter;

    FloatingActionButton fabadd;


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_syllabus, container, false);

        initviews(view);


        addData("BE Computer Science & Engineering");
        addData("BE Mechanical Engineering");
        addData("BE Electrical Engineering");
        addData("BE Electronic Engineering");
        addData("BE Civil Engineering");
        addData("BE Computer Science & Engineering");
        addData("BE Mechanical Engineering");
        addData("BE Electrical Engineering");
        addData("BE Electronic Engineering");
        addData("BE Civil Engineering");

        syllabusAdapter = new SyllabusAdapter(getActivity(),arrsyllabuslist);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(syllabusAdapter);

        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent,10);

            }
        });

        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());

            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode ==10 && resultCode == RESULT_OK) {

            Uri uri;
            if (data != null) {
                uri = data.getData();

                String uristring;
                if (uri != null) {
                    uristring = uri.toString();

                    File file = new File(uristring);
//                    String mypath = file.getAbsolutePath();
                    String filename;

                    if (uristring.startsWith("content://")) {

                        try (Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(uri, null, null, null, null)) {
                            if (cursor != null && cursor.moveToFirst()) {
                                filename = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                Toast.makeText(getActivity(), "Successfully =" + filename, Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (uristring.startsWith("file://")) {
                        filename = file.getName();
                        Toast.makeText(getActivity(), "Successfully =" + filename, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void addData(@NonNull String pdfname){
        SyllabusSuitcase syllabusSuitcase = new SyllabusSuitcase();
        syllabusSuitcase.pdfname = pdfname;

        arrsyllabuslist.add(syllabusSuitcase);
    }

    void filter(String string) {

        ArrayList<SyllabusSuitcase> filterlist = new ArrayList<>();

        for (SyllabusSuitcase syllabusSuitcase : arrsyllabuslist){
            if (syllabusSuitcase.pdfname.toLowerCase().contains(string.toLowerCase())){
                filterlist.add(syllabusSuitcase);
            }
        }
        syllabusAdapter.filterlist(filterlist);
    }

    public void initviews(@NonNull View view){

        recyclerView = view.findViewById(R.id.syllabusfrag_recyclerview);
        edtsearch = view.findViewById(R.id.syllabusfrag_edtsearch);
        fabadd = view.findViewById(R.id.syllabusfrag_fabadd);
    }

}