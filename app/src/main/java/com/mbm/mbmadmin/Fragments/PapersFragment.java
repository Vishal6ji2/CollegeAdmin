package com.mbm.mbmadmin.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mbm.mbmadmin.Adapters.PaperAdapter;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.Suitcases.PaperSuitcase;

import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.mbm.mbmadmin.FileUtils.getfilename;
import static com.mbm.mbmadmin.ViewUtils.toast;

public class PapersFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<PaperSuitcase> arrpaperlist = new ArrayList<>();
    FloatingActionButton fabadd;
    AppCompatSpinner spinsubject;
    ArrayList<String> arrsubjects = new ArrayList<>();


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container,@NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_papers, container, false);
        initviews(view);

        addData("2014");
        addData("2015");
        addData("2016");
        addData("2017");
        addData("2018");
        addData("2019");
        addData("2020");
        addData("2013");
        addData("2012");

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(new PaperAdapter(getActivity(),arrpaperlist));


        arrsubjects.add("C++");
        arrsubjects.add("Data structure & Algorithm");
        arrsubjects.add("Discrete Structures");
        arrsubjects.add("Python programming");
        arrsubjects.add("Computer Networks");

        ArrayAdapter<String> subAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()),android.R.layout.simple_spinner_dropdown_item,arrsubjects);

        spinsubject.setAdapter(subAdapter);

        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent,10);

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode ==10 && resultCode == RESULT_OK && data!=null && data.getData()!=null) {

            Uri uri = data.getData();

            String filename = getfilename(Objects.requireNonNull(getActivity()),uri);
            toast(getActivity(),filename);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initviews(View view){
        recyclerView = view.findViewById(R.id.paperfrag_recyclerview);
        fabadd = view.findViewById(R.id.paperfrag_fabadd);
        spinsubject = view.findViewById(R.id.paperfrag_spinsubject);
    }

    private void addData(String pdftime) {
        PaperSuitcase paperSuitcase = new PaperSuitcase();
        paperSuitcase.pdftime = pdftime;

        arrpaperlist.add(paperSuitcase);
    }


}