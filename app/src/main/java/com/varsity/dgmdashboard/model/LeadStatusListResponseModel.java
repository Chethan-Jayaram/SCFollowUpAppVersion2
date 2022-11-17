package com.varsity.dgmdashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeadStatusListResponseModel {
    @SerializedName("leadId")
    @Expose
    private Integer leadId;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("parentName")
    @Expose
    private String parentName;
    @SerializedName("sourceId")
    @Expose
    private Integer sourceId;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("assignedTo")
    @Expose
    private Integer assignedTo;
    @SerializedName("followUpDate")
    @Expose
    private String followUpDate;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("followupEmp")
    @Expose
    private String followupEmp;
    @SerializedName("followupEmpName")
    @Expose
    private String followupEmpName;
    @SerializedName("feedBack")
    @Expose
    private String feedBack;
    @SerializedName("reminderDate")
    @Expose
    private String reminderDate;

    public Integer getLeadId() {
        return leadId;
    }

    public void setLeadId(Integer leadId) {
        this.leadId = leadId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Integer assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(String followUpDate) {
        this.followUpDate = followUpDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFollowupEmp() {
        return followupEmp;
    }

    public void setFollowupEmp(String followupEmp) {
        this.followupEmp = followupEmp;
    }

    public String getFollowupEmpName() {
        return followupEmpName;
    }

    public void setFollowupEmpName(String followupEmpName) {
        this.followupEmpName = followupEmpName;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }
}
