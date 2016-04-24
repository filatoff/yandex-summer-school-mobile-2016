package ru.yandex.summerschool2016.filatovaa.presentation.view;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a preview screen.
 */
public interface PreviewView extends LoadDataView {

    /**
     * Close preview
     */
    void closePreview(boolean clearBackStack);
}
