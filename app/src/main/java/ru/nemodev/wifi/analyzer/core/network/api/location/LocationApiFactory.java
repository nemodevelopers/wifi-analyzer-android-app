package ru.nemodev.wifi.analyzer.core.network.api.location;

import androidx.annotation.NonNull;

import okhttp3.OkHttpClient;
import ru.nemodev.wifi.analyzer.core.network.RetrofitApiFactory;
import ru.nemodev.wifi.analyzer.core.network.ServerConfig;
import ru.nemodev.wifi.analyzer.core.network.api.OAuthTokenInterceptor;


public class LocationApiFactory extends RetrofitApiFactory<LocationApi> {

    private static final String LOCATION_ENDPOINT = ServerConfig.SERVER_URL + "v1/location";

    private final String accessToken;

    public LocationApiFactory(String accessToken) {
        this.accessToken = accessToken;
    }

    @NonNull
    @Override
    protected OkHttpClient.Builder getHttpClientBuilder() {
        return super.getHttpClientBuilder()
                .addInterceptor(new OAuthTokenInterceptor(accessToken));
    }

    @Override
    public LocationApi createApi() {
        return getRetrofitBuilder(LOCATION_ENDPOINT).build()
                .create(LocationApi.class);
    }
}
