package ru.yandex.summerschool2016.filatovaa.presentation.view;

import ru.yandex.summerschool2016.filatovaa.presentation.model.ArtistModel;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing an artist details.
 */
public interface ArtistDetailsView extends LoadDataView {
    /**
     * Prepare render an artist in the UI.
     *
     * @param artist The {@link ArtistModel} that will be shown.
     */
    void prepareRenderArtist(ArtistModel artist);

    /**
     * Render artist image
     */
    void renderArtistImage();

    /**
     * Render artist info
     */
    void renderArtistInfo();

    /**
     * Show image error
     */
    void showImageError();

    /**
     * Hide image error
     */
    void hideImageError();
}
