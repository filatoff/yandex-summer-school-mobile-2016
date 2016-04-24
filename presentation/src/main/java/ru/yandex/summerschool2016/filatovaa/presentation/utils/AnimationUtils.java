package ru.yandex.summerschool2016.filatovaa.presentation.utils;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

/**
 * Animation class helper
 */
public class AnimationUtils {

    public static Animation createAlpha(float fromAlpha, float toAlpha, long duration) {
        return createAlpha(fromAlpha, toAlpha, duration, null);
    }

    /**
     * Create alpha animation with custom parameters.
     * @param fromAlpha
     * @param toAlpha
     * @param duration
     * @param listener
     * @return animation
     */
    public static Animation createAlpha(float fromAlpha, float toAlpha, long duration,
                                        Animation.AnimationListener listener) {
        Animation animation = new AlphaAnimation(fromAlpha, toAlpha);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(duration);

        if (listener != null)
            animation.setAnimationListener(listener);

        return animation;
    }
}
