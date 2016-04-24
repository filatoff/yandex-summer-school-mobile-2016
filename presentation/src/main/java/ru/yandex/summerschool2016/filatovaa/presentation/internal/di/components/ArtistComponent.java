package ru.yandex.summerschool2016.filatovaa.presentation.internal.di.components;

import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.PerActivity;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.modules.ActivityModule;
import ru.yandex.summerschool2016.filatovaa.presentation.internal.di.modules.ArtistModule;
import ru.yandex.summerschool2016.filatovaa.presentation.view.fragment.ArtistDetailsFragment;
import ru.yandex.summerschool2016.filatovaa.presentation.view.fragment.ArtistListFragment;
import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects artist specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ArtistModule.class})
public interface ArtistComponent extends ActivityComponent {
    void inject(ArtistListFragment artistListFragment);
    void inject(ArtistDetailsFragment artistDetailsFragment);
}
