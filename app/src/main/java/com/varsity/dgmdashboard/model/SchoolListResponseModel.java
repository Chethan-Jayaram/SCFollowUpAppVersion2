package com.varsity.dgmdashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchoolListResponseModel {
    @SerializedName("schoolId")
    @Expose
    private Integer schoolId;
    @SerializedName("schoolName")
    @Expose
    private String schoolName;
    @SerializedName("scCode")
    @Expose
    private String scCode;


    public SchoolListResponseModel(Integer schoolId, String schoolName, String scCode) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.scCode = scCode;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getScCode() {
        return scCode;
    }

    public void setScCode(String scCode) {
        this.scCode = scCode;
    }

    @Override
    public String toString() {
        return schoolName;
    }
}

