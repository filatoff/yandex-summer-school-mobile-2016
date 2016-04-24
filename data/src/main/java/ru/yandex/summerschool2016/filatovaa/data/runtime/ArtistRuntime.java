package ru.yandex.summerschool2016.filatovaa.data.runtime;

import java.util.List;

import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;
import rx.Observable;

/**
 * An interface representing an artist runtime memory.
 */
public interface ArtistRuntime {
    /**
     * Gets an {@link rx.Observable} which will emit a list of {@link ArtistEntity}.
     */
    Observable<List<ArtistEntity>> get();

    /**
     * Puts and element into the memory.
     *
     * @param artistEntities Collection to insert in the memory.
     */
    void put(List<ArtistEntity> artistEntities);

    /**
     * Checks if a collection (Artist) exists in the memory.
     *
     * @return true if the collection is cached, otherwise false.
     */
    boolean isCached();
}
