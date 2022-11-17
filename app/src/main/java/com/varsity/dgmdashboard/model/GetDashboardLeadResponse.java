package com.varsity.dgmdashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDashboardLeadResponse {
    @SerializedName("leadStatus")
    @Expose
    private String leadStatus;
    @SerializedName("count")
    @Expose
    private Integer count;

    public String getLeadStatus() {
        return leadStatus;
    }

    public void setLeadStatus(String leadStatus) {
        this.leadStatus = leadStatus;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
