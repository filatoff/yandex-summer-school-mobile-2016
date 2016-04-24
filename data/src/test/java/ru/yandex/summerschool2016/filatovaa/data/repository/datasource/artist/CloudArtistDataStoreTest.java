package ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist;

import ru.yandex.summerschool2016.filatovaa.data.ApplicationTestCase;
import ru.yandex.summerschool2016.filatovaa.data.cache.ArtistCache;
import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;
import ru.yandex.summerschool2016.filatovaa.data.net.RestApi;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist.CloudArtistDataStore;
import ru.yandex.summerschool2016.filatovaa.data.runtime.ArtistRuntime;
import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class CloudArtistDataStoreTest extends ApplicationTestCase {

    private CloudArtistDataStore cloudArtistDataStore;

    @Mock
    private RestApi mockRestApi;
    @Mock
    private ArtistCache mockArtistCache;
    @Mock
    private ArtistRuntime mockArtistRuntime;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        cloudArtistDataStore = new CloudArtistDataStore(mockRestApi, mockArtistCache, mockArtistRuntime);
    }

    @Test
    public void testGetArtistEntityListFromApi() {
        List<ArtistEntity> artistsList = new ArrayList<>();
        artistsList.add(new ArtistEntity());
        given(mockRestApi.artistEntityList()).willReturn(Observable.just(artistsList));

        cloudArtistDataStore.artistEntityList();

        verify(mockRestApi).artistEntityList();
    }
}
