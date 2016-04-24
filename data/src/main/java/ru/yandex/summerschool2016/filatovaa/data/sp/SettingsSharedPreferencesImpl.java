package ru.yandex.summerschool2016.filatovaa.data.sp;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.yandex.summerschool2016.filatovaa.data.exception.ArtistsNotFoundException;
import ru.yandex.summerschool2016.filatovaa.data.utils.SharedPreferences;
import rx.Observable;

@Singleton
public class SettingsSharedPreferencesImpl implements SettingsSharedPreferences {

    private final SharedPreferences sharedPreferences;

    @Inject
    public SettingsSharedPreferencesImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public Observable<Integer> getSortArtistList() {
        return Observable.create(subscriber -> {
            subscriber.onNext(sharedPreferences.getInt(SharedPreferences.SP_KEY_ARTIST_LIST_SORT));
            subscriber.onCompleted();
        });
    }

    @Override
    public void putSortArtistList(int sort) {
        sharedPreferences.putInt(SharedPreferences.SP_KEY_ARTIST_LIST_SORT, sort);
    }

    @Override
    public Observable<Boolean> getPreviewHide() {
        return Observable.create(subscriber -> {
            subscriber.onNext(sharedPreferences.getBoolean(SharedPreferences.SP_KEY_PREVIEW_HIDE));
            subscriber.onCompleted();
        });
    }

    @Override
    public void putPreviewHide(boolean previewHide) {
        sharedPreferences.putBoolean(SharedPreferences.SP_KEY_PREVIEW_HIDE, previewHide);
    }
}
