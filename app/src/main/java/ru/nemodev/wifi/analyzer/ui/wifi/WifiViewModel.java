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
import ru.nemodev.wifi.analyzer.core.wifi.WifiAnalyzeInfo;

public class WifiViewModel extends ViewModel {

    private MutableLiveData<List<WifiAnalyzeInfo>> wifiScanResult;
    private Disposable scanResultDisposable;

    public WifiViewModel() {
        wifiScanResult = new MutableLiveData<>();
    }

    public LiveData<List<WifiAnalyzeInfo>> getWifi(Context context) {
        scanResultDisposable = ReactiveWifi.observeWifiAccessPoints(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(scanResults -> wifiScanResult.setValue(convertScanResults(scanResults)));

        return wifiScanResult;
    }

    private List<WifiAnalyzeInfo> convertScanResults(List<ScanResult> scanResults) {

        List<WifiAnalyzeInfo> wifiAnalyzeInfoList = new ArrayList<>();

        for(ScanResult scanResult: scanResults) {
            WifiAnalyzeInfo wifiAnalyzeInfo = new WifiAnalyzeInfo(scanResult.SSID,
                    scanResult.BSSID,
                    scanResult.level,
                    scanResult.frequency);

            wifiAnalyzeInfoList.add(wifiAnalyzeInfo);
        }

        // TODO как правильно отписаться
        scanResultDisposable.dispose();

        return wifiAnalyzeInfoList;
    }
}