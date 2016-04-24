package ru.yandex.summerschool2016.filatovaa.presentation.presenter;

import android.support.annotation.NonNull;

import ru.yandex.summerschool2016.filatovaa.domain.Artist;
import ru.yandex.summerschool2016.filatovaa.domain.exception.DefaultErrorBundle;
import ru.yandex.summerschool2016.filatovaa.domain.exception.ErrorBundle;
import ru.yandex.summerschool2016.filatovaa.domain.interactor.DefaultSubscriber;
import ru.yandex.summerschool2016.filatovaa.domain.interactor.GetArtistDetails;
import ru.yandex.summerschool2016.filatovaa.domain.interactor.UseCase;
import ru.yandex.summerschool2016.filatovaa.presentation.exception.ErrorMessageFactory;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.PerActivity;
import ru.yandex.summerschool2016.filatovaa.presentation.mapper.ArtistModelDataMapper;
import ru.yandex.summerschool2016.filatovaa.presentation.model.ArtistModel;
import ru.yandex.summerschool2016.filatovaa.presentation.view.ArtistDetailsView;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class ArtistDetailsPresenter implements Presenter {

    private ArtistDetailsView artistDetailsView;

    private final GetArtistDetails getArtistDetailsUseCase;
    private final ArtistModelDataMapper artistModelDataMapper;

    @Inject
    public ArtistDetailsPresenter(@Named("getArtistDetails") GetArtistDetails getArtistDetailsUseCase,
                                  ArtistModelDataMapper artistModelDataMapper) {
        this.getArtistDetailsUseCase = getArtistDetailsUseCase;
        this.artistModelDataMapper = artistModelDataMapper;
    }

    public void setView(@NonNull ArtistDetailsView view) {
        this.artistDetailsView = view;
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {
        this.getArtistDetailsUseCase.unsubscribe();
        this.artistDetailsView = null;
    }

    /**
     * Initializes the presenter by start retrieving artist details.
     */
    public void initialize() {
        this.loadArtistDetails();
    }

    /**
     * Loads artist details.
     */
    private void loadArtistDetails() {
        this.hideErrors();
        this.showViewLoading();
        this.getArtistDetails();
    }

    private void showViewLoading() {
        this.artistDetailsView.showLoading();
    }

    private void hideViewLoading() {
        this.artistDetailsView.hideLoading();
    }

    private void showError(ErrorBundle e, boolean showButtonRetry) {
        this.artistDetailsView.showError(getErrorMessage(e), showButtonRetry);
    }

    private void hideErrors() {
        this.artistDetailsView.hideError();
        this.artistDetailsView.hideImageError();
    }

    private String getErrorMessage(ErrorBundle errorBundle) {
        return ErrorMessageFactory.create(this.artistDetailsView.context(), errorBundle.getException());
    }

    /**
     * Invoke after success response from artist repository.
     * @param artist {@link Artist} for render on UI
     */
    private void showArtistDetailsInView(Artist artist) {
        final ArtistModel artistModel = this.artistModelDataMapper.transform(artist);
        this.artistDetailsView.prepareRenderArtist(artistModel);
    }

    /**
     * Execute response to artist repository.
     */
    private void getArtistDetails() {
        this.getArtistDetailsUseCase.execute(new ArtistDetailsSubscriber());
    }

    @RxLogSubscriber
    private final class ArtistDetailsSubscriber extends DefaultSubscriber<Artist> {

        @Override
        public void onCompleted() {}

        @Override
        public void onError(Throwable e) {
            ArtistDetailsPresenter.this.hideViewLoading();
            ArtistDetailsPresenter.this.showError(new DefaultErrorBundle((Exception) e), true);
        }

        @Override
        public void onNext(Artist artist) {
            ArtistDetailsPresenter.this.showArtistDetailsInView(artist);
        }
    }

    /**
     * Render artist on UI after loading photo.
     */
    public void renderArtist(boolean imageLoaded) {
        this.hideViewLoading();

        if (imageLoaded)
            this.artistDetailsView.renderArtistImage();
        else
            this.artistDetailsView.showImageError();

        this.artistDetailsView.renderArtistInfo();
    }
}
