package ru.yandex.summerschool2016.filatovaa.data.runtime;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;
import ru.yandex.summerschool2016.filatovaa.data.exception.ArtistsNotFoundException;
import rx.Observable;

@Singleton
public class ArtistRuntimeImpl implements ArtistRuntime {

    private List<ArtistEntity> artistEntities = null;

    @Inject
    public ArtistRuntimeImpl() {}

    @Override
    public Observable<List<ArtistEntity>> get() {
        return Observable.create(subscriber -> {
            if (artistEntities != null) {
                subscriber.onNext(artistEntities);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new ArtistsNotFoundException());
            }
        });
    }

    @Override
    public void put(List<ArtistEntity> artistEntities) {
        if (artistEntities != null) {
            this.artistEntities = artistEntities;
        }
    }

    @Override
    public boolean isCached() {
        return (this.artistEntities != null);
    }
}
