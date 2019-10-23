package ru.nemodev.wifi.analyzer.core.network.api.oauth;

import androidx.annotation.NonNull;

import okhttp3.OkHttpClient;
import ru.nemodev.wifi.analyzer.core.network.RetrofitApiFactory;

public abstract class AuthorizeApiFactory<T> extends RetrofitApiFactory<T> {

    private final String accessToken;

    protected AuthorizeApiFactory(String accessToken) {
        this.accessToken = accessToken;
    }

    @NonNull
    @Override
    protected OkHttpClient.Builder getHttpClientBuilder() {
        return super.getHttpClientBuilder()
                .addInterceptor(new AuthorizeTokenInterceptor(accessToken));
    }

}
