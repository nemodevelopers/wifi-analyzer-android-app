package ru.nemodev.wifi.analyzer.core.report;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nemodev.wifi.analyzer.core.network.RetrofitService;

public class WifiReportService extends RetrofitService {
    private static WifiReportService instance;

    public static WifiReportService getInstance() {
        if (instance == null) {
            instance = new WifiReportService();
        }
        return instance;
    }

    public void getSendReportAPI(WifiAnalyzeReport wifiAnalyzeReport) {
        SendWifiReportAPI reportAPI = retrofit.create(SendWifiReportAPI.class);

        reportAPI.sendReport(wifiAnalyzeReport).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
