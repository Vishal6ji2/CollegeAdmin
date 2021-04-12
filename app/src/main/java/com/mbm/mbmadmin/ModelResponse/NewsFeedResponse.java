package com.mbm.mbmadmin.ModelResponse;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewsFeedResponse implements Serializable {

    @SerializedName("error")
    @Expose
    private Boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    public NewsFeedResponse(){

    }

    @NonNull
    public Boolean getError() {
        return error;
    }

    public void setError(@NonNull Boolean error) {
        this.error = error;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    public void setMessage(@NonNull String message) {
        this.message = message;
    }

}
