package ru.yandex.summerschool2016.filatovaa.data.cache.serializer;

import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class user as Serializer/Deserializer for artist entities.
 */
@Singleton
public class JsonSerializer {

    private final Gson gson = new Gson();

    @Inject
    public JsonSerializer() {}

    /**
     * Serialize a list of artists to Json.
     *
     * @param artistEntities list of {@link ArtistEntity} to serialize.
     */
    public String serialize(List<ArtistEntity> artistEntities) {
        Type listOfArtistEntityType = new TypeToken<List<ArtistEntity>>() {}.getType();
        return gson.toJson(artistEntities, listOfArtistEntityType);
    }

    /**
     * Deserialize a json representation of an artists list.
     *
     * @param jsonString A json string to deserialize.
     * @return list of {@link ArtistEntity}
     */
    public List<ArtistEntity> deserialize(String jsonString) {
        Type listOfArtistEntityType = new TypeToken<List<ArtistEntity>>() {}.getType();
        return gson.fromJson(jsonString, listOfArtistEntityType);
    }
}
