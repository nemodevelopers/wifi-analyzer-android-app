package ru.nemodev.wifi.analyzer.ui.device;

public class DeviceInfo {
    private String device;
    private String model;
    private String version;
    private String product;

    public DeviceInfo(String device, String model, String version, String product) {
        this.device = device;
        this.model = model;
        this.version = version;
        this.product = product;
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
}
