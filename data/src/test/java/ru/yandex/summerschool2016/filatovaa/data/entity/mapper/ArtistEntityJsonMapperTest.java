package ru.yandex.summerschool2016.filatovaa.data.entity.mapper;

import ru.yandex.summerschool2016.filatovaa.data.ApplicationTestCase;
import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;

import com.google.gson.JsonSyntaxException;

import java.util.Collection;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ArtistEntityJsonMapperTest extends ApplicationTestCase {

    private static final String JSON_RESPONSE_ARTIST_DETAILS = "{\n"
            + "\"id\": 1080505,\n"
            + "\"name\": \"Tove Lo\",\n"
            + "\"description\": \"Test description\",\n"
            + "\"link\": \"http://www.tove-lo.com/\",\n"
            + "\"tracks\": 81,\n"
            + "\"albums\": 22,\n"
            + "\"genres\": [\"pop\", \"dance\", \"electronics\"],\n"
            + "\"cover\": {\"small\": \"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300\", \"big\": \"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000\"}\n"
            + "}";

    private static final String JSON_RESPONSE_ARTIST_COLLECTION = "[{\n"
            + "\"id\": 1080505,\n"
            + "\"name\": \"Tove Lo\",\n"
            + "\"description\": \"Test description of Tove Lo\",\n"
            + "\"link\": \"http://www.tove-lo.com/\",\n"
            + "\"tracks\": 81,\n"
            + "\"albums\": 22,\n"
            + "\"genres\": [\"pop\", \"dance\", \"electronics\"],\n"
            + "\"cover\": {\"small\": \"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300\", \"big\": \"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000\"}\n"
            + "}, {\n"
            + "\"id\": 2915,\n"
            + "\"name\": \"Ne-Yo\",\n"
            + "\"description\": \"Test description of Ne-Yo\",\n"
            + "\"link\": \"http://www.neyothegentleman.com/\",\n"
            + "\"tracks\": 256,\n"
            + "\"albums\": 152,\n"
            + "\"genres\": [\"rnb\", \"pop\", \"rap\"],\n"
            + "\"cover\": {\"small\": \"http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/300x300\", \"big\": \"http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/1000x1000\"}\n"
            + "}]";

    private ArtistEntityJsonMapper artistEntityJsonMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        artistEntityJsonMapper = new ArtistEntityJsonMapper();
    }

    @Test
    public void testTransformArtistEntityHappyCase() {
        ArtistEntity artistEntity = artistEntityJsonMapper.transformArtistEntity(JSON_RESPONSE_ARTIST_DETAILS);

        assertThat(artistEntity.getId(), is(1_080_505));
        assertThat(artistEntity.getName(), is(equalTo("Tove Lo")));
        assertThat(artistEntity.getLink(), is(equalTo("http://www.tove-lo.com/")));
    }

    @Test
    public void testTransformArtistEntityCollectionHappyCase() {
        Collection<ArtistEntity> artistEntityCollection = artistEntityJsonMapper.transformArtistEntityCollection(JSON_RESPONSE_ARTIST_COLLECTION);

        assertThat(((ArtistEntity) artistEntityCollection.toArray()[0]).getId(), is(1_080_505));
        assertThat(((ArtistEntity) artistEntityCollection.toArray()[1]).getId(), is(2_915));
        assertThat(artistEntityCollection.size(), is(2));
    }

    @Test
    public void testTransformArtistEntityNotValidResponse() {
        expectedException.expect(JsonSyntaxException.class);
        artistEntityJsonMapper.transformArtistEntity("Pugatcheva");
    }

    @Test
    public void testTransformArtistEntityCollectionNotValidResponse() {
        expectedException.expect(JsonSyntaxException.class);
        artistEntityJsonMapper.transformArtistEntityCollection("Kirkorov");
    }
}
