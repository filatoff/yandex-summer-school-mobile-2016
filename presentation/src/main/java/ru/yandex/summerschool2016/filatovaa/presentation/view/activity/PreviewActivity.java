package ru.yandex.summerschool2016.filatovaa.presentation.view.activity;

import android.os.Bundle;

import ru.yandex.summerschool2016.filatovaa.presentation.R;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.HasComponent;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.components.DaggerPreviewComponent;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.components.PreviewComponent;
import ru.yandex.summerschool2016.filatovaa.presentation.view.fragment.PreviewFragment;

/**
 * Preview application screen. This is the app entry point.
 * Displayed on first session only.
 */
public class PreviewActivity extends BaseActivity implements HasComponent<PreviewComponent>,
        PreviewFragment.PreviewListener {

    private PreviewComponent previewComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragment_container, new PreviewFragment());
        }
    }

    private void initializeInjector() {
        this.previewComponent = DaggerPreviewComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public PreviewComponent getComponent() {
        return previewComponent;
    }

    @Override
    public void onClosePreview(boolean clearBackStack) {
        this.navigator.navigateToArtistList(this);
        if (clearBackStack)
            finish();
    }
}
