package ru.yandex.summerschool2016.filatovaa.presentation.presenter;

import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import ru.yandex.summerschool2016.filatovaa.domain.Artist;
import ru.yandex.summerschool2016.filatovaa.domain.exception.DefaultErrorBundle;
import ru.yandex.summerschool2016.filatovaa.domain.exception.ErrorBundle;
import ru.yandex.summerschool2016.filatovaa.domain.interactor.DefaultSubscriber;
import ru.yandex.summerschool2016.filatovaa.domain.interactor.GetArtistList;
import ru.yandex.summerschool2016.filatovaa.domain.interactor.SettingsPreviewHide;
import ru.yandex.summerschool2016.filatovaa.domain.interactor.SettingsSortArtistList;
import ru.yandex.summerschool2016.filatovaa.presentation.exception.ErrorMessageFactory;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.PerActivity;
import ru.yandex.summerschool2016.filatovaa.presentation.mapper.ArtistModelDataMapper;
import ru.yandex.summerschool2016.filatovaa.presentation.model.ArtistModel;
import ru.yandex.summerschool2016.filatovaa.presentation.view.ArtistListView;
import ru.yandex.summerschool2016.filatovaa.presentation.view.PreviewView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class PreviewPresenter implements Presenter {

    private PreviewView previewView;

    private final SettingsPreviewHide settingsPreviewHideUseCase;

    @Inject
    public PreviewPresenter(
            @Named("settingsPreviewHide") SettingsPreviewHide settingsPreviewHideUseCase) {
        this.settingsPreviewHideUseCase = settingsPreviewHideUseCase;
    }

    public void setView(@NonNull PreviewView view) {
        this.previewView = view;
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {
        this.settingsPreviewHideUseCase.unsubscribe();
        this.previewView = null;
    }

    /**
     * Initializes the presenter by start checking preview hide.
     */
    public void initialize() {
        this.settingsPreviewHideUseCase.execute(new SettingsPreviewHideSubscriber());
    }

    @RxLogSubscriber
    private final class SettingsPreviewHideSubscriber extends DefaultSubscriber<Boolean> {
        @Override
        public void onCompleted() {}

        @Override
        public void onError(Throwable e) {}

        @Override
        public void onNext(Boolean previewHide) {
            if (previewHide)
                PreviewPresenter.this.closePreview(true);
        }
    }

    /**
     * Close preview.
     */
    public void onClickButtonToApp() {
        closePreview(false);
        this.settingsPreviewHideUseCase.putPreviewHide(true);
    }

    private void closePreview(boolean clearBackStack) {
        this.previewView.closePreview(clearBackStack);
    }
}