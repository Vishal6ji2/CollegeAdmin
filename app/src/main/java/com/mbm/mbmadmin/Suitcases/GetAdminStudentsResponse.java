package com.mbm.mbmadmin.Suitcases;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;


public class GetAdminStudentsResponse implements Serializable
{

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public class Data implements Serializable
    {

        @SerializedName("admin")
        @Expose
        private ArrayList<Admin> admin = null;
        @SerializedName("student")
        @Expose
        private ArrayList<Student> student = null;

        public ArrayList<Admin> getAdmin() {
            return admin;
        }

        public void setAdmin(ArrayList<Admin> admin) {
            this.admin = admin;
        }

        public ArrayList<Student> getStudent() {
            return student;
        }

        public void setStudent(ArrayList<Student> student) {
            this.student = student;
        }

    }

    public class Student implements Serializable
    {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("dept_id")
        @Expose
        private String deptId;
        @SerializedName("reg_no")
        @Expose
        private String regNo;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("profile")
        @Expose
        private String profile;
        @SerializedName("department_name")
        @Expose
        private String departmentName;

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

        public String getRegNo() {
            return regNo;
        }

        public void setRegNo(String regNo) {
            this.regNo = regNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

    }

    public class Admin implements Serializable
    {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("dept_id")
        @Expose
        private String deptId;
        @SerializedName("image_url")
        @Expose
        private String imageUrl;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("super_admin")
        @Expose
        private String superAdmin;
        @SerializedName("department_name")
        @Expose
        private String departmentName;

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

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSuperAdmin() {
            return superAdmin;
        }

        public void setSuperAdmin(String superAdmin) {
            this.superAdmin = superAdmin;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

    }
}


