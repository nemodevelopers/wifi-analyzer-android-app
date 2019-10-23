package ru.nemodev.wifi.analyzer.core.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseEntityDto {

    @SerializedName("id")
    @Expose
    private String id;

    public BaseEntityDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
