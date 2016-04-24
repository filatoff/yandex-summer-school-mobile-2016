package ru.yandex.summerschool2016.filatovaa.data.entity.mapper;

import ru.yandex.summerschool2016.filatovaa.data.ApplicationTestCase;
import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;
import ru.yandex.summerschool2016.filatovaa.domain.Artist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class ArtistEntityDataMapperTest extends ApplicationTestCase {

    private static final int FAKE_ID = 123;
    private static final String FAKE_NAME = "Fyodor Shalyapin";

    private ArtistEntityDataMapper artistEntityDataMapper;

    @Before
    public void setUp() throws Exception {
        artistEntityDataMapper = new ArtistEntityDataMapper();
    }

    @Test
    public void testTransformArtistEntity() {
        ArtistEntity artistEntity = createFakeArtistEntity();
        Artist artist = artistEntityDataMapper.transform(artistEntity);

        assertThat(artist, is(instanceOf(Artist.class)));
        assertThat(artist.getId(), is(FAKE_ID));
        assertThat(artist.getName(), is(FAKE_NAME));
    }

    @Test
    public void testTransformArtistEntityCollection() {
        ArtistEntity mockArtistEntityOne = mock(ArtistEntity.class);
        ArtistEntity mockArtistEntityTwo = mock(ArtistEntity.class);

        List<ArtistEntity> artistEntityList = new ArrayList<>();
        artistEntityList.add(mockArtistEntityOne);
        artistEntityList.add(mockArtistEntityTwo);

        Collection<Artist> artistCollection = artistEntityDataMapper.transform(artistEntityList);

        assertThat(artistCollection.toArray()[0], is(instanceOf(Artist.class)));
        assertThat(artistCollection.toArray()[1], is(instanceOf(Artist.class)));
        assertThat(artistCollection.size(), is(2));
    }

    private ArtistEntity createFakeArtistEntity() {
        ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setId(FAKE_ID);
        artistEntity.setName(FAKE_NAME);
        artistEntity.setCover(new HashMap<String, String>() {});

        return artistEntity;
    }
}
