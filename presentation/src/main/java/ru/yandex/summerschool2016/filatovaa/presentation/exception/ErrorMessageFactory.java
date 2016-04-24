package ru.yandex.summerschool2016.filatovaa.presentation.exception;

import android.content.Context;

import ru.yandex.summerschool2016.filatovaa.data.exception.NetworkConnectionException;
import ru.yandex.summerschool2016.filatovaa.data.exception.ArtistsNotFoundException;
import ru.yandex.summerschool2016.filatovaa.presentation.BuildConfig;
import ru.yandex.summerschool2016.filatovaa.presentation.R;

/**
 * Factory used to create error messages from an Exception as a condition.
 */
public class ErrorMessageFactory {

    private ErrorMessageFactory() {
        //empty
    }

    /**
     * Creates a String representing an error message.
     *
     * @param context Context needed to retrieve string resources.
     * @param exception An exception used as a condition to retrieve the correct error message.
     * @return {@link String} an error message.
     */
    public static String create(Context context, Exception exception) {
        String message = context.getString(R.string.error__message_generic);

        if (exception instanceof NetworkConnectionException) {
            message = context.getString(R.string.error__message_no_connection);
        } else if (exception instanceof ArtistsNotFoundException) {
            message = context.getString(R.string.error__message_artist_not_found);
        } else if (BuildConfig.DEBUG) {
            message = context.getString(R.string.error__message_unhandled) + exception.getMessage();
        }

        return message;
    }
}
