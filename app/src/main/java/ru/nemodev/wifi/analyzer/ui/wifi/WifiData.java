package ru.nemodev.wifi.analyzer.ui.wifi;

public class WifiData {
    private String SSID;
    private String BSSID;
    private int RSSI;
    private int frequency;

    public WifiData() {
    }

    public WifiData(String SSID, String BSSID, int RSSI, int frequency) {
        this.SSID = SSID;
        this.BSSID = BSSID;
        this.RSSI = RSSI;
        this.frequency = frequency;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public int getRSSI() {
        return RSSI;
    }

    public void setRSSI(int RSSI) {
        this.RSSI = RSSI;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
