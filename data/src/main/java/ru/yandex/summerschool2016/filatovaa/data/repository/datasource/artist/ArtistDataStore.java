package ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist;

import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;

import java.util.List;

import rx.Observable;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface ArtistDataStore {
    /**
     * Get an {@link rx.Observable} which will emit a List of {@link ArtistEntity}.
     */
    Observable<List<ArtistEntity>> artistEntityList();
}
