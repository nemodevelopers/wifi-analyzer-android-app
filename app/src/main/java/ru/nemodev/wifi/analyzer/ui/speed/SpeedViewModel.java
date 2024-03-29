package ru.nemodev.wifi.analyzer.ui.speed;

import android.net.wifi.WifiInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.crashlytics.android.Crashlytics;
import com.github.pwittchen.reactivewifi.ReactiveWifi;
import com.github.pwittchen.reactivewifi.WifiState;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.nemodev.wifi.analyzer.core.app.AndroidApplication;
import ru.nemodev.wifi.analyzer.core.entity.EntityWrapper;
import ru.nemodev.wifi.analyzer.core.entity.speed.SpeedTest;


public class SpeedViewModel extends ViewModel {

    private final static String SPEED_TEST_SERVER_URI_DL = "http://ipv4.ikoula.testdebit.info/10M.iso";
    private final static int SOCKET_TIMEOUT = 5000;

    private final MutableLiveData<EntityWrapper<SpeedTest>> speedTest;
    private Disposable speedTestDisposable;

    private final MutableLiveData<EntityWrapper<WifiInfo>> activeWifi;
    private Disposable activeWifiDisposable;

    private final MutableLiveData<WifiState> wifiStatus;
    private Disposable wifiStatusDisposable;

    private SpeedTestSocket speedTestSocket;

    public SpeedViewModel() {
        speedTest = new MutableLiveData<>();
        activeWifi = new MutableLiveData<>();
        wifiStatus = new MutableLiveData<>();
    }

    public LiveData<EntityWrapper<SpeedTest>> getSpeedTestData() {
        return speedTest;
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


    public LiveData<EntityWrapper<WifiInfo>> getActiveWifi() {

        if (activeWifiDisposable == null) {
            activeWifiDisposable = ReactiveWifi.observeWifiAccessPointChanges(AndroidApplication.getInstance().getApplicationContext())
                    .subscribeOn(Schedulers.io())
                    .subscribe(wifiInfo -> activeWifi.postValue(EntityWrapper.of(wifiInfo)),
                            throwable -> {
                                Crashlytics.logException(throwable);
                                activeWifi.postValue(EntityWrapper.of("Ошибка получения информации wi-fi, попробуйте переподключить wifi!"));
                            });
        }

        return activeWifi;
    }

    public void testSpeed() {

        if (speedTestSocket != null) {
            speedTestSocket.forceStopTask();
        }

        if (speedTestDisposable != null) {
            speedTestDisposable.dispose();
        }

        speedTestSocket = new SpeedTestSocket();
        speedTestSocket.setSocketTimeout(SOCKET_TIMEOUT);
        speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {

            @Override
            public void onCompletion(final SpeedTestReport report) {
                speedTest.postValue(EntityWrapper.of(new SpeedTest(activeWifi.getValue().getEntity(), report)));
            }

            @Override
            public void onError(final SpeedTestError speedTestError, final String errorMessage) {
                speedTest.postValue(EntityWrapper.of("Ошибка теста скорости wifi сети!" +
                        "\nПроверьте подключение к сети wifi!"));
            }

            @Override
            public void onProgress(final float percent, final SpeedTestReport downloadReport) {
                speedTest.postValue(EntityWrapper.of(new SpeedTest(null, downloadReport, true)));
            }
        });

        speedTestDisposable = Observable.fromCallable(() -> {
            speedTestSocket.startDownload(SPEED_TEST_SERVER_URI_DL);
            return true; })
                .subscribeOn(Schedulers.io())
                .subscribe(isStart -> {},
                        throwable -> {
                            Crashlytics.logException(throwable);
                            speedTest.setValue(EntityWrapper.of("Ошибка теста скорости wifi сети!" +
                                    "\nПроверьте подключение к сети wifi!"));
                        });

    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (speedTestDisposable != null) {
            speedTestDisposable.dispose();
        }
        if (activeWifiDisposable != null) {
            activeWifiDisposable.dispose();
        }
        if (wifiStatusDisposable != null) {
            wifiStatusDisposable.dispose();
        }
    }
}