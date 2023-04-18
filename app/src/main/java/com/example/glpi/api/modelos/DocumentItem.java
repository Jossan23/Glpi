package com.example.glpi.api.modelos;

import com.google.gson.annotations.SerializedName;

public class DocumentItem {

    //Clase que obtiene el id del Documento.
    @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
