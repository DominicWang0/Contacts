package com.example.contacts.dataProcessing;

import android.content.Context;
import android.content.SharedPreferences;

public class SPSave {
    public static boolean SPSaveUserInfo(Context context, String account, String password){
        SharedPreferences sp= context.getSharedPreferences("userData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(account,password);
        editor.commit();
        return true;
    }

    public static String SPGetUserInfo(Context context, String account){
        SharedPreferences sp= context.getSharedPreferences("userData", Context.MODE_PRIVATE);
        return sp.getString(account,"not found");
    }
}
