package com.varsity.dgmdashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallStatusListResponseModel {
    @SerializedName("mobileNo")
    @Expose
    private Long mobileNo;
    @SerializedName("leadId")
    @Expose
    private Integer leadId;
    @SerializedName("studentName")
    @Expose
    private String studentName;

    @SerializedName("parentName")
    @Expose
    private String parentName;

    @SerializedName("communicatedDate")
    @Expose
    private String communicatedDate;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("intrestedCourse")
    @Expose
    private String intrestedCourse;

    @SerializedName("stuClass")
    @Expose
    private String stuClass;

    @SerializedName("batchYear")
    @Expose
    private String batchYear;

    @SerializedName("leadDistrict")
    @Expose
    private Integer leadDistrict;

    @SerializedName("mandal_id")
    @Expose
    private Integer mandal_id;

    @SerializedName("pincode")
    @Expose
    private String pincode;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("landMark")
    @Expose
    private String landMark;

    @SerializedName("alternateMobile")
    @Expose
    private String alternateMobile;

    @SerializedName("schoolId")
    @Expose
    private Integer schoolId;

    @SerializedName("hallTicketNo")
    @Expose
    private String hallTicketNo;


    @SerializedName("relationStudent")
    @Expose
    private String relationStudent;

    @SerializedName("dispositions")
    @Expose
    private String dispositions;

    @SerializedName("feedback")
    @Expose
    private String feedback;

    @SerializedName("commCity")
    @Expose
    private String commCity;

    @SerializedName("commPinId")
    @Expose
    private Integer commPinId;


    @SerializedName("school")
    @Expose
    private String school;



    public Long getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getLeadId() {
        return leadId;
    }

    public void setLeadId(Integer leadId) {
        this.leadId = leadId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCommunicatedDate() {
        return communicatedDate;
    }

    public void setCommunicatedDate(String communicatedDate) {
        this.communicatedDate = communicatedDate;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIntrestedCourse() {
        return intrestedCourse;
    }

    public void setIntrestedCourse(String intrestedCourse) {
        this.intrestedCourse = intrestedCourse;
    }

    public String getStuClass() {
        return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }

    public String getBatchYear() {
        return batchYear;
    }

    public void setBatchYear(String batchYear) {
        this.batchYear = batchYear;
    }

    public Integer getLeadDistrict() {
        return leadDistrict;
    }

    public void setLeadDistrict(Integer leadDistrict) {
        this.leadDistrict = leadDistrict;
    }

    public Integer getMandal_id() {
        return mandal_id;
    }

    public void setMandal_id(Integer mandal_id) {
        this.mandal_id = mandal_id;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getAlternateMobile() {
        return alternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getHallTicketNo() {
        return hallTicketNo;
    }

    public void setHallTicketNo(String hallTicketNo) {
        this.hallTicketNo = hallTicketNo;
    }

    public String getRelationStudent() {
        return relationStudent;
    }

    public void setRelationStudent(String relationStudent) {
        this.relationStudent = relationStudent;
    }

    public String getDispositions() {
        return dispositions;
    }

    public void setDispositions(String dispositions) {
        this.dispositions = dispositions;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getCommCity() {
        return commCity;
    }

    public void setCommCity(String commCity) {
        this.commCity = commCity;
    }

    public Integer getCommPinId() {
        return commPinId;
    }

    public void setCommPinId(Integer commPinId) {
        this.commPinId = commPinId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
