package ru.nemodev.wifi.analyzer.core.network.dto.wifi.info;


import ru.nemodev.wifi.analyzer.core.network.dto.BaseEntityDto;
import ru.nemodev.wifi.analyzer.core.wifi.WifiAnalyzeInfo;

public class WifiAnalyzeInfoDto extends BaseEntityDto {

    private String creationDate;
    private String ssid;
    private String bssid;
    private Integer rssi;
    private String signalLevel;
    private Integer frequency;
    private String speed;

    public WifiAnalyzeInfoDto() {
    }

    public String  getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public Integer getRssi() {
        return rssi;
    }

    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }

    public String getSignalLevel() {
        return signalLevel;
    }

    public void setSignalLevel(String signalLevel) {
        this.signalLevel = signalLevel;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public static WifiAnalyzeInfoDto fromEntity(WifiAnalyzeInfo wifiAnalyzeInfo) {
        WifiAnalyzeInfoDto wifiAnalyzeInfoDto = new WifiAnalyzeInfoDto();
        wifiAnalyzeInfoDto.setCreationDate(null);
        wifiAnalyzeInfoDto.setSsid(wifiAnalyzeInfo.getSSID());
        wifiAnalyzeInfoDto.setBssid(wifiAnalyzeInfo.getBSSID());
        wifiAnalyzeInfoDto.setRssi(wifiAnalyzeInfo.getRSSI());
        wifiAnalyzeInfoDto.setSignalLevel(wifiAnalyzeInfo.getRssiLevel().name());
        wifiAnalyzeInfoDto.setFrequency(wifiAnalyzeInfo.getFrequency());
        wifiAnalyzeInfoDto.setSpeed(wifiAnalyzeInfo.getSpeed());

        return wifiAnalyzeInfoDto;
    }
}
