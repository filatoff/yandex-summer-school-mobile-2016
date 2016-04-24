package ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist;

import ru.yandex.summerschool2016.filatovaa.data.ApplicationTestCase;
import ru.yandex.summerschool2016.filatovaa.data.cache.ArtistCache;
import ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist.ArtistDataStore;
import ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist.ArtistDataStoreFactory;
import ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist.CacheArtistDataStore;
import ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist.CloudArtistDataStore;
import ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist.RuntimeArtistDataStore;
import ru.yandex.summerschool2016.filatovaa.data.runtime.ArtistRuntime;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class ArtistDataStoreFactoryTest extends ApplicationTestCase {

    private ArtistDataStoreFactory artistDataStoreFactory;

    @Mock
    private ArtistCache mockArtistCache;

    @Mock
    private ArtistRuntime mockArtistRuntime;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        artistDataStoreFactory = new ArtistDataStoreFactory(RuntimeEnvironment.application,
                mockArtistCache, mockArtistRuntime);
    }

    @Test
    public void testCreateRuntimeDataStore() {
        given(mockArtistRuntime.isCached()).willReturn(true);

        ArtistDataStore artistDataStore = artistDataStoreFactory.create();

        assertThat(artistDataStore, is(notNullValue()));
        assertThat(artistDataStore, is(instanceOf(RuntimeArtistDataStore.class)));

        verify(mockArtistRuntime).isCached();
    }

    @Test
    public void testCreateDiskDataStore() {
        given(mockArtistCache.isCached()).willReturn(true);
        given(mockArtistCache.isExpired()).willReturn(false);

        ArtistDataStore artistDataStore = artistDataStoreFactory.create();

        assertThat(artistDataStore, is(notNullValue()));
        assertThat(artistDataStore, is(instanceOf(CacheArtistDataStore.class)));

        verify(mockArtistCache).isCached();
        verify(mockArtistCache).isExpired();
    }

    @Test
    public void testCreateCloudDataStore() {
        given(mockArtistCache.isExpired()).willReturn(true);
        given(mockArtistCache.isCached()).willReturn(false);

        ArtistDataStore artistDataStore = artistDataStoreFactory.create();

        assertThat(artistDataStore, is(notNullValue()));
        assertThat(artistDataStore, is(instanceOf(CloudArtistDataStore.class)));

        verify(mockArtistCache).isExpired();
    }
}
