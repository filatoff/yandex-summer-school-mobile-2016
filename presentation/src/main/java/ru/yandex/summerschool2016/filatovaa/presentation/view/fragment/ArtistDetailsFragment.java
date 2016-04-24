package ru.yandex.summerschool2016.filatovaa.presentation.view.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seppius.i18n.plurals.PluralResources;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.yandex.summerschool2016.filatovaa.domain.executor.PostExecutionThread;
import ru.yandex.summerschool2016.filatovaa.presentation.R;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.components.ArtistComponent;
import ru.yandex.summerschool2016.filatovaa.presentation.model.ArtistModel;
import ru.yandex.summerschool2016.filatovaa.presentation.presenter.ArtistDetailsPresenter;
import ru.yandex.summerschool2016.filatovaa.presentation.utils.AnimationUtils;
import ru.yandex.summerschool2016.filatovaa.presentation.utils.StringUtils;
import ru.yandex.summerschool2016.filatovaa.presentation.view.ArtistDetailsView;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import javax.inject.Inject;

/**
 * Fragment that shows details of a certain artist.
 */
public class ArtistDetailsFragment extends BaseFragment implements ArtistDetailsView {

    private static final String TAG = "ArtistDetailsFragment";

    @Inject
    PostExecutionThread uiThread;
    @Inject
    Context context;
    @Inject
    ArtistDetailsPresenter artistDetailsPresenter;
    @Inject
    PluralResources pluralResources;

    @Bind(R.id.artist_detail__info)
    LinearLayout info;
    @Bind(R.id.artist_detail__image)
    ImageView image;
    @Bind(R.id.artist_detail__image_error)
    TextView imageError;
    @Bind(R.id.artist_detail__genres)
    TextView genres;
    @Bind(R.id.artist_detail__counters)
    TextView counters;
    @Bind(R.id.artist_detail__biography)
    TextView biography;
    @Bind(R.id.artist_detail__link)
    TextView link;
    @Bind(R.id.progress_bar)
    RelativeLayout progressBar;
    @Bind(R.id.error__text)
    TextView errorText;
    @Bind(R.id.error__button_retry)
    Button errorButtonRetry;

    public ArtistDetailsFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(ArtistComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_artist_details, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.artistDetailsPresenter.setView(this);
        this.loadArtistDetails();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.artistDetailsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.artistDetailsPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.artistDetailsPresenter.destroy();
    }

    @Override
    public void showLoading() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message, boolean showButtonRetry) {
        if (!TextUtils.isEmpty(message)) {
            this.errorText.setText(message);
            this.errorText.setVisibility(View.VISIBLE);
        }
        if (showButtonRetry)
            this.errorButtonRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void showImageError() {
        this.imageError.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideImageError() {
        this.imageError.setVisibility(View.GONE);
    }

    @Override
    public void hideError() {
        this.errorText.setVisibility(View.GONE);
        this.errorButtonRetry.setVisibility(View.GONE);
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    /**
     * Loads artist profile.
     */
    private void loadArtistDetails() {
        if (this.artistDetailsPresenter != null) {
            this.artistDetailsPresenter.initialize();
        }
    }

    @OnClick(R.id.error__button_retry)
    void onButtonRetryClick() {
        ArtistDetailsFragment.this.loadArtistDetails();
    }

    /**
     * Start render artist
     * @param artist The {@link ArtistModel} that will be shown.
     */
    @Override
    public void prepareRenderArtist(ArtistModel artist) {
        if (artist != null) {

            this.setTitle(artist.getName());

            try {
                this.renderImage(artist.getCoverBig());
            } catch (RenderImageReadyException e) {}

            this.genres.setText(StringUtils.join(
                    this.context.getString(R.string.artist_detail__genres_delimiter),
                    artist.getGenres()
            ));

            List<String> counters = new ArrayList<>();
            if (artist.getAlbums() > 0)
                counters.add(StringUtils.plural(
                        this.pluralResources, this.context, R.plurals.albums, artist.getAlbums()
                ));
            if (artist.getTracks() > 0)
                counters.add(StringUtils.plural(
                        this.pluralResources, this.context, R.plurals.tracks, artist.getTracks()
                ));
            this.counters.setText(StringUtils.join(
                    this.context.getString(R.string.artist_detail__counters_delimiter),
                    counters
            ));

            // Uppercase first letter always
            String biography = artist.getDescription().substring(0,1).toUpperCase() + artist.getDescription().substring(1);
            this.biography.setText(biography);

            if (artist.getLink() != null) {
                this.link.setText(artist.getLink());
                this.link.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Set action bar title if possible
     * @param title
     */
    private void setTitle(String title) {
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(title);
    }

    /**
     * Check non-zero image width for correct full width resize
     * @param imageUrl
     */
    private void renderImage(final String imageUrl) {
        Observable.interval(16, TimeUnit.MILLISECONDS, uiThread.getScheduler()).take(60)
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long input) {
                        int width = ArtistDetailsFragment.this.image.getWidth();
                        if (width > 0)
                            // image is ready for render
                            throw new RenderImageReadyException();

                        return width;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        // ... continue checked no zero width
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        // render image
                        Picasso.with(ArtistDetailsFragment.this.context).load(imageUrl)
                                .resize(ArtistDetailsFragment.this.image.getWidth(), 0)
                                .into(ArtistDetailsFragment.this.image, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        ArtistDetailsFragment.this.artistDetailsPresenter.renderArtist(true);
                                    }

                                    @Override
                                    public void onError() {
                                        ArtistDetailsFragment.this.artistDetailsPresenter.renderArtist(false);
                                    }
                                });
                    }
                });
    }

    @Override
    public void renderArtistImage() {
        this.image.startAnimation(AnimationUtils.createAlpha(0, 1, 1000));
    }

    @Override
    public void renderArtistInfo() {
        this.info.setVisibility(View.VISIBLE);
        this.info.startAnimation(AnimationUtils.createAlpha(0, 1, 1500));
    }

    /**
     * Custom exception for render image ready event
     */
    private class RenderImageReadyException extends RuntimeException {}
}
