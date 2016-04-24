package ru.yandex.summerschool2016.filatovaa.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.seppius.i18n.plurals.PluralResources;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.yandex.summerschool2016.filatovaa.presentation.R;
import ru.yandex.summerschool2016.filatovaa.presentation.model.ArtistModel;
import ru.yandex.summerschool2016.filatovaa.presentation.utils.AnimationUtils;
import ru.yandex.summerschool2016.filatovaa.presentation.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Adapter that manages a collection of {@link ArtistModel}.
 */
public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder> {

    public interface OnItemClickListener {
        void onArtistItemClicked(ArtistModel artistModel);
    }

    private Context context;
    private List<ArtistModel> artistsCollection;
    private final LayoutInflater layoutInflater;
    private final PluralResources pluralResources;

    private OnItemClickListener onItemClickListener;

    @Inject
    public ArtistsAdapter(Context context, PluralResources pluralResources) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.artistsCollection = Collections.emptyList();
        this.pluralResources = pluralResources;
    }

    @Override
    public int getItemCount() {
        return (this.artistsCollection != null) ? this.artistsCollection.size() : 0;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.list_item_artist, parent, false);
        return new ArtistViewHolder(view);
    }

    /**
     * Include animation.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ArtistViewHolder holder, final int position) {
        final ArtistModel artistModel = this.artistsCollection.get(position);

        Picasso.with(this.context).load(artistModel.getCoverSmall())
                .placeholder(R.drawable.artist_item__cover_preview)
                .error(R.drawable.artist_item__cover_preview)
                .into(holder.image);

        holder.name.setText(artistModel.getName());

        holder.genres.setText(StringUtils.join(
                this.context.getString(R.string.artist_list__item_genres_delimiter),
                artistModel.getGenres()
        ));

        List<String> counters = new ArrayList<>();
        if (artistModel.getAlbums() > 0)
            counters.add(StringUtils.plural(
                    this.pluralResources, this.context, R.plurals.albums, artistModel.getAlbums()
            ));
        if (artistModel.getTracks() > 0)
            counters.add(StringUtils.plural(
                    this.pluralResources, this.context, R.plurals.tracks, artistModel.getTracks()
            ));

        holder.counters.setText(StringUtils.join(
                this.context.getString(R.string.artist_list__item_counters_delimiter),
                counters
        ));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ArtistsAdapter.this.onItemClickListener != null) {
                    ArtistsAdapter.this.onItemClickListener.onArtistItemClicked(artistModel);
                }
            }
        });

        holder.itemView.startAnimation(AnimationUtils.createAlpha(0, 1, 1000));
    }

    @Override
    public void onViewDetachedFromWindow(ArtistViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Set adapter data
     * @param artistsCollection for view
     */
    public void setArtistsCollection(Collection<ArtistModel> artistsCollection) {
        this.validateArtistsCollection(artistsCollection);
        this.artistsCollection = (List<ArtistModel>) artistsCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateArtistsCollection(Collection<ArtistModel> artistsCollection) {
        if (artistsCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class ArtistViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_artist__image)
        ImageView image;
        @Bind(R.id.item_artist__name)
        TextView name;
        @Bind(R.id.item_artist__genres)
        TextView genres;
        @Bind(R.id.item_artist__counters)
        TextView counters;

        public ArtistViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
