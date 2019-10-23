package ru.nemodev.wifi.analyzer.core.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    protected Retrofit retrofit;

    protected RetrofitService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ServerConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
