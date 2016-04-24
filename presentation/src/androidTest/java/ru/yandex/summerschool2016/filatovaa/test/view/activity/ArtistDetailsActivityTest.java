package ru.yandex.summerschool2016.filatovaa.test.view.activity;

import android.app.Fragment;
import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import ru.yandex.summerschool2016.filatovaa.presentation.R;
import ru.yandex.summerschool2016.filatovaa.presentation.view.activity.ArtistDetailsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class ArtistDetailsActivityTest extends ActivityInstrumentationTestCase2<ArtistDetailsActivity> {

    private static final int FAKE_ID = 10;

    private ArtistDetailsActivity artistDetailsActivity;

    public ArtistDetailsActivityTest() {
        super(ArtistDetailsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.setActivityIntent(createTargetIntent());
        this.artistDetailsActivity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testContainsArtistDetailsFragment() {
        Fragment artistDetailsFragment = artistDetailsActivity.getFragmentManager().findFragmentById(R.id.fragment_container);
        assertThat(artistDetailsFragment, is(notNullValue()));
    }

    public void testLoadArtistStartViews() {
        onView(ViewMatchers.withId(R.id.artist_detail__genres)).check(matches(not(isDisplayed())));
        onView(ViewMatchers.withId(R.id.artist_detail__counters)).check(matches(not(isDisplayed())));
        onView(ViewMatchers.withId(R.id.artist_detail__biography)).check(matches(not(isDisplayed())));
    }

    private Intent createTargetIntent() {
        return ArtistDetailsActivity.getCallingIntent(getInstrumentation().getTargetContext(), FAKE_ID);
    }
}
