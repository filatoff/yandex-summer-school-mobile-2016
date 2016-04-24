package ru.yandex.summerschool2016.filatovaa.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.yandex.summerschool2016.filatovaa.presentation.R;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.components.PreviewComponent;
import ru.yandex.summerschool2016.filatovaa.presentation.presenter.PreviewPresenter;
import ru.yandex.summerschool2016.filatovaa.presentation.view.PreviewView;

import javax.inject.Inject;

/**
 * Fragment that shows a preview screen.
 */
public class PreviewFragment extends BaseFragment implements PreviewView {

    /**
     * Interface for preview events
     */
    public interface PreviewListener {
        void onClosePreview(boolean clearBackStack);
    }

    @Inject
    PreviewPresenter previewPresenter;

    @Bind(R.id.preview__button_to_app)
    Button buttonToApp;

    private PreviewListener previewListener;

    public PreviewFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof PreviewListener)
            this.previewListener = (PreviewListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(PreviewComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_preview, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.previewPresenter.setView(this);
        if (savedInstanceState == null)
            this.previewPresenter.initialize();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.previewPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.previewPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.previewPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.previewListener = null;
    }

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

    @Override
    public void showError(String message, boolean showButtonRetry) {}

    @Override
    public void hideError() {}

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    /**
     * Close preview
     */
    @Override
    public void closePreview(boolean clearBackStack) {
        this.previewListener.onClosePreview(clearBackStack);
    }

    @OnClick(R.id.preview__button_to_app)
    void onClickButtonToApp() {
        this.previewPresenter.onClickButtonToApp();
    }
}

