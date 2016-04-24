package ru.yandex.summerschool2016.filatovaa.presentation.mapper;

import ru.yandex.summerschool2016.filatovaa.domain.Artist;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.PerActivity;
import ru.yandex.summerschool2016.filatovaa.presentation.model.ArtistModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link Artist} (in the domain layer) to {@link ArtistModel} in the
 * presentation layer.
 */
@PerActivity
public class ArtistModelDataMapper {

    @Inject
    public ArtistModelDataMapper() {}

    /**
     * Transform a {@link Artist} into an {@link ArtistModel}.
     *
     * @param artist Object to be transformed.
     * @return {@link ArtistModel}.
     */
    public ArtistModel transform(Artist artist) {
        if (artist == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        ArtistModel artistModel = new ArtistModel(artist.getId());
        artistModel.setName(artist.getName());
        artistModel.setDescription(artist.getDescription());
        artistModel.setLink(artist.getLink());
        artistModel.setTracks(artist.getTracks());
        artistModel.setAlbums(artist.getAlbums());
        artistModel.setGenres(artist.getGenres());
        artistModel.setCoverSmall(artist.getCoverSmall());
        artistModel.setCoverBig(artist.getCoverBig());

        return artistModel;
    }

    /**
     * Transform a Collection of {@link Artist} into a Collection of {@link ArtistModel}.
     *
     * @param artistsCollection Objects to be transformed.
     * @return List of {@link ArtistModel}.
     */
    public Collection<ArtistModel> transform(Collection<Artist> artistsCollection) {
        Collection<ArtistModel> artistModelsCollection;

        if (artistsCollection != null && !artistsCollection.isEmpty()) {
            artistModelsCollection = new ArrayList<>();
            for (Artist artist : artistsCollection) {
                artistModelsCollection.add(transform(artist));
            }
        } else {
            artistModelsCollection = Collections.emptyList();
        }

        return artistModelsCollection;
    }
}
