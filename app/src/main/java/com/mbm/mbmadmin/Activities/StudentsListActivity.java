package com.mbm.mbmadmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mbm.mbmadmin.Adapters.StudentsAdapter;

import com.mbm.mbmadmin.ModelResponse.StudentAddResponse;
import com.mbm.mbmadmin.R;

import com.mbm.mbmadmin.RetrofitClient;
import com.mbm.mbmadmin.Sessions.LoginSession;
import com.mbm.mbmadmin.Sessions.StudentsListSession;
import com.mbm.mbmadmin.Suitcases.GetAdminStudentsResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mbm.mbmadmin.Sessions.LoginSession.KEY_DEPT_ID;
import static com.mbm.mbmadmin.ViewUtils.toast;

public class StudentsListActivity extends AppCompatActivity {

    private static final String TAG = "StudentsListActivity";

    TextView txtTitle;

    SwipeRefreshLayout refreshLayout;

    EditText edtsearch;

    RecyclerView recyclerView;

    MaterialToolbar toolbar;

    ProgressBar progressBar;

    boolean is_blocked = false;

    ShimmerFrameLayout shimmerFrameLayout;

    Uri uri;

    BottomSheetDialog bottomSheetDialog;

    @NonNull
    public static ArrayList<GetAdminStudentsResponse.Student> arrstudentlist = new ArrayList<>();

    public static boolean isinActionmode = false;

    public static final ArrayList<GetAdminStudentsResponse.Student> selectedStudentsArrayList = new ArrayList<>();

    StudentsAdapter studentsAdapter;

    int EXCEL_FILE_REQUEST = 100;

    StudentsListSession studentsListSession;

    LoginSession loginSession;

    // student bottomsheet layout views

    String encodedfile,filename;

    // add student bottomsheet layout views

    TextInputEditText edtName,edtRegNo,edtEmail,edtMob;

    ImageView canceladdImg;

    MaterialButton btnAddSave;

    ProgressBar addProgressBar;


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        initviews();

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loginSession = new LoginSession(this);

//        getStudentsData();

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                toast(StudentsListActivity.this,"Refresh");
                getStudentsData();
                refreshLayout.setRefreshing(false);
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

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        getMenuInflater().inflate(R.menu.studentsmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.student_menu_excel){
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                startActivityForResult(intent, EXCEL_FILE_REQUEST);

        }else if (item.getItemId() == R.id.student_menu_addstudent){

            addSingleStudent();

        }else if (item.getItemId() == R.id.students_menu_block){

            toast(StudentsListActivity.this,"Students blocked");
            //Block Students statement called
            clearActionMode();

        }else if (item.getItemId() == R.id.students_menu_unblock){

            toast(StudentsListActivity.this,"Students unblocked");
            //Block Students statement called
            clearActionMode();

        }else if (item.getItemId() == R.id.students_menu_delete){
            isinActionmode = false;
            studentsAdapter.removedata(selectedStudentsArrayList);
            clearActionMode();
            studentsAdapter.notifyDataSetChanged();

        }else if (item.getItemId() == R.id.students_menu_selectall){
            selectedStudentsArrayList.clear();
            isinActionmode = true;

            selectedStudentsArrayList.addAll(arrstudentlist);

            updateViewcounter();
        }

