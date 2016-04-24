package ru.yandex.summerschool2016.filatovaa.data.repository.datasource.settings;

import ru.yandex.summerschool2016.filatovaa.data.entity.ArtistEntity;

import java.util.List;

import rx.Observable;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface SettingsDataStore {
    /**
     * Get an {@link rx.Observable} which will emit an integer.
     */
    Observable<Integer> getSortArtistList();

    /**
     * Put integer value of sort artist list
     * @param sort
     */
    void putSortArtistList(int sort);

    /**
     * Get an {@link rx.Observable} which will emit a boolean.
     */
    Observable<Boolean> getPreviewHide();

    /**
     * Put boolean value of preview hide
     * @param previewHide
     */
    void putPreviewHide(boolean previewHide);
}
