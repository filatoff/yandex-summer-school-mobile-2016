package ru.yandex.summerschool2016.filatovaa.domain.repository;

import java.util.List;

import ru.yandex.summerschool2016.filatovaa.domain.Artist;
import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link Artist} related data.
 */
public interface ArtistRepository {
    /**
     * Get an {@link rx.Observable} which will emit a List of {@link Artist}.
     */
    Observable<List<Artist>> artists(int sort, String searchText);

    /**
     * Get an {@link rx.Observable} which will emit a {@link Artist}.
     *
     * @param id The artist id used to retrieve artist data.
     */
    Observable<Artist> artist(final int id);
}
