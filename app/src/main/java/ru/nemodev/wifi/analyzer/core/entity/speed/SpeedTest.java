package ru.nemodev.wifi.analyzer.core.entity.speed;

import android.net.wifi.WifiInfo;

import fr.bmartel.speedtest.SpeedTestReport;


public class SpeedTest {
    private final WifiInfo wifiInfo;
    private final SpeedTestReport report;
    private final boolean progress;

    public SpeedTest(WifiInfo wifiInfo, SpeedTestReport report) {
        this.wifiInfo = wifiInfo;
        this.report = report;
        this.progress = false;
    }

    public SpeedTest(WifiInfo wifiInfo, SpeedTestReport report, boolean progress) {
        this.wifiInfo = wifiInfo;
        this.report = report;
        this.progress = progress;
    }

    public SpeedTestReport getReport() {
        return report;
    }

    public boolean isProgress() {
        return progress;
    }
}
