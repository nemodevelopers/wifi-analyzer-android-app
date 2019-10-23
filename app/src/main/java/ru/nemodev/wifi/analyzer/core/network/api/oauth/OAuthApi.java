package ru.nemodev.wifi.analyzer.core.network.api.oauth;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import ru.nemodev.wifi.analyzer.core.network.dto.oauth.OAuthTokenDto;

public interface OAuthApi {

    @POST("token")
    Observable<OAuthTokenDto> getToken(@QueryMap Map<String, String> queryMap);

}
