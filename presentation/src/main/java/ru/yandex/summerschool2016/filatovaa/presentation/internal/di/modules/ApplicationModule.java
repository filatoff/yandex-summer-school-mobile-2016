package ru.yandex.summerschool2016.filatovaa.presentation.internal.di.modules;

import android.content.Context;

import com.seppius.i18n.plurals.PluralResources;

import ru.yandex.summerschool2016.filatovaa.data.cache.ArtistCache;
import ru.yandex.summerschool2016.filatovaa.data.cache.ArtistCacheImpl;
import ru.yandex.summerschool2016.filatovaa.data.executor.JobExecutor;
import ru.yandex.summerschool2016.filatovaa.data.repository.ArtistDataRepository;
import ru.yandex.summerschool2016.filatovaa.data.repository.SettingsDataRepository;
import ru.yandex.summerschool2016.filatovaa.data.runtime.ArtistRuntime;
import ru.yandex.summerschool2016.filatovaa.data.runtime.ArtistRuntimeImpl;
import ru.yandex.summerschool2016.filatovaa.data.sp.SettingsSharedPreferences;
import ru.yandex.summerschool2016.filatovaa.data.sp.SettingsSharedPreferencesImpl;
import ru.yandex.summerschool2016.filatovaa.domain.executor.PostExecutionThread;
import ru.yandex.summerschool2016.filatovaa.domain.executor.ThreadExecutor;
import ru.yandex.summerschool2016.filatovaa.domain.repository.ArtistRepository;
import ru.yandex.summerschool2016.filatovaa.domain.repository.SettingsRepository;
import ru.yandex.summerschool2016.filatovaa.presentation.AndroidApplication;
import ru.yandex.summerschool2016.filatovaa.presentation.UIThread;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    ArtistCache provideArtistCache(ArtistCacheImpl artistCache) {
        return artistCache;
    }

    @Provides
    @Singleton
    ArtistRuntime provideArtistRuntime(ArtistRuntimeImpl artistRuntime) {
        return artistRuntime;
    }

    @Provides
    @Singleton
    ArtistRepository provideArtistRepository(ArtistDataRepository artistDataRepository) {
        return artistDataRepository;
    }

    @Provides
    @Singleton
    SettingsSharedPreferences provideSettingsSharedPreferences(
            SettingsSharedPreferencesImpl settingsSharedPreferences) {
        return settingsSharedPreferences;
    }

    @Provides
    @Singleton
    SettingsRepository provideSettingsRepository(SettingsDataRepository settingsDataRepository) {
        return settingsDataRepository;
    }

    @Provides
    @Singleton
    PluralResources providePluralResources(Context context) {
        PluralResources pluralResources = null;
        try {
            pluralResources = new PluralResources(context.getResources());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return pluralResources;
    }
}
