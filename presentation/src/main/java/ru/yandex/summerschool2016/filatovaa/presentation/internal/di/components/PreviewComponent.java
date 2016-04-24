package ru.yandex.summerschool2016.filatovaa.presentation.internal.di.components;

import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.PerActivity;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.modules.ActivityModule;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.modules.PreviewModule;
import ru.yandex.summerschool2016.filatovaa.presentation.view.fragment.PreviewFragment;
import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects preview specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PreviewModule.class})
public interface PreviewComponent extends ActivityComponent {
    void inject(PreviewFragment previewFragment);
}
