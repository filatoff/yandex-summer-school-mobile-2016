package ru.yandex.summerschool2016.filatovaa.data.sp;

import rx.Observable;

/**
 * An interface representing an settings on shared preferences.
 */
public interface SettingsSharedPreferences {
    /**
     * Gets an {@link rx.Observable} which will emit an integer.
     */
    Observable<Integer> getSortArtistList();

    /**
     * Puts an element into the shared preferences.
     *
     * @param sort to shared preferences.
     */
    void putSortArtistList(int sort);

    /**
     * Gets an {@link rx.Observable} which will emit a boolean.
     */
    Observable<Boolean> getPreviewHide();

    /**
     * Puts an element into the shared preferences.
     *
     * @param previewHide to shared preferences.
     */
    void putPreviewHide(boolean previewHide);
}
