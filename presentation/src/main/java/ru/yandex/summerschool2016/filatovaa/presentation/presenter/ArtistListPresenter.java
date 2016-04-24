package ru.yandex.summerschool2016.filatovaa.presentation.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import ru.yandex.summerschool2016.filatovaa.domain.Artist;
import ru.yandex.summerschool2016.filatovaa.domain.exception.DefaultErrorBundle;
import ru.yandex.summerschool2016.filatovaa.domain.exception.ErrorBundle;
import ru.yandex.summerschool2016.filatovaa.domain.interactor.DefaultSubscriber;
import ru.yandex.summerschool2016.filatovaa.domain.interactor.GetArtistList;
import ru.yandex.summerschool2016.filatovaa.domain.interactor.SettingsSortArtistList;
import ru.yandex.summerschool2016.filatovaa.presentation.exception.ErrorMessageFactory;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.PerActivity;
import ru.yandex.summerschool2016.filatovaa.presentation.mapper.ArtistModelDataMapper;
import ru.yandex.summerschool2016.filatovaa.presentation.model.ArtistModel;
import ru.yandex.summerschool2016.filatovaa.presentation.view.ArtistListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class ArtistListPresenter implements Presenter {

    private ArtistListView artistListView;

    private final GetArtistList getArtistListUseCase;
    private final SettingsSortArtistList settingsSortArtistListUseCase;
    private final ArtistModelDataMapper artistModelDataMapper;

    private boolean loadingShow = false;

    private boolean errorShow = false;
    private String errorMessage = "";
    private boolean errorShowButtonRetry = false;

    @Inject
    public ArtistListPresenter(@Named("getArtistList") GetArtistList getArtistListUserCase,
                               @Named("settingsSortArtistList") SettingsSortArtistList settingsSortArtistListUseCase,
                               ArtistModelDataMapper artistModelDataMapper) {
        this.getArtistListUseCase = getArtistListUserCase;
        this.settingsSortArtistListUseCase = settingsSortArtistListUseCase;
        this.artistModelDataMapper = artistModelDataMapper;
    }

    public void setView(@NonNull ArtistListView view) {
        this.artistListView = view;
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void destroy() {
        this.getArtistListUseCase.unsubscribe();
        this.settingsSortArtistListUseCase.unsubscribe();
        this.artistListView = null;
    }

    /**
     * Restore view after change configuration.
     */
    public void restoreView() {
        if (this.errorShow)
            this.artistListView.showError(this.errorMessage, this.errorShowButtonRetry);

        if (this.loadingShow)
            this.artistListView.showLoading();
    }

    /**
     * Initializes the presenter by start retrieving the artist list.
     */
    public void initialize() {
        this.hideError();
        this.hideViewEmpty();
        this.showViewLoading();
        this.settingsSortArtistListUseCase.execute(new SettingsSortArtistListSubscriber());
    }

    @RxLogSubscriber
    private final class SettingsSortArtistListSubscriber extends DefaultSubscriber<Integer> {
        @Override
        public void onCompleted() {}

        @Override
        public void onError(Throwable e) {
            ArtistListPresenter.this.loadArtistList();
        }

        @Override
        public void onNext(Integer sort) {
            ArtistListPresenter.this.getArtistListUseCase.setSort(sort);
            ArtistListPresenter.this.loadArtistList();
        }
    }

    /**
     * Loads all artists.
     */
    public void loadArtistList() {
        this.hideError();
        this.hideViewEmpty();
        this.showViewLoading();
        this.getArtistList();
    }

    /**
     * Invoke on artists item clicked
     * @param artistModel {@link ArtistModel} for detalize
     */
    public void onArtistClicked(ArtistModel artistModel) {
        this.artistListView.viewArtist(artistModel);
    }

    private void showViewLoading() {
        this.artistListView.showLoading();
        this.loadingShow = true;
    }

    private void hideViewLoading() {
        this.artistListView.hideLoading();
        this.loadingShow = false;
    }

    private void showError(ErrorBundle e, boolean showButtonRetry) {
        String errorMessage = getErrorMessage(e);

        this.artistListView.showError(errorMessage, showButtonRetry);

        this.errorShow = true;
        this.errorMessage = errorMessage;
        this.errorShowButtonRetry = showButtonRetry;
    }

    private void hideError() {
        this.artistListView.hideError();
        this.errorShow = false;
    }

    private String getErrorMessage(ErrorBundle errorBundle) {
        return ErrorMessageFactory.create(this.artistListView.context(), errorBundle.getException());
    }

    private void showViewEmpty() {
        this.artistListView.showEmpty();
    }

    private void hideViewEmpty() {
        this.artistListView.hideEmpty();
    }

    /**
     * Render artist list on UI after response from artists repository.
     * @param artistsCollection {@link Collection<Artist>} for render
     */
    private void showArtistsCollectionInView(Collection<Artist> artistsCollection) {
        final Collection<ArtistModel> artistModelsCollection = this.artistModelDataMapper.transform(artistsCollection);
        this.artistListView.renderArtistList(artistModelsCollection);
        if (artistModelsCollection.size() == 0)
            this.showViewEmpty();
    }

    /**
     * Execute response to artist repository
     */
    private void getArtistList() {
        this.getArtistListUseCase.execute(new ArtistListSubscriber());
    }

    @RxLogSubscriber
    private final class ArtistListSubscriber extends DefaultSubscriber<List<Artist>> {

        @Override
        public void onCompleted() {
            ArtistListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            ArtistListPresenter.this.hideViewLoading();
            ArtistListPresenter.this.showError(new DefaultErrorBundle((Exception) e), true);
        }

        @Override
        public void onNext(List<Artist> artists) {
            ArtistListPresenter.this.showArtistsCollectionInView(artists);
        }
    }

    /**
     * Search artists
     */
    public void search(String searchText) {
        this.hideError();
        this.hideViewEmpty();
        this.showViewLoading();
        this.searchArtistList(searchText);
    }

    private void searchArtistList(String searchText) {;
        this.getArtistListUseCase.setSearchText(searchText);
        this.getArtistList();
    }

    /**
     * Sorting
     */
    public void initializeSortMenu() {
       this.artistListView.setSort(this.getArtistListUseCase.getSort());
    }

    public void changeSort(int sort) {
        if (sort != this.getArtistListUseCase.getSort()) {
            // draw on view menu
            this.artistListView.setSort(sort);

            // get artist list
            this.getArtistListUseCase.setSort(sort);
            this.getArtistList();

            // save setting to repository
            this.settingsSortArtistListUseCase.putSortArtistList(sort);
        }
    }
}
