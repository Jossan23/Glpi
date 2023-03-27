package com.example.glpi.api.get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ticket implements Serializable {

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


    @Override
    public String toString() {
        return "Ticket{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", urgency=" + urgency +
                '}';
    }


    public Ticket(String name, String content, int urgency){
        this.name = name;
        this.content = content;
        this.urgency = urgency;
    }

    public Ticket(String name, String content, int status, int urgency) {
        this.name = name;
        this.content = content;
        this.status = status;
        this.urgency = urgency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }
}
