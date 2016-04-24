package ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist;

import ru.yandex.summerschool2016.filatovaa.data.runtime.ArtistRuntime;
import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;

import java.util.List;

import rx.Observable;

/**
 * {@link ArtistDataStore} implementation based on runtime memory data store.
 */
public class RuntimeArtistDataStore implements ArtistDataStore {

    private final ArtistRuntime artistRuntime;

    /**
     * Construct a {@link ArtistDataStore} based in runtime memory data store.
     *
     * @param artistRuntime A {@link ArtistRuntime} to set memory data retrieved from the api.
     */
    public RuntimeArtistDataStore(ArtistRuntime artistRuntime) {
        this.artistRuntime = artistRuntime;
    }

    @Override
    public Observable<List<ArtistEntity>> artistEntityList() {
        return this.artistRuntime.get();
    }
}
