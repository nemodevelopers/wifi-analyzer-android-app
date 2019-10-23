package ru.nemodev.wifi.analyzer.core.network;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitService {

    protected Retrofit retrofit;

    protected RetrofitService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerConfig.SERVER_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
