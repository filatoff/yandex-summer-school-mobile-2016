package ru.yandex.summerschool2016.filatovaa.data.repository;

import ru.yandex.summerschool2016.filatovaa.data.ApplicationTestCase;
import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;
import ru.yandex.summerschool2016.filatovaa.data.entity.mapper.ArtistEntityDataMapper;
import ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist.ArtistDataStore;
import ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist.ArtistDataStoreFactory;
import ru.yandex.summerschool2016.filatovaa.domain.Artist;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class ArtistDataRepositoryTest extends ApplicationTestCase {

    private ArtistDataRepository artistDataRepository;

    @Mock
    private ArtistDataStoreFactory mockArtistDataStoreFactory;
    @Mock
    private ArtistEntityDataMapper mockArtistEntityDataMapper;
    @Mock
    private ArtistDataStore mockArtistDataStore;
    @Mock
    private ArtistEntity mockArtistEntity;
    @Mock
    private Artist mockArtist;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        artistDataRepository = new ArtistDataRepository(mockArtistDataStoreFactory, mockArtistEntityDataMapper);

        given(mockArtistDataStoreFactory.create()).willReturn(mockArtistDataStore);
    }

    @Test
    public void testGetArtistsHappyCase() {
        List<ArtistEntity> artistsList = new ArrayList<>();
        artistsList.add(new ArtistEntity());
        given(mockArtistDataStore.artistEntityList()).willReturn(Observable.just(artistsList));

        artistDataRepository.artists(0, null);

        verify(mockArtistDataStoreFactory).create();
        verify(mockArtistDataStore).artistEntityList();
    }
}
