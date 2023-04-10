package com.example.glpi.api.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileData {

    @SerializedName("name")
    @Expose
    private String name;

    public ProfileData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
