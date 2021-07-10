package com.mbm.mbmadmin.Sessions;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mbm.mbmadmin.Suitcases.GetAdminStudentsResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StudentsListSession {

    private static final String KEY_STUDENTS = "studentsListSession" ;
    SharedPreferences studentsListSession;

    SharedPreferences.Editor editor;

    Context context;
    private static final String studentsList = "studentsList";


    public StudentsListSession(@NonNull Context context){
        this.context = context;

        studentsListSession = context.getSharedPreferences(KEY_STUDENTS,Context.MODE_PRIVATE);
        editor = studentsListSession.edit();
    }

    public void saveStudentsList(@NonNull ArrayList<GetAdminStudentsResponse.Student> arrStudentsList){

        studentsListSession = context.getSharedPreferences(KEY_STUDENTS,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(arrStudentsList);
        editor.putString(studentsList,json);
        editor.apply();

    }

    @NonNull
    public ArrayList<GetAdminStudentsResponse.Student> loadStudentsList(@NonNull Context context){

        studentsListSession = context.getSharedPreferences(KEY_STUDENTS,Context.MODE_PRIVATE);
        String json = studentsListSession.getString(studentsList,"");

        Gson gson = new Gson();

        Type type = new TypeToken<ArrayList<GetAdminStudentsResponse.Student>>(){}.getType();

        return gson.fromJson(json,type);
    }

    public void removeStudentsList(){
        editor.clear();
        editor.commit();
    }

}
