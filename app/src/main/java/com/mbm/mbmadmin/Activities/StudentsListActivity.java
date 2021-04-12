package com.mbm.mbmadmin.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.mbm.mbmadmin.Adapters.StudentsAdapter;
import com.mbm.mbmadmin.ModelResponse.PlacementnewsResponse;
import com.mbm.mbmadmin.ModelResponse.StudentsExcelResponse;
import com.mbm.mbmadmin.R;
import com.mbm.mbmadmin.RetrofitClient;
import com.mbm.mbmadmin.Suitcases.EbooksSuitcase;
import com.mbm.mbmadmin.Suitcases.StudentsSuitcase;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentsListActivity extends AppCompatActivity {

    ImageView backimg, okimg, excelimg;

    EditText edtsearch;

    RecyclerView recyclerView;

    MaterialToolbar toolbar;

    ProgressBar progressBar;

    ArrayList<StudentsSuitcase> arrstudentlist = new ArrayList<>();

    StudentsAdapter studentsAdapter;

    int EXCEL_FILE_REQUEST = 100;

    // student bottomsheet layout views
    CircularImageView profileimg;
    ImageView cancelimg;
    TextView txtname, txtemail, txtmob, txtbranch;

    String encodedfile,filename;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        initviews();

        setSupportActionBar(toolbar);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        studentsAdapter = new StudentsAdapter(this, arrstudentlist);

        okimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentsListActivity.this, "Status Changed Successfully", Toast.LENGTH_SHORT).show();
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

        excelimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                startActivityForResult(intent, EXCEL_FILE_REQUEST);
            }
        });

        addStudentsData("Vishal Kumavat", "v6k7@gmail.com", "7239973921", "Computer Science & Engineering", R.drawable.profilefive, "1");
        addStudentsData("Vishal Dadlani", "v6k7@gmail.com", "7239973921", "Computer Science & Engineering", R.drawable.profilefour, "0");
        addStudentsData("Rahul Kumavat", "v6k7@gmail.com", "7239973921", "Computer Science & Engineering", R.drawable.profilefive, "1");
        addStudentsData("Vishal khandelwal", "v6k7@gmail.com", "7239973921", "Computer Science & Engineering", R.drawable.profilefive, "0");
        addStudentsData("Ram Kumar", "v6k7@gmail.com", "7239973921", "Computer Science & Engineering", R.drawable.profilefour, "0");
        addStudentsData("Monica", "v6k7@gmail.com", "7239973921", "Computer Science & Engineering", R.drawable.profilefour, "1");
        addStudentsData("Honey Singh", "v6k7@gmail.com", "7239973921", "Computer Science & Engineering", R.drawable.profilefive, "0");
        addStudentsData("Ramesh chand", "v6k7@gmail.com", "7239973921", "Computer Science & Engineering", R.drawable.profilefour, "1");
        addStudentsData("Deepak Rao", "v6k7@gmail.com", "7239973921", "Computer Science & Engineering", R.drawable.profilefive, "0");
        addStudentsData("Tarun kumar", "v6k7@gmail.com", "7239973921", "Computer Science & Engineering", R.drawable.profilefour, "1");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(studentsAdapter);
        studentsAdapter.onClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = "";
