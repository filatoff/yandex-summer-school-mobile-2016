package ru.yandex.summerschool2016.filatovaa.data.entity.mapper;

import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;
import ru.yandex.summerschool2016.filatovaa.domain.Artist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link ArtistEntity} (in the data layer) to {@link Artist} in the
 * domain layer.
 */
@Singleton
public class ArtistEntityDataMapper {

    @Inject
    public ArtistEntityDataMapper() {}

    /**
     * Transform a {@link ArtistEntity} into an {@link Artist}.
     *
     * @param artistEntity Object to be transformed.
     * @return {@link Artist} if valid {@link ArtistEntity} otherwise null.
     */
    public Artist transform(ArtistEntity artistEntity) {
        Artist artist = null;
        if (artistEntity != null) {
            artist = new Artist(artistEntity.getId());
            artist.setName(artistEntity.getName());
            artist.setDescription(artistEntity.getDescription());
            artist.setLink(artistEntity.getLink());
            artist.setTracks((artistEntity.getTracks() > Artist.TRACKS_MIN_VALUE)
                    ? artistEntity.getTracks()
                    : Artist.TRACKS_DEFAULT_VALUE);
            artist.setAlbums((artistEntity.getAlbums() > Artist.ALBUMS_MIN_VALUE)
                    ? artistEntity.getAlbums()
                    : Artist.ALBUMS_DEFAULT_VALUE);
            artist.setGenres(artistEntity.getGenres());
            artist.setCoverSmall((artistEntity.getCover().containsKey(ArtistEntity.COVER_SMALL_JSON_KEY))
                    ? artistEntity.getCover().get(ArtistEntity.COVER_SMALL_JSON_KEY)
                    : Artist.COVER_SMALL_DEFAULT_VALUE);
            artist.setCoverBig((artistEntity.getCover().containsKey(ArtistEntity.COVER_BIG_JSON_KEY))
                    ? artistEntity.getCover().get(ArtistEntity.COVER_BIG_JSON_KEY)
                    : Artist.COVER_BIG_DEFAULT_VALUE);
        }

        return artist;
    }

    /**
     * Transform a List of {@link ArtistEntity} into a Collection of {@link Artist}.
     *
     * @param artistEntityCollection Object Collection to be transformed.
     * @return {@link Artist} if valid {@link ArtistEntity} otherwise null.
     */
    public List<Artist> transform(Collection<ArtistEntity> artistEntityCollection) {
        List<Artist> artistList = new ArrayList<>();
        Artist artist;
        for (ArtistEntity artistEntity : artistEntityCollection) {
            artist = transform(artistEntity);
            if (artist != null) {
                artistList.add(artist);
            }
        }

        return artistList;
    }
}
