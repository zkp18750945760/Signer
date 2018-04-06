package com.zhoukp.signer.module.welcome;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author zhoukp
 * @time 2018/4/1 14:54
 * @email 275557625@qq.com
 * @function
 */
public class WelcomeUtils {

    static final int NO_COLOR_SET = -1;

    private static final String TAG = WelcomeUtils.class.getName();

    public static String getKey(Class<? extends WelcomeActivity> activityClass) {
        String key = null;

        try {
            key = (String) activityClass.getMethod("welcomeKey").invoke(null);
            if (key.isEmpty()) {
                Log.w(TAG, "welcomeKey() from " + activityClass.getSimpleName() + " returned an empty string. Is that an accident?");
            }
        } catch (NoSuchMethodException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (key == null) {
            key = "";
        }
        return key;
    }

    static boolean setTypeface(TextView textView, String typefacePath, Context context) {
        if (typefacePath != null && !typefacePath.equals("")) {
            try {
                textView.setTypeface(Typeface.createFromAsset(context.getAssets(), typefacePath));
                return true;
            } catch (Exception e) {
                Log.w(TAG, "Error setting typeface");
            }
        }
        return false;
    }

    static boolean isIndexBeforeLastPage(int index, int lastPageIndex, boolean isRtl) {
        return isRtl ? index > lastPageIndex : index < lastPageIndex;
    }

    static int calculateParallaxLayers(View view, boolean recursive) {
        if (recursive)
            return calculateParallaxLayersRecursively(view, 0);
        else
            return calculateParallaxLayers(view);
    }

    private static int calculateParallaxLayers(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            return group.getChildCount();
        }
        return 1;
    }

    private static int calculateParallaxLayersRecursively(View view, int startCount) {
        int count = startCount;
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                count = calculateParallaxLayersRecursively(group.getChildAt(i), count);
            }
        } else {
            count++;
        }
        return count;
    }


    public static void applyParallaxEffect(View view, boolean recursive, int offsetPixels, float startParallaxFactor, float parallaxInterval) {
        if (recursive)
            applyParallaxEffectRecursively(view, offsetPixels, startParallaxFactor, parallaxInterval, 0);
        else
            applyParallaxEffectToImmediateChildren(view, offsetPixels, startParallaxFactor, parallaxInterval);
    }

    private static void applyParallaxEffectToImmediateChildren(View view, int offsetPixels, float startParallaxFactor, float parallaxInterval) {
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                translateViewForParallaxEffect(group.getChildAt(i), i, offsetPixels, startParallaxFactor, parallaxInterval);
            }
        } else {
            translateViewForParallaxEffect(view, 0, offsetPixels, startParallaxFactor, parallaxInterval);
        }
    }

    private static int applyParallaxEffectRecursively(View view, int offsetPixels, float startParallaxFactor, float parallaxInterval, int index) {
        int nextIndex = index;
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                nextIndex = applyParallaxEffectRecursively(group.getChildAt(i), offsetPixels, startParallaxFactor, parallaxInterval, nextIndex);
            }
        } else {
            translateViewForParallaxEffect(view, index, offsetPixels, startParallaxFactor, parallaxInterval);
            nextIndex++;
        }
        return nextIndex;
    }

    private static void translateViewForParallaxEffect(View view, int index, int offsetPixels, float startParallaxFactor, float parallaxInterval) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.setTranslationX(calculateParallaxOffsetAmount(index, offsetPixels, startParallaxFactor, parallaxInterval));
        }
    }

    private static float calculateParallaxOffsetAmount(int index, int offsetPixels, float startParallaxFactor, float parallaxInterval) {
        return (startParallaxFactor + (index * parallaxInterval)) * -offsetPixels;
    }
}
