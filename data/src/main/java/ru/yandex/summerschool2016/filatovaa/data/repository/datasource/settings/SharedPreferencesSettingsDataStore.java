package ru.yandex.summerschool2016.filatovaa.data.repository.datasource.settings;

import ru.yandex.summerschool2016.filatovaa.data.sp.SettingsSharedPreferences;
import rx.Observable;

/**
 * {@link SettingsDataStore} implementation based on shared preferences.
 */
public class SharedPreferencesSettingsDataStore implements SettingsDataStore {

    private final SettingsSharedPreferences settingsSharedPreferences;

    /**
     * Construct a {@link SettingsDataStore} based on shared preferences.
     *
     * @param settingsSharedPreferences A {@link SettingsSharedPreferences} to set data to shared preferences.
     */
    public SharedPreferencesSettingsDataStore(SettingsSharedPreferences settingsSharedPreferences) {
        this.settingsSharedPreferences = settingsSharedPreferences;
    }

    @Override
    public Observable<Integer> getSortArtistList() {
        return this.settingsSharedPreferences.getSortArtistList();
    }

    @Override
    public void putSortArtistList(int sort) {
        this.settingsSharedPreferences.putSortArtistList(sort);
    }

    @Override
    public Observable<Boolean> getPreviewHide() {
        return this.settingsSharedPreferences.getPreviewHide();
    }

    @Override
    public void putPreviewHide(boolean previewHide) {
        this.settingsSharedPreferences.putPreviewHide(previewHide);
    }
}
