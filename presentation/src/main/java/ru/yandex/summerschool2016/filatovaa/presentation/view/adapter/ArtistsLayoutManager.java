package ru.yandex.summerschool2016.filatovaa.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Layout manager to position items inside a {@link android.support.v7.widget.RecyclerView}.
 */
public class ArtistsLayoutManager extends GridLayoutManager {

    public ArtistsLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }
}
