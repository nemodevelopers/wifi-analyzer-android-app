package ru.nemodev.wifi.analyzer.core.report;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SendWifiReportAPI {

    @POST("/report")
    Call<Void> sendReport(@Body WifiAnalyzeReport wifiAnalyzeReport);
}
