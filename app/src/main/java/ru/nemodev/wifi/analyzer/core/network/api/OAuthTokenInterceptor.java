package ru.nemodev.wifi.analyzer.core.network.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static ru.nemodev.wifi.analyzer.core.network.api.oauth.OAuthApiFactory.AUTORIZATION_HEADER;

public class OAuthTokenInterceptor implements Interceptor {

    private static final String ACCESS_TOKEN_TYPE = "Bearer";

    private final String accessToken;

    public OAuthTokenInterceptor(String accessToken) {
        this.accessToken = ACCESS_TOKEN_TYPE  + " " + accessToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();

        Request.Builder builder = originalRequest.newBuilder()
                .header(AUTORIZATION_HEADER, accessToken);

        return chain.proceed(builder.build());

    }
}
