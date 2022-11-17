package com.varsity.dgmdashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponseModel {
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("token_type")
    @Expose
    private String tokenType;
    @SerializedName("expires_in")
    @Expose
    private Integer expiresIn;
    @SerializedName("scope")
    @Expose
    private String scope;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("managerPayrollId")
    @Expose
    private String managerPayrollId;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("campusName")
    @Expose
    private String campusName;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("isCollege")
    @Expose
    private Boolean isCollege;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("roles")
    @Expose
    private List<String> roles = null;
    @SerializedName("payrollId")
    @Expose
    private String payrollId;
    @SerializedName("managerName")
    @Expose
    private String managerName;
    @SerializedName("managerAvailable")
    @Expose
    private Boolean managerAvailable;
    @SerializedName("bloodGroup")
    @Expose
    private String bloodGroup;

    @SerializedName("aadhar")
    @Expose
    private String aadhar;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("empDesig")
    @Expose
    private String empDesig;
    @SerializedName("pfNo")
    @Expose
    private String pfNo;
    @SerializedName("panNo")
    @Expose
    private String panNo;
    @SerializedName("managerMobile")
    @Expose
    private String managerMobile;
    @SerializedName("employeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("managerDesig")
    @Expose
    private String managerDesig;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("doj")
    @Expose
    private String doj;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getManagerPayrollId() {
        return managerPayrollId;
    }

    public void setManagerPayrollId(String managerPayrollId) {
        this.managerPayrollId = managerPayrollId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getCollege() {
        return isCollege;
    }

    public void setCollege(Boolean college) {
        isCollege = college;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(String payrollId) {
        this.payrollId = payrollId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public Boolean getManagerAvailable() {
        return managerAvailable;
    }

    public void setManagerAvailable(Boolean managerAvailable) {
        this.managerAvailable = managerAvailable;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmpDesig() {
        return empDesig;
    }

    public void setEmpDesig(String empDesig) {
        this.empDesig = empDesig;
    }

    public String getPfNo() {
        return pfNo;
    }

    public void setPfNo(String pfNo) {
        this.pfNo = pfNo;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getManagerMobile() {
        return managerMobile;
    }

    public void setManagerMobile(String managerMobile) {
        this.managerMobile = managerMobile;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getManagerDesig() {
        return managerDesig;
    }

    public void setManagerDesig(String managerDesig) {
        this.managerDesig = managerDesig;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }
}
