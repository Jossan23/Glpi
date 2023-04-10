package com.example.glpi.api.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileList {

    @SerializedName("active_profile")
    @Expose
    private ProfileData data;

    public ProfileList(ProfileData data) {
        this.data = data;
    }

    public ProfileData getData() {
        return data;
    }

}
