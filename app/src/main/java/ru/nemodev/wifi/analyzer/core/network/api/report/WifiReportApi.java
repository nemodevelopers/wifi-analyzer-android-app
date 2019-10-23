package ru.nemodev.wifi.analyzer.core.network.api.report;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.nemodev.wifi.analyzer.core.network.dto.wifi.report.WifiAnalyzeReportDto;

public interface WifiReportApi {

    @POST("v1/report")
    Observable<WifiAnalyzeReportDto> sendReport(@Body WifiAnalyzeReportDto reportDto);
}
