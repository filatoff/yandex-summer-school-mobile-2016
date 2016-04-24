package ru.yandex.summerschool2016.filatovaa.data.cache.serializer;

import ru.yandex.summerschool2016.filatovaa.data.ApplicationTestCase;

import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JsonSerializerTest extends ApplicationTestCase {

    private static final String JSON_RESPONSE = "[{\n"
            + "\"id\": 1080505,\n"
            + "\"name\": \"Tove Lo\",\n"
            + "\"description\": \"Test description of Tove Lo\",\n"
            + "\"link\": \"http://www.tove-lo.com/\",\n"
            + "\"tracks\": 81,\n"
            + "\"albums\": 22,\n"
            + "\"genres\": [\"pop\", \"dance\", \"electronics\"],\n"
            + "\"cover\": {\"small\": \"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300\", \"big\": \"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000\"}\n"
            + "}, {\n"
            + "\"id\": 100500,\n"
            + "\"name\": \"Jay Sean\",\n"
            + "\"description\": \"Test description of Jay Sean\",\n"
            + "\"tracks\": 106,\n"
            + "\"albums\": 38,\n"
            + "\"genres\": [\"pop\", \"rap\", \"rnb\"],\n"
            + "\"cover\": {\"small\": \"http://avatars.yandex.net/get-music-content/db35e57a.p.100500/300x300\", \"big\": \"http://avatars.yandex.net/get-music-content/dfc531f5.p.10050/1000x1000\"}\n"
            + "}]";

    private JsonSerializer jsonSerializer;

    @Before
    public void setUp() {
        jsonSerializer = new JsonSerializer();
    }

    @Test
    public void testSerializeHappyCase() {
        List<ArtistEntity> artistEntitiesOne = jsonSerializer.deserialize(JSON_RESPONSE);
        String jsonString = jsonSerializer.serialize(artistEntitiesOne);
        List<ArtistEntity> artistEntitiesTwo = jsonSerializer.deserialize(jsonString);

        assertThat(artistEntitiesOne.size(), is(artistEntitiesTwo.size()));
        assertThat(artistEntitiesOne.get(0).getId(), is(artistEntitiesTwo.get(0).getId()));
        assertThat(artistEntitiesOne.get(1).getName(), is(equalTo(artistEntitiesTwo.get(1).getName())));
        assertThat(artistEntitiesOne.get(0).getTracks(), is(artistEntitiesTwo.get(0).getTracks()));

        assertThat(artistEntitiesOne.get(1).getGenres(), is(equalTo(artistEntitiesTwo.get(1).getGenres())));
        assertThat(artistEntitiesOne.get(0).getCover(), is(equalTo(artistEntitiesTwo.get(0).getCover())));
    }

    @Test
    public void testDesearializeHappyCase() {
        List<ArtistEntity> artistEntities = jsonSerializer.deserialize(JSON_RESPONSE);

        assertThat(artistEntities.get(0).getId(), is(1_080_505));
        assertThat(artistEntities.get(0).getLink(), is("http://www.tove-lo.com/"));
        assertThat(artistEntities.get(1).getAlbums(), is(38));

        assertThat(artistEntities.get(0).getGenres(), hasItem("dance"));
        assertThat(artistEntities.get(0).getGenres().size(), is(3));
        assertThat(artistEntities.get(1).getGenres(), hasItem("rap"));
        assertThat(artistEntities.get(1).getGenres().size(), is(3));
    }
}
