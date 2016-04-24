package ru.yandex.summerschool2016.filatovaa.presentation.internal.di.modules;

import ru.yandex.summerschool2016.filatovaa.domain.executor.PostExecutionThread;
import ru.yandex.summerschool2016.filatovaa.domain.executor.ThreadExecutor;
import ru.yandex.summerschool2016.filatovaa.domain.interactor.GetArtistDetails;
import ru.yandex.summerschool2016.filatovaa.domain.interactor.GetArtistList;
import ru.yandex.summerschool2016.filatovaa.domain.interactor.SettingsSortArtistList;
import ru.yandex.summerschool2016.filatovaa.domain.repository.ArtistRepository;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;

/**
 * Dagger module that provides artists
 */
@Module
public class ArtistModule {

    private int id = -1;

    public ArtistModule() {}

    public ArtistModule(int id) {
        this.id = id;
    }

    @Provides
    @PerActivity
    @Named("getArtistList")
    GetArtistList provideGetArtistListUseCase(GetArtistList getArtistList) {
        return getArtistList;
    }

    @Provides
    @PerActivity
    @Named("getArtistDetails")
    GetArtistDetails provideGetArtistDetailsUseCase(ArtistRepository artistRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        return new GetArtistDetails(id, artistRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("settingsSortArtistList")
    SettingsSortArtistList provideSettingsSortArtistListUseCase(SettingsSortArtistList settingsSortArtistList) {
        return settingsSortArtistList;
    }
}