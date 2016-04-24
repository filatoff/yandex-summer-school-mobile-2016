package ru.yandex.summerschool2016.filatovaa.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;
import ru.yandex.summerschool2016.filatovaa.data.entity.mapper.ArtistEntityJsonMapper;
import ru.yandex.summerschool2016.filatovaa.data.exception.NetworkConnectionException;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.net.MalformedURLException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {

    private final Context context;
    private final ArtistEntityJsonMapper artistEntityJsonMapper;

    /**
     * Constructor of the class
     *
     * @param context {@link android.content.Context}.
     * @param artistEntityJsonMapper {@link ArtistEntityJsonMapper}.
     */
    public RestApiImpl(Context context, ArtistEntityJsonMapper artistEntityJsonMapper) {
        if (context == null || artistEntityJsonMapper == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.artistEntityJsonMapper = artistEntityJsonMapper;
    }

    @RxLogObservable
    @Override
    public Observable<List<ArtistEntity>> artistEntityList() {
        return Observable.create(new Observable.OnSubscribe<List<ArtistEntity>>() {
            @Override
            public void call(Subscriber<? super List<ArtistEntity>> subscriber) {
                if (RestApiImpl.this.isThereInternetConnection()) {
                    try {
                        String responseArtistEntities = RestApiImpl.this.getArtistEntitiesFromApi();
                        if (responseArtistEntities != null) {
                            subscriber.onNext(artistEntityJsonMapper.transformArtistEntityCollection(responseArtistEntities));
                            subscriber.onCompleted();
                        } else {
                            subscriber.onError(new NetworkConnectionException());
                        }
                    } catch (Exception e) {
                        subscriber.onError(new NetworkConnectionException(e.getCause()));
                    }
                } else {
                    subscriber.onError(new NetworkConnectionException());
                }
            }
        });
    }

    private String getArtistEntitiesFromApi() throws MalformedURLException {
        return ApiConnection.createGET(RestApi.API_URL_GET_ARTIST_LIST).requestSyncCall();
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
