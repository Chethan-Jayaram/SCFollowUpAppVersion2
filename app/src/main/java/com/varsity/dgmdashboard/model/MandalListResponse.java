package com.varsity.dgmdashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MandalListResponse {
    @SerializedName("mandalId")
    @Expose
    private Integer mandalId;
    @SerializedName("mandalName")
    @Expose
    private String mandalName;

    public MandalListResponse(Integer mandalId, String mandalName) {
        this.mandalId = mandalId;
        this.mandalName = mandalName;
    }

    public Integer getMandalId() {
        return mandalId;
    }

    public void setMandalId(Integer mandalId) {
        this.mandalId = mandalId;
    }

    public String getMandalName() {
        return mandalName;
    }

    public void setMandalName(String mandalName) {
        this.mandalName = mandalName;
    }

    @Override
    public String toString() {
        return mandalName;
    }
}
