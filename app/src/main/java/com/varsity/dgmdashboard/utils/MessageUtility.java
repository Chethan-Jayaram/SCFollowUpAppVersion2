package com.varsity.dgmdashboard.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class MessageUtility {
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLog(String TAG, String msg) {
        Log.e(TAG, msg);
    }

    public static String[] getSplit(String value, String regex) {
        if (value == null) return null;
        return value.split(regex);
    }
}
