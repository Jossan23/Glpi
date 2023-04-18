package com.example.glpi.api.modelos;

import com.google.gson.annotations.SerializedName;

public class InitSession {

    //Modelo para obtener el token de sesion al iniciar sesion con usuario y contraseña.
    @SerializedName("session_token")
    private String sessionToken;

    public String getSessionToken() {
        return sessionToken;
    }

}
