package ru.yandex.summerschool2016.filatovaa.domain.interactor;

import ru.yandex.summerschool2016.filatovaa.domain.Artist;
import ru.yandex.summerschool2016.filatovaa.domain.executor.PostExecutionThread;
import ru.yandex.summerschool2016.filatovaa.domain.executor.ThreadExecutor;
import ru.yandex.summerschool2016.filatovaa.domain.repository.ArtistRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Artist}.
 */
public class GetArtistList extends UseCase {

    public static final int SORT_NONE = 0;
    public static final int SORT_BY_NAME = 1;
    public static final int SORT_BY_TRACKS = 2;

    private final ArtistRepository artistRepository;

    private int sort = SORT_NONE;
    private String searchText = null;

    @Inject
    public GetArtistList(ArtistRepository artistRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.artistRepository = artistRepository;
    }

    @Override
    public Observable buildUseCaseObservable() {
        return this.artistRepository.artists(sort, searchText);
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getSort() {
        return this.sort;
    }
}
