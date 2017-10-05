package co.com.etn.arquitecturamvpbase.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by draiven on 10/3/17.
 */

public class Customer implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("surname")
    @Expose
    private String surName;

    @SerializedName("phoneList")
    @Expose
    private ArrayList<PhoneList> phoneList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public ArrayList<PhoneList> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(ArrayList<PhoneList> phoneList) {
        this.phoneList = phoneList;
    }
}
