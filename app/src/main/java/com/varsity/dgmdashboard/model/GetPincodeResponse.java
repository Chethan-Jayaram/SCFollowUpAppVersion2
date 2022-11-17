package com.varsity.dgmdashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetPincodeResponse {
    @SerializedName("pincode")
    @Expose
    private Integer pincode;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("districtName")
    @Expose
    private String districtName;
    @SerializedName("districtId")
    @Expose
    private Integer districtId;
    @SerializedName("pinOffices")
    @Expose
    private ArrayList<PinOffice> pinOffices = null;
    @SerializedName("cities")
    @Expose
    private ArrayList<City> cities = null;
    @SerializedName("mandals")
    @Expose
    private ArrayList<Mandal> mandals = null;

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public ArrayList<PinOffice> getPinOffices() {
        return pinOffices;
    }

    public void setPinOffices(ArrayList<PinOffice> pinOffices) {
        this.pinOffices = pinOffices;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public ArrayList<Mandal> getMandals() {
        return mandals;
    }

    public void setMandals(ArrayList<Mandal> mandals) {
        this.mandals = mandals;
    }

    public class Mandal {

        @SerializedName("mandalId")
        @Expose
        private Integer mandalId;
        @SerializedName("mandalName")
        @Expose
        private String mandalName;

        public Integer getMandalId() {
            return mandalId;
        }

        public void setMandalId(Integer mandalId) {
            this.mandalId = mandalId;
        }

        public String getMandalName() {
            return mandalName;
        }

        public void setMandalName(String mandalName) {
            this.mandalName = mandalName;
        }

    }

    public class PinOffice {

        @SerializedName("pinId")
        @Expose
        private Integer pinId;
        @SerializedName("pincode")
        @Expose
        private Integer pincode;
        @SerializedName("officeName")
        @Expose
        private String officeName;

        public Integer getPinId() {
            return pinId;
        }

        public void setPinId(Integer pinId) {
            this.pinId = pinId;
        }

        public Integer getPincode() {
            return pincode;
        }

        public void setPincode(Integer pincode) {
            this.pincode = pincode;
        }

        public String getOfficeName() {
            return officeName;
        }

        public void setOfficeName(String officeName) {
            this.officeName = officeName;
        }

        @Override
        public String toString() {
            return officeName;
        }
    }

    public class City {

        @SerializedName("cityId")
        @Expose
        private Integer cityId;
        @SerializedName("cityName")
        @Expose
        private String cityName;

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        @Override
        public String toString() {
            return cityName;
        }
    }
}
