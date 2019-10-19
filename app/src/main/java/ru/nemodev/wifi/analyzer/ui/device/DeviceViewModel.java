package ru.nemodev.wifi.analyzer.ui.device;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DeviceViewModel extends ViewModel {

    private MutableLiveData<DeviceInfo> deviceInfo;

    public DeviceViewModel() {
        deviceInfo = new MutableLiveData<>();
        initDeviceInfo();
    }

    public LiveData<DeviceInfo> getDeviceInfo() {
        return deviceInfo;
    }

    private void initDeviceInfo()
    {
        String device = android.os.Build.DEVICE;
        String model = android.os.Build.MODEL;
        String version = android.os.Build.VERSION.SDK;
        String product = android.os.Build.PRODUCT;

        deviceInfo.setValue(new DeviceInfo(device, model, version, product));
    }
}