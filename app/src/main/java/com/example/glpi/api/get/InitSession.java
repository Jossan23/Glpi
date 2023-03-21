package com.example.glpi.api.get;

import com.google.gson.annotations.SerializedName;

public class InitSession {

    @SerializedName("session_token")
    private String sessionToken;

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
