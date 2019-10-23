package ru.nemodev.wifi.analyzer.ui.wifi;

import android.content.Context;
import android.net.wifi.ScanResult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.pwittchen.reactivewifi.ReactiveWifi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WifiViewModel extends ViewModel {

    private MutableLiveData<List<WifiData>> wifiScanResult;
    private Disposable scanResultDisposable;

    public WifiViewModel() {
        wifiScanResult = new MutableLiveData<>();
    }

    public LiveData<List<WifiData>> getWifi(Context context) {
        scanResultDisposable = ReactiveWifi.observeWifiAccessPoints(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(scanResults -> wifiScanResult.setValue(convertScanResults(scanResults)));

        return wifiScanResult;
    }

    private List<WifiData> convertScanResults(List<ScanResult> scanResults) {

        List<WifiData> wifiDataList = new ArrayList<>();

        for(ScanResult scanResult: scanResults) {
            WifiData wifiData = new WifiData(scanResult.SSID,
                    scanResult.BSSID,
                    scanResult.level,
                    scanResult.frequency);

            wifiDataList.add(wifiData);
        }

        // TODO как правильно отписаться
        scanResultDisposable.dispose();

        return wifiDataList;
    }
}