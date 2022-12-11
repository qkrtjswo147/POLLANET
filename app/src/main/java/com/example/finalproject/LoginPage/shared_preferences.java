package com.example.finalproject.LoginPage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

public class shared_preferences {

    private static final boolean DEFAULT_VALUE_BOOLEAN =  false;
    static final String userID = "user_id";

    static SharedPreferences get_shared_preferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    // 정보 저장
    public static void set_user_email(Context context, String user_id) {
        SharedPreferences.Editor editor = get_shared_preferences(context).edit();
        editor.putString(userID, user_id);
        editor.commit();
    }

    // 정보 읽기
    public static String get_user_email(Context context) {
        return get_shared_preferences(context).getString(userID, "");
    }


    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences prefs = get_shared_preferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences prefs = get_shared_preferences(context);
        boolean value = prefs.getBoolean(key, DEFAULT_VALUE_BOOLEAN);
        return value;
    }

    // 로그아웃
    public static void clear_user(Context context) {
        SharedPreferences.Editor editor = get_shared_preferences(context).edit();
        editor.clear();
        editor.commit();
    }
}
