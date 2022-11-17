package com.varsity.dgmdashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateNotificationResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("empID")
    @Expose
    private Integer empID;
    @SerializedName("leadCount")
    @Expose
    private Integer leadCount;
    @SerializedName("isRead")
    @Expose
    private Boolean isRead;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpID() {
        return empID;
    }

    public void setEmpID(Integer empID) {
        this.empID = empID;
    }

    public Integer getLeadCount() {
        return leadCount;
    }

    public void setLeadCount(Integer leadCount) {
        this.leadCount = leadCount;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}
