package com.example.glpi.api.modelos;

import com.example.glpi.api.get.Ticket;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TicketList implements Serializable {
    @SerializedName("input")
    @Expose
    private List<Ticket> data;

    public TicketList(List<Ticket> data) {
        this.data = data;
    }

}
