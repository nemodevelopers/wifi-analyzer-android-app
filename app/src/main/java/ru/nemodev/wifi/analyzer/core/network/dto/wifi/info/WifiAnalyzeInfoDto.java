package ru.nemodev.wifi.analyzer.core.network.dto.wifi.info;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

import ru.nemodev.wifi.analyzer.core.network.dto.BaseEntityDto;
import ru.nemodev.wifi.analyzer.core.wifi.WifiAnalyzeInfo;

public class WifiAnalyzeInfoDto extends BaseEntityDto {

    @SerializedName("creationDate")
    @Expose
    private LocalDateTime creationDate;

    @SerializedName("ssid")
    @Expose
    private String ssid;

    @SerializedName("bssid")
    @Expose
    private String bssid;

    @SerializedName("rssi")
    @Expose
    private Integer rssi;

    @SerializedName("signalLevel")
    @Expose
    private String signalLevel;

    @SerializedName("frequency")
    @Expose
    private Integer frequency;

    public WifiAnalyzeInfoDto() {
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
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

    public static WifiAnalyzeInfoDto fromEntity(WifiAnalyzeInfo wifiAnalyzeInfo) {
        WifiAnalyzeInfoDto wifiAnalyzeInfoDto = new WifiAnalyzeInfoDto();
        wifiAnalyzeInfoDto.setCreationDate(null);
        wifiAnalyzeInfoDto.setSsid(wifiAnalyzeInfo.getSSID());
        wifiAnalyzeInfoDto.setBssid(wifiAnalyzeInfo.getBSSID());
        wifiAnalyzeInfoDto.setRssi(wifiAnalyzeInfo.getRSSI());
        wifiAnalyzeInfoDto.setSignalLevel(wifiAnalyzeInfo.getRssiLevel().name());
        wifiAnalyzeInfoDto.setFrequency(wifiAnalyzeInfo.getFrequency());

        return wifiAnalyzeInfoDto;
    }
}
