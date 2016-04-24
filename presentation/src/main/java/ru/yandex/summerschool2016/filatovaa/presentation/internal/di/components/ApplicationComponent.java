package ru.yandex.summerschool2016.filatovaa.presentation.internal.di.components;

import android.content.Context;

import com.seppius.i18n.plurals.PluralResources;

import ru.yandex.summerschool2016.filatovaa.domain.executor.PostExecutionThread;
import ru.yandex.summerschool2016.filatovaa.domain.executor.ThreadExecutor;
import ru.yandex.summerschool2016.filatovaa.domain.repository.ArtistRepository;
import ru.yandex.summerschool2016.filatovaa.domain.repository.SettingsRepository;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.modules.ApplicationModule;
import ru.yandex.summerschool2016.filatovaa.presentation.view.activity.BaseActivity;
import dagger.Component;

import javax.inject.Singleton;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    // Exposed to sub-graphs:

    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    ArtistRepository artistRepository();

    SettingsRepository settingsRepository();

    PluralResources pluralResources();
}
