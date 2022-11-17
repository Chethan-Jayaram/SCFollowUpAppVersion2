package com.varsity.dgmdashboard.model;

public class ProAddNoteRequest {
    private int assignedTo;
    private String comment;
    private String feedBack;
    private String followUpType;
    private boolean followupStatus;
    private int leadId;
    private int leadStatusId;
    private String reminder;

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public int getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public String getFollowUpType() {
        return followUpType;
    }

    public void setFollowUpType(String followUpType) {
        this.followUpType = followUpType;
    }

    public boolean isFollowupStatus() {
        return followupStatus;
    }

    public void setFollowupStatus(boolean followupStatus) {
        this.followupStatus = followupStatus;
    }

    public int getLeadId() {
        return leadId;
    }

    public void setLeadId(int leadId) {
        this.leadId = leadId;
    }

    public int getLeadStatusId() {
        return leadStatusId;
    }

    public void setLeadStatusId(int leadStatusId) {
        this.leadStatusId = leadStatusId;
    }
}
