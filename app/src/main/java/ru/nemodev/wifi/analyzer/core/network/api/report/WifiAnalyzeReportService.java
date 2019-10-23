package ru.nemodev.wifi.analyzer.core.network.api.report;

import android.content.Context;
import android.widget.Toast;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.nemodev.wifi.analyzer.core.network.RetrofitApiFactory;
import ru.nemodev.wifi.analyzer.core.network.dto.oauth.OAuthTokenDto;
import ru.nemodev.wifi.analyzer.core.network.dto.wifi.report.WifiAnalyzeReportDto;
import ru.nemodev.wifi.analyzer.core.report.WifiAnalyzeReport;

public class WifiAnalyzeReportService {

    private final Context context;
    private final WifiReportApi reportAPI;
    private static WifiAnalyzeReportService instance;


    private WifiAnalyzeReportService(Context context) {
        this.context = context;
        OAuthTokenDto tokenDto = RetrofitApiFactory.tokenDto;
        reportAPI = new WifiReportApiFactory(tokenDto.getAccess_token()).createApi();
    }

    public static WifiAnalyzeReportService getInstance(Context context) {
        if (instance == null) {
            instance = new WifiAnalyzeReportService(context);
        }
        return instance;
    }

    public void sendReport(WifiAnalyzeReport report) {
        WifiAnalyzeReportDto reportDto = WifiAnalyzeReportDto.fromEntity(report);

        reportAPI.sendReport(reportDto)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WifiAnalyzeReportDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WifiAnalyzeReportDto reportDto) {
                        Toast.makeText(context, "Отчет отправлен", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
