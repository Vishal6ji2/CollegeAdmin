package com.mbm.mbmadmin.Suitcases;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class NewsFetchResponse implements Serializable
    {

        @SerializedName("newsfeeds")
        @Expose
        private ArrayList<Newsfeed> newsfeeds = null;

        @NonNull
        public ArrayList<Newsfeed> getNewsfeeds() {
            return newsfeeds;
        }

        public void setNewsfeeds(@NonNull ArrayList<Newsfeed> newsfeeds) {
            this.newsfeeds = newsfeeds;
        }


        public static class Newsfeed implements Serializable
        {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("image_ids")
            @Expose
            private String imageIds;
            @SerializedName("news_date")
            @Expose
            private String newsDate;
            @SerializedName("news_time")
            @Expose
            private String newsTime;
            @SerializedName("news_title")
            @Expose
            private String newsTitle;
            @SerializedName("news_paragraph")
            @Expose
            private String newsParagraph;
            @SerializedName("admin_name")
            @Expose
            private String adminName;
            @SerializedName("dept_id")
            @Expose
            private String deptId;
            @SerializedName("images")
            @Expose
            private ArrayList<String> images = null;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImageIds() {
                return imageIds;
            }

            public void setImageIds(String imageIds) {
                this.imageIds = imageIds;
            }

            public String getNewsDate() {
                return newsDate;
            }

            public void setNewsDate(String newsDate) {
                this.newsDate = newsDate;
            }

            public String getNewsTime() {
                return newsTime;
            }

            public void setNewsTime(String newsTime) {
                this.newsTime = newsTime;
            }

            public String getNewsTitle() {
                return newsTitle;
            }

            public void setNewsTitle(String newsTitle) {
                this.newsTitle = newsTitle;
            }

            public String getNewsParagraph() {
                return newsParagraph;
            }

            public void setNewsParagraph(String newsParagraph) {
                this.newsParagraph = newsParagraph;
            }

            public String getAdminName() {
                return adminName;
            }

            public void setAdminName(String adminName) {
                this.adminName = adminName;
            }

            public String getDeptId() {
                return deptId;
            }

            public void setDeptId(String deptId) {
                this.deptId = deptId;
            }

            public ArrayList<String> getImages() {
                return images;
            }

            public void setImages(ArrayList<String> images) {
                this.images = images;
            }

        }

    }




