package com.mbm.mbmadmin.ModelResponse.AddResponses;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddLinkResponse implements Serializable
    {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("message")
        @Expose
        private String message;

        @NonNull
        public Integer getStatus() {
            return status;
        }

        public void setStatus(@NonNull Integer status) {
            this.status = status;
        }

        @NonNull
        public String getMessage() {
            return message;
        }

        public void setMessage(@NonNull String message) {
            this.message = message;
        }

    }

