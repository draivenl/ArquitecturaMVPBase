package co.com.etn.arquitecturamvpbase.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import co.com.etn.arquitecturamvpbase.model.User;

/**
 * Created by Lisandro Gómez on 10/17/17.
 */

public class CustomSharedPreferences {

    private SharedPreferences sharedPreferences;

    public CustomSharedPreferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE);
    }


    public String getString(String key) {
        if (sharedPreferences.contains(key)) {
            return sharedPreferences.getString(key, null);
        }
        return null;
    }


    public void addString(String key, String value) {
        if (value == null) {
            deleteValue(key);
        } else {
            addValue(key, value);
        }
    }


    public void addInt(String key, Integer value) {
        if (value == null) {
            deleteValue(key);
        } else {
            addValue(key, value);
        }
    }


    public Integer getInt(String key) {
        if (sharedPreferences.contains(key)) {
            return sharedPreferences.getInt(key, 1);
        }
        return null;
    }


    public void saveObjectUser(String key, User user){

        Gson gson = new Gson();
        String json = gson.toJson(user);
        addValue(key, json);
    }

    private void addValue(String key, String json) {
        sharedPreferences.edit().putString(key, json).commit();
    }

    public User getObjectUser(String key) {
        Gson gson =  new Gson();
        String json = sharedPreferences.getString(key, "");
        User user = gson.fromJson(json, User.class);
        return user;
    }

    public  void deleteValue(String key){
        sharedPreferences.edit().remove(key).commit();


    }

    private void addValue(String key, Integer value) {
        sharedPreferences.edit().putInt(key, value).commit();
    }
}
