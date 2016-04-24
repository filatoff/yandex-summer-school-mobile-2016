package ru.yandex.summerschool2016.filatovaa.presentation.utils;

import android.content.Context;
import android.util.Log;

import com.seppius.i18n.plurals.PluralResources;

import java.util.List;

/**
 * String utils for convenience
 */
public class StringUtils {

    /**
     * Java8 port
     *
     * @param delimiter string
     * @param chunks list of string
     * @return joined string
     */
    public static String join(String delimiter, List<String> chunks) {
        Integer chunksCount = chunks.size();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chunksCount; i++) {
            sb.append(chunks.get(i));
            if (i != chunksCount - 1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    /**
     * Wrapper for correct support older android versions on plural string
     *
     * @param pluralResources for support older android versions
     * @param context
     * @param id plurals rules
     * @param count
     * @return locale string
     */
    public static String plural(PluralResources pluralResources, Context context, int id, int count) {
        if (pluralResources != null) {
            return pluralResources.getQuantityString(id, count, count);
        } else {
            return context.getResources().getQuantityString(id, count, count);
        }
    }
}
