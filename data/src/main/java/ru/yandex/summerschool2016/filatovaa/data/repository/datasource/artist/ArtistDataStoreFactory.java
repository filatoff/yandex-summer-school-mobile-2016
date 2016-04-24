package ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist;

import android.content.Context;

import ru.yandex.summerschool2016.filatovaa.data.cache.ArtistCache;
import ru.yandex.summerschool2016.filatovaa.data.entity.mapper.ArtistEntityJsonMapper;
import ru.yandex.summerschool2016.filatovaa.data.net.RestApiImpl;
import ru.yandex.summerschool2016.filatovaa.data.runtime.ArtistRuntime;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link ArtistDataStore}.
 */
@Singleton
public class ArtistDataStoreFactory {

    private final Context context;
    private final ArtistCache artistCache;
    private final ArtistRuntime artistRuntime;

    @Inject
    public ArtistDataStoreFactory(Context context, ArtistCache artistCache, ArtistRuntime artistRuntime) {
        if (context == null || artistCache == null || artistRuntime == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.artistCache = artistCache;
        this.artistRuntime = artistRuntime;
    }

    /**
     * Create {@link ArtistDataStore}.
     */
    public ArtistDataStore create() {
        ArtistDataStore artistDataStore;

        if (artistRuntime.isCached()) {
            // From memory
            artistDataStore = new RuntimeArtistDataStore(artistRuntime);
        } else if (!artistCache.isExpired() && artistCache.isCached()) {
            // From cache
            artistDataStore = new CacheArtistDataStore(artistCache, artistRuntime);
        } else {
            // From cloud
            artistDataStore = new CloudArtistDataStore(
                    new RestApiImpl(context, new ArtistEntityJsonMapper()),
                    artistCache,
                    artistRuntime
            );
        }

        return artistDataStore;
    }
}
