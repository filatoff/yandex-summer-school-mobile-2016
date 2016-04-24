package ru.yandex.summerschool2016.filatovaa.data.repository.datasource.settings;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.yandex.summerschool2016.filatovaa.data.sp.SettingsSharedPreferences;

/**
 * Factory that creates different implementations of {@link SettingsDataStore}.
 */
@Singleton
public class SettingsDataStoreFactory {

    private final SettingsSharedPreferences settingsSharedPreferences;

    @Inject
    public SettingsDataStoreFactory(SettingsSharedPreferences settingsSharedPreferences) {
        if (settingsSharedPreferences == null) {
            throw new IllegalArgumentException("Constructor parameters cannot be null!!!");
        }
        this.settingsSharedPreferences = settingsSharedPreferences;
    }

    /**
     * Create sort artist list {@link SettingsDataStore}.
     */
    public SettingsDataStore create() {
        // Always from shared preferences
        return new SharedPreferencesSettingsDataStore(settingsSharedPreferences);
    }
}
