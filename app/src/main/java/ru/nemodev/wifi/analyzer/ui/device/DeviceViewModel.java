package ru.nemodev.wifi.analyzer.ui.device;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.nemodev.wifi.analyzer.core.device.DeviceInfo;
import ru.nemodev.wifi.analyzer.core.device.DeviceManager;

public class DeviceViewModel extends ViewModel {

    private MutableLiveData<DeviceInfo> deviceInfo;

    public DeviceViewModel() {
        deviceInfo = new MutableLiveData<>();
    }

    public LiveData<DeviceInfo> getDeviceInfo(Context context) {
        DeviceInfo device = DeviceManager.getDeviceInfo(context);

        deviceInfo.setValue(device);
        return deviceInfo;
    }
}