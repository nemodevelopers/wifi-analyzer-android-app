package ru.nemodev.wifi.analyzer.core.network.dto.wifi.report;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ru.nemodev.wifi.analyzer.core.network.dto.BaseEntityDto;
import ru.nemodev.wifi.analyzer.core.network.dto.device.DeviceInfoDto;
import ru.nemodev.wifi.analyzer.core.network.dto.location.LocationDto;
import ru.nemodev.wifi.analyzer.core.network.dto.user.UserOwnerDto;
import ru.nemodev.wifi.analyzer.core.network.dto.wifi.info.WifiAnalyzeInfoDto;
import ru.nemodev.wifi.analyzer.core.report.WifiAnalyzeReport;
import ru.nemodev.wifi.analyzer.core.wifi.WifiAnalyzeInfo;

public class WifiAnalyzeReportDto extends BaseEntityDto {

    @SerializedName("creationDate")
    @Expose
    private String creationDate;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("ownerUser")
    @Expose
    private UserOwnerDto ownerUser;

    @SerializedName("location")
    @Expose
    private LocationDto location;

    @SerializedName("deviceInfo")
    @Expose
    private DeviceInfoDto deviceInfo;

    @SerializedName("wifiAnalyzeInfos")
    @Expose
    private List<WifiAnalyzeInfoDto> wifiAnalyzeInfos;

    public WifiAnalyzeReportDto() {
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserOwnerDto getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(UserOwnerDto ownerUser) {
        this.ownerUser = ownerUser;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public DeviceInfoDto getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoDto deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public List<WifiAnalyzeInfoDto> getWifiAnalyzeInfos() {
        return wifiAnalyzeInfos;
    }

    public void setWifiAnalyzeInfos(List<WifiAnalyzeInfoDto> wifiAnalyzeInfos) {
        this.wifiAnalyzeInfos = wifiAnalyzeInfos;
    }

    public static WifiAnalyzeReportDto fromEntity(WifiAnalyzeReport wifiAnalyzeReport) {
        WifiAnalyzeReportDto wifiAnalyzeReportDto = new WifiAnalyzeReportDto();

        wifiAnalyzeReportDto.setComment(wifiAnalyzeReport.getComment());
        wifiAnalyzeReportDto.setOwnerUser(null);
        wifiAnalyzeReportDto.setLocation(LocationDto.fromEntity(wifiAnalyzeReport.getLocation()));
        wifiAnalyzeReportDto.setDeviceInfo(DeviceInfoDto.fromEntity(wifiAnalyzeReport.getDeviceInfo()));

        List<WifiAnalyzeInfoDto> wifiAnalyzeInfoDtoList = new ArrayList<>();

        for(WifiAnalyzeInfo wifiAnalyzeInfo: wifiAnalyzeReport.getWifiAnalyzeInfoList()) {
            wifiAnalyzeInfoDtoList.add(WifiAnalyzeInfoDto.fromEntity(wifiAnalyzeInfo));
        }

        wifiAnalyzeReportDto.setWifiAnalyzeInfos(wifiAnalyzeInfoDtoList);

        return wifiAnalyzeReportDto;
    }
}
