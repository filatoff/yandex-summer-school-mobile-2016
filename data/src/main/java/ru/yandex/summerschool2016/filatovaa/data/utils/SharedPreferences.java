package ru.yandex.summerschool2016.filatovaa.data.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Helper class to do operations on shared preferences.
 */
@Singleton
public class SharedPreferences {

    private static final String SP_FILE_NAME = "ru.yandex.summerschool.filatovaa.SETTINGS";

    public static final String SP_KEY_CACHE_LAST_UPDATE = "cache_last_update";
    public static final String SP_KEY_ARTIST_LIST_SORT = "artist_list_sort";
    public static final String SP_KEY_PREVIEW_HIDE = "preview_hide";

    private final android.content.SharedPreferences sharedPreferences;

    @Inject
    public SharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Write an integer value to a user preferences file.
     *
     * @param key A string for the key that will be used to retrieve the value in the future.
     * @param value A long representing the value to be inserted.
     */
    public void putInt(String key, int value) {

        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Get an integer value from a user preferences file.
     *
     * @param key A key that will be used to retrieve the value from the preference file.
     * @return An int representing the value retrieved from the preferences file.
     */
    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    /**
     * Write a long value to a user preferences file.
     *
     * @param key A string for the key that will be used to retrieve the value in the future.
     * @param value A long representing the value to be inserted.
     */
    public void putLong(String key, long value) {

        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * Get a long value from a user preferences file.
     *
     * @param key A key that will be used to retrieve the value from the preference file.
     * @return A long representing the value retrieved from the preferences file.
     */
    public long getLong(String key) {
        return sharedPreferences.getLong(key, 0);
    }

    /**
     * Write a boolean value to a user preferences file.
     *
     * @param key A string for the key that will be used to retrieve the value in the future.
     * @param value A boolean representing the value to be inserted.
     */
    public void putBoolean(String key, boolean value) {

        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Get a boolean value from a user preferences file.
     *
     * @param key A key that will be used to retrieve the value from the preference file.
     * @return A boolean representing the value retrieved from the preferences file.
     */
    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
}
