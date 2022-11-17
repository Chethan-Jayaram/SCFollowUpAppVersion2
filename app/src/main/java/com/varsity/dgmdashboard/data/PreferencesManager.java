package com.varsity.dgmdashboard.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.varsity.dgmdashboard.model.LoginResponseModel;
import com.varsity.dgmdashboard.utils.AppConstant;

public class PreferencesManager {
    public static final String mypreference = "dgm_dashboard_pref";

    private Context context;
    private SharedPreferences sharedPreferences;

    public PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
    }

    public String saveStringValue(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
        return key;
    }


    public String getStringValue(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void saveBooleanValue(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBooleanValue(String key) {
        return sharedPreferences.getBoolean(key, false);
    }


    public String saveIntValue(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
        return key;
    }

    public int getIntValue(String key) {
        return sharedPreferences.getInt(key,0);
    }

    public void clearData() {
        sharedPreferences.edit().clear().apply();
    }


    public void saveUserData(LoginResponseModel responseModel){
        Gson gson = new Gson();
        saveStringValue(AppConstant.USER_DATA,gson.toJson(responseModel));
    }

    public LoginResponseModel getUserData(){
        Gson gson = new Gson();
        LoginResponseModel responseModel=gson.fromJson(getStringValue(AppConstant.USER_DATA),LoginResponseModel.class);
        return responseModel;
    }
}
