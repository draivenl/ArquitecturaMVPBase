package co.com.etn.arquitecturamvpbase.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by draiven on 10/3/17.
 */

public class PhoneList implements Serializable {
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("number")
    @Expose
    private String number;

    @SerializedName("location")
    @Expose
    private Location location;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
