package com.example.glpi.api.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ticket implements Serializable {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("content")
    @Expose
    public String content;
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("urgency")
    @Expose
    public int urgency;
    @SerializedName("users_id_recipient")
    @Expose
    public String userCreatorTicker;

    @SerializedName("type")
    public int type;


    public Ticket(int id, int urgency, int status) {
        this.id = id;
        this.urgency = urgency;
        this.status = status;
    }


    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", urgency=" + urgency +
                ", userCreatorTicker='" + userCreatorTicker + '\'' +
                '}';
    }

    public String getUserCreatorTicker() {
        return userCreatorTicker;
    }

    public void setUserCreatorTicker(String userCreatorTicker) {
        this.userCreatorTicker = userCreatorTicker;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ticket(String name, String content, int status, int urgency, int type) {
        this.name = name;
        this.content = content;
        this.status = status;
        this.urgency = urgency;
        this.type = type;
    }

    public String getName() {
        return name;
    }


    public String getContent() {
        return content;
    }


    public int getStatus() {
        return status;
    }


    public int getUrgency() {
        return urgency;
    }

    public int getType() {
        return type;
    }
}
