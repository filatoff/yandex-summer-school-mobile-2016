package ru.yandex.summerschool2016.filatovaa.test.exception;

import android.test.AndroidTestCase;

import ru.yandex.summerschool2016.filatovaa.data.exception.NetworkConnectionException;
import ru.yandex.summerschool2016.filatovaa.data.exception.ArtistsNotFoundException;
import ru.yandex.summerschool2016.filatovaa.presentation.R;
import ru.yandex.summerschool2016.filatovaa.presentation.exception.ErrorMessageFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ErrorMessageFactoryTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testNetworkConnectionErrorMessage() {
        String expectedMessage = getContext().getString(R.string.error__message_no_connection);
        String actualMessage = ErrorMessageFactory.create(getContext(),
                new NetworkConnectionException());

        assertThat(actualMessage, is(equalTo(expectedMessage)));
    }

    public void testUserNotFoundErrorMessage() {
        String expectedMessage = getContext().getString(R.string.error__message_artist_not_found);
        String actualMessage = ErrorMessageFactory.create(getContext(), new ArtistsNotFoundException());

        assertThat(actualMessage, is(equalTo(expectedMessage)));
    }
}
