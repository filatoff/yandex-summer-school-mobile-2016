package ru.yandex.summerschool2016.filatovaa.domain.interactor;

import javax.inject.Inject;

import ru.yandex.summerschool2016.filatovaa.domain.executor.PostExecutionThread;
import ru.yandex.summerschool2016.filatovaa.domain.executor.ThreadExecutor;
import ru.yandex.summerschool2016.filatovaa.domain.repository.SettingsRepository;
import rx.Observable;

public class SettingsPreviewHide extends UseCase {

    private final SettingsRepository settingsRepository;

    @Inject
    public SettingsPreviewHide(SettingsRepository settingsRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.settingsRepository = settingsRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return settingsRepository.getPreviewHide();
    }

    public void putPreviewHide(boolean previewHide) {
        this.settingsRepository.putPreviewHide(previewHide);
    }
}
