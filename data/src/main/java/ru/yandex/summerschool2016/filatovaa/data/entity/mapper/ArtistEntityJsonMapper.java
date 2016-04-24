package ru.yandex.summerschool2016.filatovaa.data.entity.mapper;

import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class ArtistEntityJsonMapper {

    private final Gson gson;

    @Inject
    public ArtistEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link ArtistEntity}.
     *
     * @param artistJsonResponse A json representing an artist profile.
     * @return {@link ArtistEntity}.
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
     */
    public ArtistEntity transformArtistEntity(String artistJsonResponse) throws JsonSyntaxException {
        try {
            Type artistEntityType = new TypeToken<ArtistEntity>() {}.getType();
            ArtistEntity artistEntity = this.gson.fromJson(artistJsonResponse, artistEntityType);

            return artistEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }

    /**
     * Transform from valid json string to List of {@link ArtistEntity}.
     *
     * @param artistListJsonResponse A json representing a collection of artists.
     * @return List of {@link ArtistEntity}.
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
     */
    public List<ArtistEntity> transformArtistEntityCollection(String artistListJsonResponse)
            throws JsonSyntaxException {

        List<ArtistEntity> artistEntityCollection;
        try {
            Type listOfArtistEntityType = new TypeToken<List<ArtistEntity>>() {}.getType();
            artistEntityCollection = this.gson.fromJson(artistListJsonResponse, listOfArtistEntityType);

            return artistEntityCollection;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}
