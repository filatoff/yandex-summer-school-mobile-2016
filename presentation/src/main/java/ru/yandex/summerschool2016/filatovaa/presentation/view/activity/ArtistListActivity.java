package ru.yandex.summerschool2016.filatovaa.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import ru.yandex.summerschool2016.filatovaa.presentation.R;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.HasComponent;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.components.ArtistComponent;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.components.DaggerArtistComponent;
import ru.yandex.summerschool2016.filatovaa.presentation.model.ArtistModel;
import ru.yandex.summerschool2016.filatovaa.presentation.view.fragment.ArtistListFragment;

/**
 * Activity that shows a list of artists.
 */
public class ArtistListActivity extends BaseActivity implements HasComponent<ArtistComponent>,
        ArtistListFragment.ArtistListListener {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, ArtistListActivity.class);
    }

    private ArtistComponent artistComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();
        if (savedInstanceState == null) {
            addFragment(R.id.fragment_container, new ArtistListFragment());
        }
    }

    private void initializeInjector() {
        this.artistComponent = DaggerArtistComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public ArtistComponent getComponent() {
        return artistComponent;
    }

    @Override
    public void onArtistClicked(ArtistModel artistModel) {
        this.navigator.navigateToArtistDetails(this, artistModel.getId());
    }
}
