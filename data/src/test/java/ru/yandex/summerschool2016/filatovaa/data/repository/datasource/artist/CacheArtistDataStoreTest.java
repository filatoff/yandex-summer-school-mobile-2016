package ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist;

import ru.yandex.summerschool2016.filatovaa.data.ApplicationTestCase;
import ru.yandex.summerschool2016.filatovaa.data.cache.ArtistCache;
import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;
import ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist.CacheArtistDataStore;
import ru.yandex.summerschool2016.filatovaa.data.runtime.ArtistRuntime;
import rx.Observable;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class CacheArtistDataStoreTest extends ApplicationTestCase {

    private CacheArtistDataStore cacheArtistDataStore;

    @Mock
    private ArtistCache mockArtistCache;

    @Mock
    private ArtistRuntime mockArtistRuntime;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        cacheArtistDataStore = new CacheArtistDataStore(mockArtistCache, mockArtistRuntime);
    }

    @Test
    public void testGetArtistEntityListFromCache() {
        List<ArtistEntity> artistsList = new ArrayList<>();
        artistsList.add(new ArtistEntity());
        given(mockArtistCache.get()).willReturn(Observable.just(artistsList));

        cacheArtistDataStore.artistEntityList();
        verify(mockArtistCache).get();
    }
}
