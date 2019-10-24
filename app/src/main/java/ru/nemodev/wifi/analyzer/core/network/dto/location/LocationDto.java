package ru.nemodev.wifi.analyzer.core.network.dto.location;

import java.util.ArrayList;
import java.util.List;

import ru.nemodev.wifi.analyzer.core.network.dto.BaseEntityDto;
import ru.nemodev.wifi.analyzer.core.report.ReportLocation;

public class LocationDto extends BaseEntityDto {

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
        locationDto.setId(reportLocation.getId());
        locationDto.setName(reportLocation.getName());

        return locationDto;
    }

    public static List<ReportLocation> toEntityList(List<LocationDto> locationDtoList) {
        List<ReportLocation> locations = new ArrayList<>();

        for (LocationDto locationDto : locationDtoList) {
            locations.add(toEntity(locationDto));
        }

        return locations;
    }

    public static ReportLocation toEntity(LocationDto locationDto) {
        ReportLocation location = new ReportLocation();
        location.setId(locationDto.getId());
        location.setName(locationDto.getName());

        return location;
    }

}
