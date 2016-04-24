package ru.yandex.summerschool2016.filatovaa.data.exception;

/**
 * Exception throw by the application when a Artist search can't return a valid result.
 */
public class ArtistsNotFoundException extends Exception {

    public ArtistsNotFoundException() {
        super();
    }

    public ArtistsNotFoundException(final String message) {
        super(message);
    }

    public ArtistsNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ArtistsNotFoundException(final Throwable cause) {
        super(cause);
    }
}
