package ru.nemodev.wifi.analyzer.core.report;


public class ReportLocation {

    private String id;
    private String name;

    public ReportLocation() {
    }

    public ReportLocation(String id) {
        this.id = id;
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
}
