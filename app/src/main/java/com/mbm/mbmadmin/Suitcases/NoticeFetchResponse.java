package com.mbm.mbmadmin.Suitcases;


import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;


public class NoticeFetchResponse implements Serializable {

        @SerializedName("noticetable")
        @Expose
        private ArrayList<Noticetable> noticetable = null;

    @NonNull
    public ArrayList<Noticetable> getNoticetable() {
            return noticetable;
        }

        public void setNoticetable(@NonNull ArrayList<Noticetable> noticetable) {
            this.noticetable = noticetable;
        }

    public static class Noticetable implements Serializable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("dept_id")
        @Expose
        private String deptId;
        @SerializedName("notice_image_id")
        @Expose
        private String noticeImageId;
        @SerializedName("create_time")
        @Expose
        private String createTime;
        @SerializedName("images")
        @Expose
        private ArrayList<String> images;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDeptId() {
            return deptId;
        }

        public void setDeptId(String deptId) {
            this.deptId = deptId;
        }

        public String getNoticeImageId() {
            return noticeImageId;
        }

        public void setNoticeImageId(String noticeImageId) {
            this.noticeImageId = noticeImageId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public ArrayList<String> getImages() {
            return images;
        }

        public void setImages(ArrayList<String> images) {
            this.images = images;
        }

    }
}

