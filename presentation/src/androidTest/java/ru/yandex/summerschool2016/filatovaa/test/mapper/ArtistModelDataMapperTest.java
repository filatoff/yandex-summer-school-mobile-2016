package ru.yandex.summerschool2016.filatovaa.test.mapper;

import ru.yandex.summerschool2016.filatovaa.domain.Artist;
import ru.yandex.summerschool2016.filatovaa.presentation.mapper.ArtistModelDataMapper;
import ru.yandex.summerschool2016.filatovaa.presentation.model.ArtistModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class ArtistModelDataMapperTest extends TestCase {

    private static final int FAKE_ID = 123;
    private static final String FAKE_NAME = "Moiseev";

    private ArtistModelDataMapper artistModelDataMapper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        artistModelDataMapper = new ArtistModelDataMapper();
    }

    public void testTransformArtist() {
        Artist artist = createFakeArtist();
        ArtistModel artistModel = artistModelDataMapper.transform(artist);

        assertThat(artistModel, is(instanceOf(ArtistModel.class)));
        assertThat(artistModel.getId(), is(FAKE_ID));
        assertThat(artistModel.getName(), is(FAKE_NAME));
    }

    public void testTransformArtistCollection() {
        Artist mockArtistOne = mock(Artist.class);
        Artist mockArtistTwo = mock(Artist.class);

        List<Artist> artistList = new ArrayList<Artist>();
        artistList.add(mockArtistOne);
        artistList.add(mockArtistTwo);

        Collection<ArtistModel> artistModelList = artistModelDataMapper.transform(artistList);

        assertThat(artistModelList.toArray()[0], is(instanceOf(ArtistModel.class)));
        assertThat(artistModelList.toArray()[1], is(instanceOf(ArtistModel.class)));
        assertThat(artistModelList.size(), is(2));
    }

    private Artist createFakeArtist() {
        Artist artist = new Artist(FAKE_ID);
        artist.setName(FAKE_NAME);

        return artist;
    }
}
