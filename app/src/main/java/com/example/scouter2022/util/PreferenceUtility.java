package com.example.scouter2022.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.scouter2022.model.TransferCode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

public class PreferenceUtility {

    private static final String TAG = "PreferenceUtility";
    private final static String DEFAULTS = "com.example.defaults";
    private final static String MATCHES = "com.example.frc.matches";

    public static void saveAllMatches(Context context, Map<String, TransferCode> allMatches) {

        for (Map.Entry<String, TransferCode> entry: allMatches.entrySet()) {
            String key = entry.getKey();
            TransferCode value = entry.getValue();

            Log.i(TAG, "saveAllMatches: KEY ===> " + key + ", VALUE ===> " + value);
        }

        Gson gson = new Gson();
        String json = gson.toJson(allMatches);

        Log.i(TAG, "saveAllMatches: JSON ==> "+ json);
        SharedPreferences pref = context.getSharedPreferences(MATCHES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("ALL_MATCHES", json);
        editor.apply();
    }

    public static Map<String, TransferCode> getAllMatches(Context context) {
        Map<String, TransferCode> allMatches = new HashMap<>();

        Gson gson = new Gson();
        SharedPreferences pref = context.getSharedPreferences(MATCHES, Context.MODE_PRIVATE);

        Log.i(TAG, "getAllMatches...");
        if (pref.contains("ALL_MATCHES")) {
            String json = pref.getString("ALL_MATCHES", null);
            allMatches = gson.fromJson(json, new TypeToken<Map<String, TransferCode>>() {}.getType());

            for (Map.Entry<String, TransferCode> entry: allMatches.entrySet()) {
                String key = entry.getKey();
                TransferCode value = entry.getValue();
            }
        }

        return allMatches;
    }
}