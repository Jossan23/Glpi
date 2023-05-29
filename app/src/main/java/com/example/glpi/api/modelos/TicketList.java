package com.example.glpi.api.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TicketList implements Serializable {
    //Modelo que obtiene la lista de tickets con el nombre input
    // necesario para añadir el ticket en GLPI.
    @SerializedName("input")
    @Expose
    public List<Ticket> data;

    public TicketList(List<Ticket> data) {
        this.data = data;
    }

}