//                URLUtil.isFileUrl(url);

                int position = recyclerView.getChildAdapterPosition(v);
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(StudentsListActivity.this, R.style.BottomSheetDialogTheme);
                View bottomsheetview = LayoutInflater.from(StudentsListActivity.this).inflate(R.layout.fullstudentlayout, (LinearLayout) findViewById(R.id.studentprofile_layout));
                studentbottomsheetviews(bottomsheetview);
                txtname.setText(arrstudentlist.get(position).strname);
                txtmob.setText(arrstudentlist.get(position).strmob);
                txtemail.setText(arrstudentlist.get(position).stremail);
                txtbranch.setText(arrstudentlist.get(position).strbranch);
                profileimg.setImageResource(arrstudentlist.get(position).profileimg);
                cancelimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomsheetview);
                bottomSheetDialog.show();
            }
        });


    }

    private void filter(String string) {

        ArrayList<StudentsSuitcase> filterlist = new ArrayList<>();

        for (StudentsSuitcase studentsSuitcase : arrstudentlist) {
            if (studentsSuitcase.strname.toLowerCase().contains(string.toLowerCase())) {
                filterlist.add(studentsSuitcase);
            }
        }
        studentsAdapter.filterlist(filterlist);
    }


    public void addStudentsData(String name, String email, String mob, String branch, int profileimg, String status) {

        StudentsSuitcase studentsSuitcase = new StudentsSuitcase();
        studentsSuitcase.strname = name;
        studentsSuitcase.stremail = email;
        studentsSuitcase.strmob = mob;
        studentsSuitcase.strbranch = branch;
        studentsSuitcase.profileimg = profileimg;
        studentsSuitcase.status = status;

        arrstudentlist.add(studentsSuitcase);
    }

    public void studentbottomsheetviews(View view) {
        profileimg = view.findViewById(R.id.studentprofile_profileimg);
        txtname = view.findViewById(R.id.studentprofile_txtname);
        txtmob = view.findViewById(R.id.studentprofile_txtmob);
        txtemail = view.findViewById(R.id.studentprofile_txtemail);
        txtbranch = view.findViewById(R.id.studentprofile_txtbranch);
        cancelimg = view.findViewById(R.id.studentprofile_cancelimg);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == EXCEL_FILE_REQUEST && resultCode == RESULT_OK && data != null) {

            Uri selectpdf = data.getData();
            Log.d("uri",selectpdf.toString());

            filename = getfilename(selectpdf,data);


            //String path= getPDFPath(selectpdf);
            //Commons.getPath(selectpdf, this);
            //
//            String lastpath = path.substring(path.indexOf("/storage"));

//            String id = DocumentsContract.getDocumentId(selectpdf);
//                Log.d("id", id);

            try {
                InputStream is = getContentResolver().openInputStream(selectpdf);
                byte[] bytesArray;
                if (is != null) {
                    bytesArray = new byte[is.available()];
                    is.read(bytesArray);

                    Log.d("exceldata", "bytearray->" + bytesArray.toString());

                    encodedfile = Base64.encodeToString(bytesArray, Base64.DEFAULT);
                    Log.d("exceldata", "Base 64 string->" + encodedfile);

                    uploadExcelData();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void uploadExcelData() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.VISIBLE);

        Call<StudentsExcelResponse> studentsExcelResponseCall = RetrofitClient.getInstance()
                .getapi()
                .excelupload(encodedfile,filename);

        studentsExcelResponseCall.enqueue(new Callback<StudentsExcelResponse>() {
            @Override
            public void onResponse(@NotNull Call<StudentsExcelResponse> call, @NotNull Response<StudentsExcelResponse> response) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                StudentsExcelResponse studentsExcelResponse = response.body();
                if (studentsExcelResponse != null && response.isSuccessful()) {
                    Toast.makeText(StudentsListActivity.this, studentsExcelResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    if (studentsExcelResponse.getMessage().equals("Students List Uploaded Successfully")){
                        Log.d("studentdata",studentsExcelResponse.getMessage());
                    }else {
                        Log.d("studentdata","\n"+studentsExcelResponse.getError());
                    }

                }else{
                    Toast.makeText(StudentsListActivity.this,studentsExcelResponse.getMessage(),Toast.LENGTH_SHORT).show();
//                    bottomSheetDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NotNull Call<StudentsExcelResponse> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Log.d("studentfail",call.toString()+"\n"+t.getMessage());
                Log.d("student",t.getMessage()+"\n"+new Gson().toJson(call.request().body())+"\n"+call.request().body());
                Toast.makeText(StudentsListActivity.this, "Students list upload failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public String getfilename(Uri uri, Intent data) {
        String filename = null;
        if (data != null) {
            uri = data.getData();

            String uristring = uri.toString();
            File file = new File(uristring);
            String mypath = file.getAbsolutePath();


            if (uristring.startsWith("content://")) {
                Cursor cursor = null;

                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        filename = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("filename", filename);
                    }

                } finally {
                    cursor.close();
                }
            } else if (uristring.startsWith("file://")) {
                filename = file.getName();
                Log.d("filename", filename);
            }
        }
        return filename;
    }


    private void initviews() {

        progressBar = findViewById(R.id.students_progressbar);

        excelimg = findViewById(R.id.students_uploadimg);
        okimg = findViewById(R.id.students_checkimg);
        backimg = findViewById(R.id.students_backimg);

        toolbar = findViewById(R.id.students_toolbar);

        recyclerView = findViewById(R.id.students_recyclerview);

        edtsearch = findViewById(R.id.students_edtsearch);

    }

    /*public void ReadExcel(String filePath,String fileName,String sheetName) throws InterruptedException, IOException{
        File file = new File(filePath);
        Log.d("newfile",""+file);
        //Create an object of FileInputStream class to read excel file
        FileInputStream inputStream = new FileInputStream(file);
        Workbook AddCatalog = null;
        //Find the file extension by splitting file name in substring  and getting only extension name
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        Log.d("extension",fileExtensionName);
        //Check condition if the file is a .xls file or .xlsx file
        if(fileExtensionName.equals(".xls")){
            //If it is .xls file then create object of HSSFWorkbook class
            AddCatalog = new HSSFWorkbook(inputStream);
        }
        else if(fileExtensionName.equals(".xlsx")){
            //If it is .xlsx file then create object of XSSFWorkbook class
            AddCatalog = new XSSFWorkbook(inputStream);
        }
        //Read sheet inside the workbook by its name
        Sheet AddCatalogSheet = null;
        if (AddCatalog != null) {
            AddCatalogSheet = AddCatalog.getSheet(sheetName);

            //Find number of rows in excel file
            int rowcount = AddCatalogSheet.getLastRowNum() - AddCatalogSheet.getFirstRowNum();
//        System.out.println("Total row number: "+rowcount);

            Log.d("totalrow", "" + rowcount);
            for (int i = 1; i < rowcount + 1; i++) {
                //Create a loop to get the cell values of a row for one iteration
                Row row = AddCatalogSheet.getRow(i);
                List<String> arrName = new ArrayList<String>();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    // Create an object reference of 'Cell' class
                    Cell cell = row.getCell(j);
                    // Add all the cell values of a particular row
                    arrName.add(cell.getStringCellValue());
                }
//            System.out.println(arrName);
//            System.out.println("Size of the arrayList: "+arrName.size());
                Log.d("arraysize", "" + arrName.size());
                // Create an iterator to iterate through the arrayList- 'arrName'

                for (String s : arrName) {
//                System.out.println("arrayList values: "+itr.next());
                    Log.d("arraylist", s);
                }
            }
        }

    }*/
}