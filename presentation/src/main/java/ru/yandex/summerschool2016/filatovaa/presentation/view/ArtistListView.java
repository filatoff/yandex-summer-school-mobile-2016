package ru.yandex.summerschool2016.filatovaa.presentation.view;

import ru.yandex.summerschool2016.filatovaa.presentation.model.ArtistModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link ArtistModel}.
 */
public interface ArtistListView extends LoadDataView {

    /**
     * Render an artist list in the UI.
     *
     * @param artistModelCollection The collection of {@link ArtistModel} that will be shown.
     */
    void renderArtistList(Collection<ArtistModel> artistModelCollection);

    /**
     * View a {@link ArtistModel} details.
     *
     * @param artistModel The artist that will be shown.
     */
    void viewArtist(ArtistModel artistModel);

    /**
     * Show a message on empty content
     */
    void showEmpty();

    /**
     * Hide a message on empty content
     */
    void hideEmpty();

    /**
     * Select sort menu item
     *
     * @param sort integer of item
     */
    void setSort(int sort);
}
