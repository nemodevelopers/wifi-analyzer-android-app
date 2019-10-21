package ru.nemodev.wifi.analyzer.ui.device;

public class DeviceInfo {
    private String device;
    private String model;
    private String version;
    private String product;
    private String mac;
    private String ip;

    public DeviceInfo(String device, String model, String version, String product, String mac, String ip) {
        this.device = device;
        this.model = model;
        this.version = version;
        this.product = product;
        this.mac = mac;
        this.ip = ip;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
