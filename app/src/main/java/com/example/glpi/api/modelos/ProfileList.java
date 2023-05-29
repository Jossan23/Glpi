package com.example.glpi.api.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileList {

    //Clase para obtener los datos del perfil que esta actualmente iniciando sesion.
    @SerializedName("active_profile")
    @Expose
    public ProfileData data;

    public ProfileList(ProfileData data) {
        this.data = data;
    }

}
