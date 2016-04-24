package ru.yandex.summerschool2016.filatovaa.test.view.activity;

import android.app.Fragment;
import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
import android.test.ActivityInstrumentationTestCase2;

import ru.yandex.summerschool2016.filatovaa.presentation.R;
import ru.yandex.summerschool2016.filatovaa.presentation.view.activity.ArtistListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class ArtistListActivityTest extends ActivityInstrumentationTestCase2<ArtistListActivity> {

    private ArtistListActivity artistListActivity;

    public ArtistListActivityTest() {
        super(ArtistListActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.setActivityIntent(createTargetIntent());
        artistListActivity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testContainsArtistListFragment() {
        Fragment artistListFragment = artistListActivity.getFragmentManager().findFragmentById(R.id.fragment_container);
        assertThat(artistListFragment, is(notNullValue()));
    }

    public void testLoadArtistStartViews() {
        onView(ViewMatchers.withId(R.id.artist_list__recycler_view)).check(matches(isDisplayed()));
    }

    private Intent createTargetIntent() {
        return ArtistListActivity.getCallingIntent(getInstrumentation().getTargetContext());
    }
}
