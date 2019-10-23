package ru.nemodev.wifi.analyzer.core.service.location;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.nemodev.wifi.analyzer.core.entity.location.Location;
import ru.nemodev.wifi.analyzer.core.network.RetrofitApiFactory;
import ru.nemodev.wifi.analyzer.core.network.api.location.LocationApiFactory;
import ru.nemodev.wifi.analyzer.core.network.dto.location.LocationDto;

public class LocationService {

    public static final LocationService INSTANCE = new LocationService();

    private List<Location> locationList;

    public LocationService() { }

    public Observable<List<Location>> getList() {
        if (locationList == null) {
            LocationApiFactory locationApiFactory = new LocationApiFactory(RetrofitApiFactory.tokenDto.getAccess_token());
            return locationApiFactory.createApi().getList()
                    .map(this::convertList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        return Observable.just(locationList);
    }

    private List<Location> convertList(List<LocationDto> locationDtoList) {
        List<Location> locations = new ArrayList<>();

        for (LocationDto locationDto : locationDtoList) {
            locations.add(convert(locationDto));
        }

        this.locationList = locations;

        return locations;
    }

    private Location convert(LocationDto locationDto) {
        Location location = new Location();
        location.setId(locationDto.getId());
        location.setName(locationDto.getName());

        return location;
    }

}
