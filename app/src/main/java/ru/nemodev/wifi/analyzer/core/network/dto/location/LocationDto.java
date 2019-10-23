package ru.nemodev.wifi.analyzer.core.network.dto.location;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.nemodev.wifi.analyzer.core.network.dto.BaseEntityDto;
import ru.nemodev.wifi.analyzer.core.report.ReportLocation;

public class LocationDto extends BaseEntityDto {

    @SerializedName("name")
    @Expose
    private String name;

    public LocationDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static LocationDto fromEntity(ReportLocation reportLocation) {
        LocationDto locationDto = new LocationDto();
        locationDto.setName(reportLocation.getName());

        return locationDto;
    }
}
