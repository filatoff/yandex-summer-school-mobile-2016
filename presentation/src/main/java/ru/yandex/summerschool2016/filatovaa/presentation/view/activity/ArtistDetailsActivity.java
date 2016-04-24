package ru.yandex.summerschool2016.filatovaa.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import ru.yandex.summerschool2016.filatovaa.presentation.R;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.HasComponent;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.components.ArtistComponent;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.components.DaggerArtistComponent;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.modules.ArtistModule;
import ru.yandex.summerschool2016.filatovaa.presentation.view.fragment.ArtistDetailsFragment;

/**
 * Activity that shows details of a certain artist.
 */
public class ArtistDetailsActivity extends BaseActivity implements HasComponent<ArtistComponent> {

    private static final String INTENT_EXTRA_PARAM_ARTIST_ID = "ru.yandex.summerschool.filatovaa.INTENT_PARAM_ARTIST_ID";
    private static final String INSTANCE_STATE_PARAM_ARTIST_ID = "ru.yandex.summerschool.filatovaa.STATE_PARAM_ARTIST_ID";

    public static Intent getCallingIntent(Context context, int artistId) {
        Intent callingIntent = new Intent(context, ArtistDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_ARTIST_ID, artistId);
        return callingIntent;
    }

    private int artistId;
    private ArtistComponent artistComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_ARTIST_ID, this.artistId);
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.artistId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_ARTIST_ID, -1);
            addFragment(R.id.fragment_container, new ArtistDetailsFragment());
        } else {
            this.artistId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_ARTIST_ID);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initializeInjector() {
        this.artistComponent = DaggerArtistComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .artistModule(new ArtistModule(this.artistId))
                .build();
    }

    @Override
    public ArtistComponent getComponent() {
        return artistComponent;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
