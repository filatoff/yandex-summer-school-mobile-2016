package ru.yandex.summerschool2016.filatovaa.data.cache;

import java.util.List;

import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;
import rx.Observable;

/**
 * An interface representing an artist Cache.
 */
public interface ArtistCache {
    /**
     * Gets an {@link rx.Observable} which will emit a list of {@link ArtistEntity}.
     */
    Observable<List<ArtistEntity>> get();

    /**
     * Puts and element into the cache.
     *
     * @param artistEntities Collection to insert in the cache.
     */
    void put(List<ArtistEntity> artistEntities);

    /**
     * Checks if a collection (Artist) exists in the cache.
     *
     * @return true if the collection is cached, otherwise false.
     */
    boolean isCached();

    /**
     * Checks if the cache is expired.
     *
     * @return true, the cache is expired, otherwise false.
     */
    boolean isExpired();

    /**
     * Evict all elements of the cache.
     */
    void evictAll();
}
