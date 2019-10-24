package ru.nemodev.wifi.analyzer.ui.wifi;

import android.net.wifi.ScanResult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.crashlytics.android.Crashlytics;
import com.github.pwittchen.reactivewifi.ReactiveWifi;
import com.github.pwittchen.reactivewifi.WifiState;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.nemodev.wifi.analyzer.core.app.AndroidApplication;
import ru.nemodev.wifi.analyzer.core.wifi.WifiAnalyzeInfo;

public class WifiViewModel extends ViewModel {

    private final MutableLiveData<List<WifiAnalyzeInfo>> wifiScanResult;
    private Disposable scanResultDisposable;

    private final MutableLiveData<WifiState> wifiStatus;
    private Disposable wifiStatusDisposable;

    public WifiViewModel() {
        this.wifiScanResult = new MutableLiveData<>();
        this.wifiStatus = new MutableLiveData<>();
    }

    public LiveData<WifiState> getWifiStatus() {

        if (wifiStatusDisposable == null) {
            wifiStatusDisposable = ReactiveWifi.observeWifiStateChange(AndroidApplication.getInstance())
                    .filter(wifiState -> wifiState == WifiState.ENABLED
                            || wifiState == WifiState.DISABLED || wifiState == WifiState.UNKNOWN)
                    .subscribeOn(Schedulers.io())
                    .subscribe(wifiStatus::postValue,
                            throwable -> {
                                Crashlytics.logException(throwable);
                                wifiStatus.postValue(WifiState.UNKNOWN);
                            });
        }

        return wifiStatus;
    }

    public LiveData<List<WifiAnalyzeInfo>> getScanResult() {
        return wifiScanResult;
    }

    public void scanWifi() {
        if (scanResultDisposable != null) {
            scanResultDisposable.dispose();
        }

        scanResultDisposable = ReactiveWifi.observeWifiAccessPoints(AndroidApplication.getInstance())
                .subscribeOn(Schedulers.io())
                .subscribe(scanResults -> wifiScanResult.postValue(convertScanResults(scanResults)));
    }

    private List<WifiAnalyzeInfo> convertScanResults(List<ScanResult> scanResults) {

        List<WifiAnalyzeInfo> wifiAnalyzeInfoList = new ArrayList<>();

        for (ScanResult scanResult : scanResults) {
            WifiAnalyzeInfo wifiAnalyzeInfo = new WifiAnalyzeInfo(scanResult.SSID,
                    scanResult.BSSID,
                    scanResult.level,
                    scanResult.frequency);

            wifiAnalyzeInfoList.add(wifiAnalyzeInfo);
        }

        scanResultDisposable.dispose();

        return wifiAnalyzeInfoList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (scanResultDisposable != null) {
            scanResultDisposable.dispose();
        }
        if (wifiStatusDisposable != null) {
            wifiStatusDisposable.dispose();
        }
    }
}