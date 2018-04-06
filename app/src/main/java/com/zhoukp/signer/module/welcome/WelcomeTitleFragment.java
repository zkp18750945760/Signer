package com.zhoukp.signer.module.welcome;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhoukp.signer.R;

/**
 * @author zhoukp
 * @time 2018/4/1 15:08
 * @email 275557625@qq.com
 * @function
 */
public class WelcomeTitleFragment extends Fragment implements WelcomePage.OnChangeListener {

    private static final String ARG_DRAWABLE_ID = "drawable_id";
    private static final String ARG_TITLE = "title";
    private static final String ARG_SHOW_ANIM = "show_anim";
    private static final String ARG_TYPEFACE_PATH = "typeface_path";
    private static final String ARG_TITLE_COLOR = "title_color";

    private int drawableId;
    private String title = "";
    private boolean showParallaxAnim = true;
    private TextView titleView = null;
    private ImageView imageView = null;

    public static WelcomeTitleFragment newInstance(@DrawableRes int resId,
                                                   String title,
                                                   boolean showParallaxAnim,
                                                   @Nullable String typefacePath,
                                                   @ColorInt int titleColor) {
        WelcomeTitleFragment fragment = new WelcomeTitleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DRAWABLE_ID, resId);
        args.putString(ARG_TITLE, title);
        args.putBoolean(ARG_SHOW_ANIM, showParallaxAnim);
        args.putString(ARG_TYPEFACE_PATH, typefacePath);
        args.putInt(ARG_TITLE_COLOR, titleColor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wel_fragment_title, container, false);
        imageView = view.findViewById(R.id.wel_image);
        titleView = view.findViewById(R.id.wel_title);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();

        if (args == null)
            return;

        drawableId = args.getInt(ARG_DRAWABLE_ID);
        title = args.getString(ARG_TITLE);

        imageView.setImageResource(drawableId);
        titleView.setText(title);

        int titleColor = args.getInt(ARG_TITLE_COLOR, WelcomeUtils.NO_COLOR_SET);
        if (titleColor != WelcomeUtils.NO_COLOR_SET)
            titleView.setTextColor(titleColor);

        showParallaxAnim = args.getBoolean(ARG_SHOW_ANIM, showParallaxAnim);

        WelcomeUtils.setTypeface(titleView, args.getString(ARG_TYPEFACE_PATH), getActivity());
    }

    @Override
    public void onWelcomeScreenPageScrolled(int pageIndex, float offset, int offsetPixels) {
        if (showParallaxAnim && Build.VERSION.SDK_INT >= 11 && imageView != null) {
            imageView.setTranslationX(-offsetPixels * 0.8f);
        }
    }

    @Override
    public void onWelcomeScreenPageSelected(int pageIndex, int selectedPageIndex) {
    }

    @Override
    public void onWelcomeScreenPageScrollStateChanged(int pageIndex, int state) {
    }
}