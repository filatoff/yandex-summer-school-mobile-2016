package ru.yandex.summerschool2016.filatovaa.data.repository;

import android.text.TextUtils;

import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;
import ru.yandex.summerschool2016.filatovaa.data.entity.mapper.ArtistEntityDataMapper;
import ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist.ArtistDataStore;
import ru.yandex.summerschool2016.filatovaa.data.repository.datasource.artist.ArtistDataStoreFactory;
import ru.yandex.summerschool2016.filatovaa.domain.Artist;
import ru.yandex.summerschool2016.filatovaa.domain.repository.ArtistRepository;
import ru.yandex.summerschool2016.filatovaa.domain.interactor.GetArtistList;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * {@link ArtistRepository} for retrieving artist data.
 */
@Singleton
public class ArtistDataRepository implements ArtistRepository {

    private final ArtistDataStoreFactory artistDataStoreFactory;
    private final ArtistEntityDataMapper artistEntityDataMapper;

    /**
     * Constructs a {@link ArtistRepository}.
     *
     * @param dataStoreFactory A factory to construct different data source implementations.
     * @param artistEntityDataMapper {@link ArtistEntityDataMapper}.
     */
    @Inject
    public ArtistDataRepository(ArtistDataStoreFactory dataStoreFactory, ArtistEntityDataMapper artistEntityDataMapper) {
        this.artistDataStoreFactory = dataStoreFactory;
        this.artistEntityDataMapper = artistEntityDataMapper;
    }

    @SuppressWarnings("Convert2MethodRef")
    @Override
    public Observable<List<Artist>> artists(int sort, String searchText) {
        final ArtistDataStore artistDataStore = this.artistDataStoreFactory.create();

        Observable<List<ArtistEntity>> observableArtistsEntityList = artistDataStore.artistEntityList();

        if (!TextUtils.isEmpty(searchText))
            observableArtistsEntityList = observableArtistsEntityList
                    .flatMap(artistEntities -> Observable.from(artistEntities))
                    .filter(artistEntity -> artistEntity.getName().toUpperCase().contains(searchText.toUpperCase()))
                    .toList();

        switch (sort) {
            case GetArtistList.SORT_BY_NAME:
                observableArtistsEntityList = observableArtistsEntityList
                        .flatMap(Observable::from)
                        .toSortedList((artistEntity, artistEntity2) ->
                                artistEntity.getName().compareTo(artistEntity2.getName()));
                break;
            case GetArtistList.SORT_BY_TRACKS:
                observableArtistsEntityList = observableArtistsEntityList
                        .flatMap(Observable::from)
                        .toSortedList((artistEntity, artistEntity2) ->
                                artistEntity2.getTracks() - artistEntity.getTracks());
                break;
            case GetArtistList.SORT_NONE:
            default:
                break;
        }

        return observableArtistsEntityList
                .map(artistEntities -> this.artistEntityDataMapper.transform(artistEntities));
    }

    @SuppressWarnings("Convert2MethodRef")
    @Override
    public Observable<Artist> artist(int id) {
        final ArtistDataStore artistDataStore = this.artistDataStoreFactory.create();
        return artistDataStore.artistEntityList()
                .flatMap(Observable::from)
                .firstOrDefault(null, artistEntity -> (artistEntity.getId() == id))
                .map(artistEntity -> this.artistEntityDataMapper.transform(artistEntity));
    }
}
