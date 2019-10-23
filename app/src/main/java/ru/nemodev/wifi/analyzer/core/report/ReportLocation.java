package ru.nemodev.wifi.analyzer.core.report;

import ru.nemodev.wifi.analyzer.core.network.dto.location.LocationDto;

public class ReportLocation {

    private String id;
    private String name;

    public ReportLocation() {
    }

    public ReportLocation(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ReportLocation from(LocationDto dto) {
        return new ReportLocation(dto.getId(), dto.getName());
    }
}
