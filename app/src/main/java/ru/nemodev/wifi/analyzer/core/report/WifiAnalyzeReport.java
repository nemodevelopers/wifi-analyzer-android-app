package ru.nemodev.wifi.analyzer.core.report;

import java.util.Calendar;
import java.util.List;

import ru.nemodev.wifi.analyzer.core.device.DeviceInfo;
import ru.nemodev.wifi.analyzer.core.wifi.WifiAnalyzeInfo;

public class WifiAnalyzeReport {

    private DeviceInfo deviceInfo;
    private List<WifiAnalyzeInfo> wifiAnalyzeInfoList;
    private ReportLocation location;
    private String comment;

    public WifiAnalyzeReport() {
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public List<WifiAnalyzeInfo> getWifiAnalyzeInfoList() {
        return wifiAnalyzeInfoList;
    }

    public void setWifiAnalyzeInfoList(List<WifiAnalyzeInfo> wifiAnalyzeInfoList) {
        this.wifiAnalyzeInfoList = wifiAnalyzeInfoList;
    }

    public ReportLocation getLocation() {
        return location;
    }

    public void setLocation(ReportLocation location) {
        this.location = location;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
