package ru.nemodev.wifi.analyzer.core.network.api.location;

import ru.nemodev.wifi.analyzer.core.network.ServerConfig;
import ru.nemodev.wifi.analyzer.core.network.api.oauth.AuthorizeApiFactory;


public class LocationApiFactory extends AuthorizeApiFactory<LocationApi> {

    private static final String LOCATION_ENDPOINT = ServerConfig.SERVER_URL;

    public LocationApiFactory(String accessToken) {
        super(accessToken);
    }

    @Override
    public LocationApi createApi() {
        return getRetrofitBuilder(LOCATION_ENDPOINT).build()
                .create(LocationApi.class);
    }
}
