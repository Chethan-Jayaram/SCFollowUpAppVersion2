package com.varsity.dgmdashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetNotificationListResponse {

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
    @SerializedName("message")
    @Expose
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
