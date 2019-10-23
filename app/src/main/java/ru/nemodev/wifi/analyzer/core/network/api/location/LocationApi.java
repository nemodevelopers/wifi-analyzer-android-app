package ru.nemodev.wifi.analyzer.core.network.api.location;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import ru.nemodev.wifi.analyzer.core.network.dto.location.LocationDto;


public interface LocationApi {

    @GET("v1/location")
    Observable<List<LocationDto>> getList();

}
