package ru.nemodev.wifi.analyzer.core.network.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseEntityDto {

    @SerializedName("id")
    @Expose
    private Long id;

    public BaseEntityDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
