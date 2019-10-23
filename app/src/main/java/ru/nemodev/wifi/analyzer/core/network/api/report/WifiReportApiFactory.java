package ru.nemodev.wifi.analyzer.core.network.api.report;

import ru.nemodev.wifi.analyzer.core.network.ServerConfig;
import ru.nemodev.wifi.analyzer.core.network.api.oauth.AuthorizeApiFactory;

public class WifiReportApiFactory extends AuthorizeApiFactory<WifiReportApi> {

    private static final String REPORT_ENDPOINT = ServerConfig.SERVER_URL;

    public WifiReportApiFactory(String accessToken) {
        super(accessToken);
    }

    @Override
    public WifiReportApi createApi() {
        return getRetrofitBuilder(REPORT_ENDPOINT).build()
                .create(WifiReportApi.class);
    }
}
