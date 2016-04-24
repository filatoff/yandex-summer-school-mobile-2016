package ru.yandex.summerschool2016.filatovaa.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.yandex.summerschool2016.filatovaa.data.repository.datasource.settings.SettingsDataStoreFactory;
import ru.yandex.summerschool2016.filatovaa.domain.repository.SettingsRepository;
import rx.Observable;

/**
 * {@link SettingsRepository} for retrieving settings data.
 */
@Singleton
public class SettingsDataRepository implements SettingsRepository {

    private final SettingsDataStoreFactory settingsDataStoreFactory;

    /**
     * Constructs a {@link SettingsRepository}.
     *
     * @param dataStoreFactory A factory to construct different data source implementations.
     */
    @Inject
    public SettingsDataRepository(SettingsDataStoreFactory dataStoreFactory) {
        this.settingsDataStoreFactory = dataStoreFactory;
    }

    @Override
    public Observable<Integer> getSortArtistList() {
        return this.settingsDataStoreFactory.create().getSortArtistList();
    }

    @Override
    public void putSortArtistList(int sort) {
        this.settingsDataStoreFactory.create().putSortArtistList(sort);
    }

    @Override
    public Observable<Boolean> getPreviewHide() {
        return this.settingsDataStoreFactory.create().getPreviewHide();
    }

    @Override
    public void putPreviewHide(boolean previewHide) {
        this.settingsDataStoreFactory.create().putPreviewHide(previewHide);
    }
}
