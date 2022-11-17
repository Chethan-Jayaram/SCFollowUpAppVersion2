package com.varsity.dgmdashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProDetailsLeadResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ASSIGNED_TO")
    @Expose
    private Integer assignedTo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("communicatedLeads")
    @Expose
    private Integer communicatedLeads;
    @SerializedName("completedLeads")
    @Expose
    private Integer completedLeads;
    @SerializedName("pendingLeads")
    @Expose
    private Integer pendingLeads;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Integer assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCommunicatedLeads() {
        return communicatedLeads;
    }

    public void setCommunicatedLeads(Integer communicatedLeads) {
        this.communicatedLeads = communicatedLeads;
    }

    public Integer getCompletedLeads() {
        return completedLeads;
    }

    public void setCompletedLeads(Integer completedLeads) {
        this.completedLeads = completedLeads;
    }

    public Integer getPendingLeads() {
        return pendingLeads;
    }

    public void setPendingLeads(Integer pendingLeads) {
        this.pendingLeads = pendingLeads;
    }
}
