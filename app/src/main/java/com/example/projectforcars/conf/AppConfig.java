package com.example.projectforcars.conf;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import com.example.projectforcars.CarsActivity;
import com.example.projectforcars.R;


public class AppConfig {
    private Context context;
    private SharedPreferences sharedPreferences;
    public final MediaPlayer audioClick;


    public AppConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file_key), context.MODE_PRIVATE);
        //download  audio from folder raw in ressources
        audioClick = MediaPlayer.create(context, R.raw.click);

    }

    //to check the user is login or not
    public boolean isUserLogin() {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_is_user_login), false);

    }

    //this is for save login status
    public void updateUserLoginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_is_user_login), status);
        editor.apply();
    }

    //this method for saveing name of user
    public void saveNameofUser(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_name_of_user), name);
        editor.apply();//save the data
    }

    //return the name of user from sharedPreferences API
    public String getNameofUser() {
        return sharedPreferences.getString(context.getString(R.string.pref_name_of_user), "Unkonwn");
    }

    public void logout(){
        sharedPreferences.edit().clear();
        sharedPreferences.edit().commit();
        ((CarsActivity) context).finish();
    }


}