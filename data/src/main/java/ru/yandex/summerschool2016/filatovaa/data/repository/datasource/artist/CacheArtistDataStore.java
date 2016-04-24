package ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist;

import ru.yandex.summerschool2016.filatovaa.data.cache.ArtistCache;
import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;

import java.util.List;

import ru.yandex.summerschool2016.filatovaa.data.runtime.ArtistRuntime;
import rx.Observable;

/**
 * {@link ArtistDataStore} implementation based on file system data store.
 */
public class CacheArtistDataStore implements ArtistDataStore {

    private final ArtistCache artistCache;
    private final ArtistRuntime artistRuntime;

    /**
     * Construct a {@link ArtistDataStore} based file system data store.
     *
     * @param artistCache A {@link ArtistCache} to cache data retrieved from the api.
     * @param artistRuntime A {@link ArtistRuntime} to set memory data retreived from cache.
     */
    public CacheArtistDataStore(ArtistCache artistCache, ArtistRuntime artistRuntime) {
        this.artistCache = artistCache;
        this.artistRuntime = artistRuntime;
    }

    @Override
    public Observable<List<ArtistEntity>> artistEntityList() {
        return this.artistCache.get().doOnNext(artistEntities -> {
            if (artistEntities != null) {
                CacheArtistDataStore.this.artistRuntime.put(artistEntities);
            }
        });
    }
}
