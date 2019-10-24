package ru.nemodev.wifi.analyzer.ui.device;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.pwittchen.reactivewifi.ReactiveWifi;
import com.github.pwittchen.reactivewifi.WifiState;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.nemodev.wifi.analyzer.core.app.AndroidApplication;
import ru.nemodev.wifi.analyzer.core.device.DeviceInfo;
import ru.nemodev.wifi.analyzer.core.device.DeviceManager;
import ru.nemodev.wifi.analyzer.core.entity.EntityWrapper;

public class DeviceViewModel extends ViewModel {

    private final MutableLiveData<DeviceInfo> deviceInfo;
    private Disposable wifiStatusDisposable;

    public DeviceViewModel() {
        deviceInfo = new MutableLiveData<>();
    }

    public LiveData<DeviceInfo> getDeviceInfo() {

        if (wifiStatusDisposable == null) {
            wifiStatusDisposable = ReactiveWifi.observeWifiStateChange(AndroidApplication.getInstance())
                    .filter(wifiState -> wifiState == WifiState.ENABLED)
                    .subscribeOn(Schedulers.io())
                    .subscribe(wifiState -> refreshDeviceInfo());
        }

        return deviceInfo;
    }

    public void refreshDeviceInfo() {
        deviceInfo.postValue(DeviceManager.getDeviceInfo());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (wifiStatusDisposable != null) {
            wifiStatusDisposable.dispose();
        }
    }
}