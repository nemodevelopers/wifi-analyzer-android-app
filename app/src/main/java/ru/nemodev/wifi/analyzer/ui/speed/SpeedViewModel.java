package ru.nemodev.wifi.analyzer.ui.speed;

import android.net.wifi.WifiInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.pwittchen.reactivewifi.ReactiveWifi;

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

    private SpeedTestSocket speedTestSocket;

    public SpeedViewModel() {
        speedTest = new MutableLiveData<>();
        activeWifi = new MutableLiveData<>();
    }

    public LiveData<EntityWrapper<SpeedTest>> getSpeedTestData() {
        return speedTest;
    }

    public LiveData<EntityWrapper<WifiInfo>> getActiveWifi() {

        if (activeWifiDisposable == null) {
            activeWifiDisposable = ReactiveWifi.observeWifiAccessPointChanges(AndroidApplication.getInstance().getApplicationContext())
                    .subscribeOn(Schedulers.io())
                    .subscribe(wifiInfo -> activeWifi.postValue(EntityWrapper.of(wifiInfo)));
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
                speedTest.postValue(EntityWrapper.of(new SpeedTest(report)));
            }

            @Override
            public void onError(final SpeedTestError speedTestError, final String errorMessage) {
                speedTest.postValue(EntityWrapper.of("Ошибка теста скорости wifi сети!" +
                        "\nПроверьте подключение к сети wifi!"));
            }

            @Override
            public void onProgress(final float percent, final SpeedTestReport downloadReport) {
                speedTest.postValue(EntityWrapper.of(new SpeedTest(downloadReport, true)));
            }
        });

        speedTestDisposable = Observable.fromCallable(() -> {
            speedTestSocket.startDownload(SPEED_TEST_SERVER_URI_DL);
            return true; })
                .subscribeOn(Schedulers.io())
                .subscribe(isStart -> {},
                        exception -> {
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
    }
}