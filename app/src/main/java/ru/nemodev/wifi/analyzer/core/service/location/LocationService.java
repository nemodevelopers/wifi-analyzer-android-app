package ru.nemodev.wifi.analyzer.core.service.location;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.nemodev.wifi.analyzer.core.auth.AuthContext;
import ru.nemodev.wifi.analyzer.core.entity.location.Location;
import ru.nemodev.wifi.analyzer.core.network.api.location.LocationApiFactory;
import ru.nemodev.wifi.analyzer.core.network.dto.location.LocationDto;


public class LocationService {

    public static final LocationService INSTANCE = new LocationService();

    private List<Location> locationList;

    public LocationService() { }

    public Observable<List<Location>> getList() {
        if (locationList == null) {
            LocationApiFactory locationApiFactory = new LocationApiFactory(AuthContext.tokenDto.getAccess_token());

            Observable<List<Location>> observable = locationApiFactory.createApi().getList()
                    .map(LocationDto::toEntityList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            observable.subscribe(locations -> locationList = locations);

            return observable;
        }

        return Observable.just(locationList);
    }

}
