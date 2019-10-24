package ru.nemodev.wifi.analyzer.ui.speed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
    private Disposable wifiStatusDisposable;
    private Disposable speedTestDisposable;

    private SpeedTestSocket speedTestSocket;

    public SpeedViewModel() {
        speedTest = new MutableLiveData<>();
    }

    public LiveData<EntityWrapper<SpeedTest>> getSpeedTestData() {

        if (wifiStatusDisposable == null) {
            wifiStatusDisposable = ReactiveWifi.observeWifiStateChange(AndroidApplication.getInstance())
                    .filter(wifiState -> wifiState == WifiState.ENABLED || wifiState == WifiState.ENABLING)
                    .subscribeOn(Schedulers.io())
                    .subscribe(wifiState -> testSpeed());
        }

        return speedTest;
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
                speedTest.postValue(EntityWrapper.of("Ошибка тестирования скорости wifi сети!" +
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
                            speedTest.setValue(EntityWrapper.of("Ошибка тестирования скорости wifi сети!" +
                                    "\nПроверьте подключение к сети wifi!"));
                        });

    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (speedTestDisposable != null) {
            speedTestDisposable.dispose();
        }
        if (wifiStatusDisposable != null) {
            wifiStatusDisposable.dispose();
        }
    }
}