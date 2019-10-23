package ru.nemodev.wifi.analyzer.core.network.api.oauth;

import androidx.annotation.NonNull;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import ru.nemodev.wifi.analyzer.core.network.RetrofitApiFactory;
import ru.nemodev.wifi.analyzer.core.network.ServerConfig;


public class OAuthApiFactory extends RetrofitApiFactory<OAuthApi> {

    public static final String AUTORIZATION_HEADER = "Authorization";

    private static final String OAUTH_ENDPOINT = ServerConfig.SERVER_URL + "oauth/";

    private final String clientId;
    private final String clientSecret;

    public OAuthApiFactory(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @NonNull
    @Override
    protected OkHttpClient.Builder getHttpClientBuilder() {

        return super.getHttpClientBuilder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();

                    Request.Builder builder = originalRequest.newBuilder().
                            header(AUTORIZATION_HEADER, Credentials.basic(clientId, clientSecret));

                    return chain.proceed(builder.build());
                });
    }

    @Override
    public OAuthApi createApi() {
        return getRetrofitBuilder(OAUTH_ENDPOINT)
                .build().create(OAuthApi.class);
    }
}
