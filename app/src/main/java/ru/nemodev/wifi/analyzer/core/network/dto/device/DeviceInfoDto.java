package ru.nemodev.wifi.analyzer.core.network.dto.device;


import ru.nemodev.wifi.analyzer.core.device.DeviceInfo;
import ru.nemodev.wifi.analyzer.core.network.dto.BaseEntityDto;

public class DeviceInfoDto extends BaseEntityDto {

    private String device;
    private String model;
    private String versionOS;
    private String macAddress;
    private String ipAddress;

    public DeviceInfoDto() {
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVersionOS() {
        return versionOS;
    }

    public void setVersionOS(String versionOS) {
        this.versionOS = versionOS;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public static DeviceInfoDto fromEntity(DeviceInfo deviceInfo) {
        DeviceInfoDto deviceInfoDto = new DeviceInfoDto();
        deviceInfoDto.setDevice(deviceInfo.getDevice());
        deviceInfoDto.setModel(deviceInfo.getModel());
        deviceInfoDto.setVersionOS(deviceInfo.getRelease());
        deviceInfoDto.setMacAddress(deviceInfo.getMac());
        deviceInfoDto.setIpAddress(deviceInfo.getIp());

        return deviceInfoDto;
    }
}
