package com.mbm.mbmadmin.ModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mbm.mbmadmin.Suitcases.DeptSuitcase;

import java.util.List;

public class DeptFetchResponse {

    @SerializedName("stuff")
    @Expose
    private List<DeptSuitcase> stuff = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<DeptSuitcase> getStuff() {
        return stuff;
    }

    public void setStuff(List<DeptSuitcase> stuff) {
        this.stuff = stuff;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
