package com.varsity.dgmdashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusListResponseModel {


    public StatusListResponseModel(Integer id, Boolean leadCloseable, String statusName) {
        this.id = id;
        this.leadCloseable = leadCloseable;
        this.statusName = statusName;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("leadCloseable")
    @Expose
    private Boolean leadCloseable;
    @SerializedName("statusName")
    @Expose
    private String statusName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getLeadCloseable() {
        return leadCloseable;
    }

    public void setLeadCloseable(Boolean leadCloseable) {
        this.leadCloseable = leadCloseable;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
