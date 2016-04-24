package ru.yandex.summerschool2016.filatovaa.presentation.view;

import android.content.Context;

/**
 * Interface representing a View that will use to load data.
 */
public interface LoadDataView {
    /**
     * Show a view with a progress bar indicating a loading process.
     */
    void showLoading();

    /**
     * Hide a loading view.
     */
    void hideLoading();

    /**
     * Show an error message
     *
     * @param message A string representing an error
     * @param showButtonRetry boolean of showing retry button
     */
    void showError(String message, boolean showButtonRetry);

    /**
     * Hide an error message
     */
    void hideError();

    /**
     * Get a {@link android.content.Context}.
     */
    Context context();
}
