package ru.yandex.summerschool2016.filatovaa.domain.repository;

import rx.Observable;

/**
 * Interface that represents a Repository for getting settings.
 */
public interface SettingsRepository {

    /**
     * Get an {@link rx.Observable} which will emit an integer of sort artist list
     */
    Observable<Integer> getSortArtistList();

    /**
     * Put sort artist list
     *
     * @param sort of artist list
     */
    void putSortArtistList(int sort);

    /**
     * Get an {@link rx.Observable} which will emit a boolean of preview hide
     */
    Observable<Boolean> getPreviewHide();

    /**
     * Put preview hide
     *
     * @param previewHide
     */
    void putPreviewHide(boolean previewHide);
}
