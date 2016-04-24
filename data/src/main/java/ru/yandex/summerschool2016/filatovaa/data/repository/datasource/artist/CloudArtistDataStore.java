package ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist;

import ru.yandex.summerschool2016.filatovaa.data.cache.ArtistCache;
import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;
import ru.yandex.summerschool2016.filatovaa.data.net.RestApi;

import java.util.List;

import ru.yandex.summerschool2016.filatovaa.data.runtime.ArtistRuntime;
import rx.Observable;

/**
 * {@link ArtistDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudArtistDataStore implements ArtistDataStore {

    private final RestApi restApi;
    private final ArtistCache artistCache;
    private final ArtistRuntime artistRuntime;

    /**
     * Construct a {@link ArtistDataStore} based on connections to the api (Cloud).
     *
     * @param restApi The {@link RestApi} implementation to use.
     * @param artistCache A {@link ArtistCache} to cache data retrieved from the api.
     * @param artistRuntime A {@link ArtistRuntime} to put data to memory.
     */
    public CloudArtistDataStore(RestApi restApi, ArtistCache artistCache, ArtistRuntime artistRuntime) {
        this.restApi = restApi;
        this.artistCache = artistCache;
        this.artistRuntime = artistRuntime;
    }

    @Override
    public Observable<List<ArtistEntity>> artistEntityList() {
        return this.restApi.artistEntityList().doOnNext(artistEntities -> {
            if (artistEntities != null) {
                CloudArtistDataStore.this.artistRuntime.put(artistEntities);
                CloudArtistDataStore.this.artistCache.put(artistEntities);
            }
        });
    }
}
