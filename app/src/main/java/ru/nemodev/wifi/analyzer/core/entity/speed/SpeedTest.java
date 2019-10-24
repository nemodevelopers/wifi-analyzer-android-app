package ru.nemodev.wifi.analyzer.core.entity.speed;

import fr.bmartel.speedtest.SpeedTestReport;


public class SpeedTest {
    private final SpeedTestReport report;
    private final boolean progress;

    public SpeedTest(SpeedTestReport report) {
        this.report = report;
        this.progress = false;
    }

    public SpeedTest(SpeedTestReport report, boolean progress) {
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
