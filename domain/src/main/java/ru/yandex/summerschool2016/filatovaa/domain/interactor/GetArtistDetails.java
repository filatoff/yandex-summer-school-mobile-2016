package ru.yandex.summerschool2016.filatovaa.domain.interactor;

import ru.yandex.summerschool2016.filatovaa.domain.Artist;
import ru.yandex.summerschool2016.filatovaa.domain.executor.PostExecutionThread;
import ru.yandex.summerschool2016.filatovaa.domain.executor.ThreadExecutor;
import ru.yandex.summerschool2016.filatovaa.domain.repository.ArtistRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving data related to an specific {@link Artist}.
 */
public class GetArtistDetails extends UseCase {

    private final int id;
    private final ArtistRepository artistRepository;

    @Inject
    public GetArtistDetails(int id, ArtistRepository artistRepository,
                          ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.id = id;
        this.artistRepository = artistRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.artistRepository.artist(this.id);
    }
}
