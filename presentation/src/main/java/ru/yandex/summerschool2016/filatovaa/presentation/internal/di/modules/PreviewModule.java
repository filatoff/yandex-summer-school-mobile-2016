package ru.yandex.summerschool2016.filatovaa.presentation.internal.di.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.yandex.summerschool2016.filatovaa.domain.interactor.SettingsPreviewHide;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.PerActivity;

/**
 * Dagger module that provides preview
 */
@Module
public class PreviewModule {

    @Provides
    @PerActivity
    @Named("settingsPreviewHide")
    SettingsPreviewHide provideSettingsPreviewHideUseCase(SettingsPreviewHide settingsPreviewHide) {
        return settingsPreviewHide;
    }
}
