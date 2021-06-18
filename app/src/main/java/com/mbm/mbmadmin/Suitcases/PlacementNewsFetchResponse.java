package com.mbm.mbmadmin.Suitcases;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PlacementNewsFetchResponse implements Serializable {

    @SerializedName("placementnews")
    @Expose
    private ArrayList<Placementnews> placementnews = null;

    @NonNull
    public ArrayList<Placementnews> getPlacementnews() {
        return placementnews;
    }

    public void setPlacementnews(@NonNull ArrayList<Placementnews> placementnews) {
        this.placementnews = placementnews;
    }


    public static class Placementnews implements Serializable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("cmp_name")
        @Expose
        private String cmpName;
        @SerializedName("cmp_filepath")
        @Expose
        private String cmpFilepath;
        @SerializedName("cmp_filename")
        @Expose
        private String cmpFilename;
        @SerializedName("cmp_title")
        @Expose
        private String cmpTitle;
        @SerializedName("cmp_news")
        @Expose
        private String cmpNews;
        @SerializedName("cmp_time")
        @Expose
        private String cmpTime;
        @SerializedName("cmp_uploadeby")
        @Expose
        private String cmpUploadeby;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCmpName() {
            return cmpName;
        }

        public void setCmpName(String cmpName) {
            this.cmpName = cmpName;
        }

        public String getCmpFilepath() {
            return cmpFilepath;
        }

        public void setCmpFilepath(String cmpFilepath) {
            this.cmpFilepath = cmpFilepath;
        }

        public String getCmpFilename() {
            return cmpFilename;
        }

        public void setCmpFilename(String cmpFilename) {
            this.cmpFilename = cmpFilename;
        }

        public String getCmpTitle() {
            return cmpTitle;
        }

        public void setCmpTitle(String cmpTitle) {
            this.cmpTitle = cmpTitle;
        }

        public String getCmpNews() {
            return cmpNews;
        }

        public void setCmpNews(String cmpNews) {
            this.cmpNews = cmpNews;
        }

        public String getCmpTime() {
            return cmpTime;
        }

        public void setCmpTime(String cmpTime) {
            this.cmpTime = cmpTime;
        }

        public String getCmpUploadeby() {
            return cmpUploadeby;
        }

        public void setCmpUploadeby(String cmpUploadeby) {
            this.cmpUploadeby = cmpUploadeby;
        }

    }
}
