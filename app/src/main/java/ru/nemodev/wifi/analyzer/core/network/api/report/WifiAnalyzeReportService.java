package ru.nemodev.wifi.analyzer.core.network.api.report;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.nemodev.wifi.analyzer.core.auth.AuthContext;
import ru.nemodev.wifi.analyzer.core.network.dto.oauth.OAuthTokenDto;
import ru.nemodev.wifi.analyzer.core.network.dto.wifi.report.WifiAnalyzeReportDto;
import ru.nemodev.wifi.analyzer.core.report.WifiAnalyzeReport;

public class WifiAnalyzeReportService {

    private final WifiReportApi reportAPI;
    private static WifiAnalyzeReportService instance;


    private WifiAnalyzeReportService() {
        OAuthTokenDto tokenDto = AuthContext.tokenDto;
        reportAPI = new WifiReportApiFactory(tokenDto.getAccess_token()).createApi();
    }

    public static WifiAnalyzeReportService getInstance() {
        if (instance == null) {
            instance = new WifiAnalyzeReportService();
        }
        return instance;
    }

    public Observable<WifiAnalyzeReportDto> sendReport(WifiAnalyzeReport report) {
        WifiAnalyzeReportDto reportDto = WifiAnalyzeReportDto.fromEntity(report);

        return reportAPI.sendReport(reportDto)
                .subscribeOn(Schedulers.io());
    }
}
