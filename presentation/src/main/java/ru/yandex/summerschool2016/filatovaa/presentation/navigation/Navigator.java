package ru.yandex.summerschool2016.filatovaa.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import ru.yandex.summerschool2016.filatovaa.presentation.view.activity.ArtistDetailsActivity;
import ru.yandex.summerschool2016.filatovaa.presentation.view.activity.ArtistListActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

    @Inject
    public Navigator() {
        //empty
    }

    /**
     * Goes to the artist list screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToArtistList(Context context) {
        if (context != null) {
            Intent intentToLaunch = ArtistListActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    /**
     * Goes to the artist details screen.
     *
     * @param context A Context needed to open the destiny activity.
     */
    public void navigateToArtistDetails(Context context, int id) {
        if (context != null) {
            Intent intentToLaunch = ArtistDetailsActivity.getCallingIntent(context, id);
            context.startActivity(intentToLaunch);
        }
    }
}
