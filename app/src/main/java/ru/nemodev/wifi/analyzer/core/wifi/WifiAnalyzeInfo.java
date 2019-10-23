package ru.nemodev.wifi.analyzer.core.wifi;

/**
 * Данные о Wi-Fi соединении
 */
public class WifiAnalyzeInfo {

    /**
     * Имя сети
     * Service set identifier
     */
    private String SSID;

    /**
     * Mac-адрес сетевого адаптера
     * Basic serviсe set id
     */
    private String BSSID;

    /**
     * Показатель уровня принимаемого сигнала
     * Received signal strength indicator
     */
    private int RSSI;

    /**
     * Частота канала
     */
    private int frequency;

    private RssiLevel rssiLevel;

    public WifiAnalyzeInfo() {
    }

    public WifiAnalyzeInfo(String SSID, String BSSID, int RSSI, int frequency) {
        this.SSID = SSID;
        this.BSSID = BSSID;
        this.RSSI = RSSI;
        this.frequency = frequency;

        this.rssiLevel = RssiLevel.defineRssiLevel(RSSI);
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

    public RssiLevel getRssiLevel() {
        return rssiLevel;
    }

    public void setRssiLevel(RssiLevel rssiLevel) {
        this.rssiLevel = rssiLevel;
    }
}
