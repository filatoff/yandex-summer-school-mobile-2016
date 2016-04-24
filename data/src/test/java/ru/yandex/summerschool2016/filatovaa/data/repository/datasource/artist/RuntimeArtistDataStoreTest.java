package ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist;

import ru.yandex.summerschool2016.filatovaa.data.ApplicationTestCase;
import ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist.RuntimeArtistDataStore;
import ru.yandex.summerschool2016.filatovaa.data.runtime.ArtistRuntime;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class RuntimeArtistDataStoreTest extends ApplicationTestCase {

    private RuntimeArtistDataStore runtimeArtistDataStore;

    @Mock
    private ArtistRuntime mockArtistRuntime;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        runtimeArtistDataStore = new RuntimeArtistDataStore(mockArtistRuntime);
    }

    @Test
    public void testGetArtistEntityListFromRuntime() {
        runtimeArtistDataStore.artistEntityList();
        verify(mockArtistRuntime).get();
    }
}
