package ru.yandex.summerschool2016.filatovaa.presentation;

import android.app.Application;

import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.components.ApplicationComponent;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.components.DaggerApplicationComponent;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.modules.ApplicationModule;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        this.initializePicasso();
        this.initializeLeakDetection();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    private void initializePicasso() {
        new Picasso.Builder(this)
                .downloader(new OkHttpDownloader(new OkHttpClient()))
                .build();
    }

    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }
}