        return true;
    }

    private void addSingleStudent() {

        bottomSheetDialog = new BottomSheetDialog(StudentsListActivity.this,R.style.BottomSheetDialogTheme);
        final View bottomsheetview = LayoutInflater.from(StudentsListActivity.this).inflate(R.layout.addstudent_bottomsheet,(LinearLayout)findViewById(R.id.addstudent_bottomsheetlayout));
        addStudentViews(bottomsheetview);

        canceladdImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        btnAddSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtRegNo.getText().toString().equals("") && edtName.getText().toString().equals("") && edtEmail.getText().toString().equals("") && edtMob.getText().toString().equals("")){

                    toast(StudentsListActivity.this,"Enter Student full details");

                }else {

                    addStudentsData();
                }

            }
        });

        bottomSheetDialog.setContentView(bottomsheetview);
        bottomSheetDialog.show();

    }

    public void prepareToolbar(int menu){

        toolbar.getMenu().clear();
        toolbar.inflateMenu(menu);
        isinActionmode = true;
        studentsAdapter.notifyDataSetChanged();

    }

    public void prepareSelection(int position) {

        if (!selectedStudentsArrayList.contains(arrstudentlist.get(position))){
            selectedStudentsArrayList.add(arrstudentlist.get(position));
            studentsAdapter.notifyDataSetChanged();
        }else {
            selectedStudentsArrayList.remove(arrstudentlist.get(position));
            studentsAdapter.notifyDataSetChanged();
        }

        updateViewcounter();

        if(selectedStudentsArrayList.size()>0) {
            if (selectedStudentsArrayList.get(0).getStatus().equals("0")) {
                is_blocked = true;
                prepareToolbar(R.menu.studentsunblockmenus);
                // Unblock Students api called status = 1
            } else {
                is_blocked = false;
                prepareToolbar(R.menu.studentsblockmenus);
                // Block Students api called status = 0
            }

        }else {
            clearActionMode();
        }
    }

    private void updateViewcounter() {

        studentsAdapter.notifyDataSetChanged();

        int counter;

        counter = selectedStudentsArrayList.size();

        studentsAdapter.notifyDataSetChanged();

        if (counter == 0){
            clearActionMode();
        }else {
            txtTitle.setText(String.valueOf(counter));
        }
    }

    void addStudentsData() {

        addProgressBar.setVisibility(View.VISIBLE);

        Call<StudentAddResponse> studentAddResponseCall = RetrofitClient.getInstance().getapi().addStudent(edtEmail.getText().toString(),edtMob.getText().toString(),edtName.getText().toString(),edtRegNo.getText().toString(),loginSession.getAdminDetailsFromSession().get(KEY_DEPT_ID));

        studentAddResponseCall.clone().enqueue(new Callback<StudentAddResponse>() {
            @Override
            public void onResponse(Call<StudentAddResponse> call, Response<StudentAddResponse> response) {

                addProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            toast(StudentsListActivity.this, response.body().getMessage());
                            bottomSheetDialog.dismiss();
                        }else {
                            toast(StudentsListActivity.this,response.body().getMessage());
                        }
                    }else {
                        toast(StudentsListActivity.this,"Time out Error");
                    }
                }
            }

            @SuppressLint("LogConditional")
            @Override
            public void onFailure(Call<StudentAddResponse> call, Throwable t) {
                Log.d(TAG,t.getLocalizedMessage());
                toast(StudentsListActivity.this,"Something Went Wrong");
            }
        });
    }

    void addStudentViews(View addStudentView) {

        canceladdImg = addStudentView.findViewById(R.id.addstudent_backimg);

        addProgressBar = addStudentView.findViewById(R.id.addstudent_progressbar);

        edtName = addStudentView.findViewById(R.id.addstudent_edtname);
        edtEmail = addStudentView.findViewById(R.id.addstudent_edtemail);
        edtMob = addStudentView.findViewById(R.id.addstudent_edtmob);
        edtRegNo = addStudentView.findViewById(R.id.addstudent_edtregno);

        btnAddSave = addStudentView.findViewById(R.id.addstudent_btnsubmit);

    }

    void filter(String string) {

        ArrayList<GetAdminStudentsResponse.Student> filterlist = new ArrayList<>();

        for (GetAdminStudentsResponse.Student studentsSuitcase : arrstudentlist) {
            if (studentsSuitcase.getName().toLowerCase().contains(string.toLowerCase())) {
                filterlist.add(studentsSuitcase);
            }
        }
        studentsAdapter.filterlist(filterlist);
    }

    public void getStudentsData() {

        arrstudentlist.clear();

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.getLayoutManager().removeAllViews();

        studentsListSession = new StudentsListSession(this);

        arrstudentlist = studentsListSession.loadStudentsList(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentsAdapter = new StudentsAdapter(this,arrstudentlist);

//        saveState = recyclerView.getLayoutManager().onSaveInstanceState();

//        recyclerView.getLayoutManager().onRestoreInstanceState(saveState);

//        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));


        recyclerView.setAdapter(studentsAdapter);

//        studentsAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == EXCEL_FILE_REQUEST && resultCode == RESULT_OK && data != null) {

            uri = data.getData();
            Log.d("uri",uri.toString());

            filename = getfilename(data);

            try {
                InputStream is = getContentResolver().openInputStream(uri);
                byte[] bytesArray;
                if (is != null) {
                    bytesArray = new byte[is.available()];
                    is.read(bytesArray);

                    Log.d("exceldata", "bytearray->" + Arrays.toString(bytesArray));

                    encodedfile = Base64.encodeToString(bytesArray, Base64.DEFAULT);
                    Log.d("exceldata", "Base 64 string->" + encodedfile);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @NonNull
    public String getfilename(@NonNull Intent data) {
        String filename = null;
        if (data != null) {
            Uri uri = data.getData();

            String uristring = uri.toString();
            File file = new File(uristring);
            String filepath = file.getAbsolutePath();


            if (uristring.startsWith("content://")) {

                try (Cursor cursor = this.getContentResolver().query(uri, null, null, null, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        filename = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("filename", filename);
                    }

                }
            } else if (uristring.startsWith("file://")) {
                filename = file.getName();
                Log.d("filename", filename);
            }
        }
        return filename;
    }

    private void initviews() {

        refreshLayout = findViewById(R.id.students_refresh);

        txtTitle = findViewById(R.id.students_txttitle);


        shimmerFrameLayout = findViewById(R.id.students_shimmerlayout);

        progressBar = findViewById(R.id.students_progressbar);

        toolbar = findViewById(R.id.students_toolbar);

        recyclerView = findViewById(R.id.students_recyclerview);

        edtsearch = findViewById(R.id.students_edtsearch);

    }

    public void clearActionMode() {
        StudentsAdapter.flag = 0;
        isinActionmode = false;
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.studentsmenu);
        if (getSupportActionBar()!=null) {
            txtTitle.setVisibility(View.VISIBLE);
            txtTitle.setText("Students");
        }
        selectedStudentsArrayList.clear();

    }

    @Override
    public void onBackPressed() {
        if (isinActionmode){
            clearActionMode();
            studentsAdapter.notifyDataSetChanged();
        }else {
            super.onBackPressed();
        }
    }

}