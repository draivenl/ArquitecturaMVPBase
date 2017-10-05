package co.com.etn.arquitecturamvpbase.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by draiven on 10/3/17.
 */

public class Location implements Serializable {
    @SerializedName("coordinates")
    @Expose
    Double[] coordinates;

    @SerializedName("type")
    @Expose
    String type;

    public Double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
