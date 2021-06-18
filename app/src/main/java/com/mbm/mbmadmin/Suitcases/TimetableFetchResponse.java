package com.mbm.mbmadmin.Suitcases;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class TimetableFetchResponse implements Serializable {

    @SerializedName("timetables")
    @Expose
    private ArrayList<Timetable> timetables = null;

    @NonNull
    public ArrayList<Timetable> getTimetables() {
            return timetables;
    }
    public void setTimetables(@NonNull ArrayList<Timetable> timetables) {
        this.timetables = timetables;
    }

    public static class Timetable implements Serializable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("timetableimage_path")
        @Expose
        private String timetableimagePath;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTimetableimagePath() {
            return timetableimagePath;
        }

        public void setTimetableimagePath(String timetableimagePath) {
            this.timetableimagePath = timetableimagePath;
        }
    }

}
