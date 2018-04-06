package com.zhoukp.signer.module.welcome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.zhoukp.signer.R;
import com.zhoukp.signer.module.main.MainActivity;

/**
 * @author zhoukp
 * @time 2018/4/1 14:02
 * @email 275557625@qq.com
 * @function
 */
public abstract class WelcomeActivity extends AppCompatActivity {

    public static final String WELCOME_SCREEN_KEY = "welcome_screen_key";

    protected ViewPager viewPager;
    private WelcomeFragmentPagerAdapter adapter;
    private WelcomeConfiguration configuration;
    private WelcomeItemList responsiveItems = new WelcomeItemList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configuration = configuration();

        super.onCreate(null);

        showOrToMain();

        setContentView(R.layout.wel_activity_welcome);

        adapter = new WelcomeFragmentPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.wel_view_pager);
        viewPager.setAdapter(adapter);

        responsiveItems = new WelcomeItemList();


        FrameLayout bottomFrame = findViewById(R.id.wel_bottom_frame);
        View.inflate(this, configuration.getBottomLayoutResId(), bottomFrame);

        if (configuration.getShowActionBarBackButton()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null)
                actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SkipButton skip = new SkipButton(findViewById(R.id.wel_button_skip));
        addViewWrapper(skip, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeWelcomeScreen();
            }
        });

        PreviousButton prev = new PreviousButton(findViewById(R.id.wel_button_prev));
        addViewWrapper(prev, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollToPreviousPage();
            }
        });

        NextButton next = new NextButton(findViewById(R.id.wel_button_next));
        addViewWrapper(next, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollToNextPage();
            }
        });

        DoneButton done = new DoneButton(findViewById(R.id.wel_button_done));
        addViewWrapper(done, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeWelcomeScreen();
            }
        });

        View firstBarButton = findViewById(R.id.wel_button_bar_first);
        if (firstBarButton != null) {
            firstBarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonBarFirstPressed();
                }
            });
        }

        View secondBarButton = findViewById(R.id.wel_button_bar_second);
        if (secondBarButton != null) {
            secondBarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonBarSecondPressed();
                }
            });
        }

        WelcomeViewPagerIndicator indicator = findViewById(R.id.wel_pager_indicator);
        if (indicator != null) {
            responsiveItems.add(indicator);
        }

        WelcomeBackgroundView background = findViewById(R.id.wel_background_view);

        WelcomeViewHider hider = new WelcomeViewHider(findViewById(R.id.wel_root));
        hider.setOnViewHiddenListener(new WelcomeViewHider.OnViewHiddenListener() {
            @Override
            public void onViewHidden() {
                completeWelcomeScreen();
            }
        });

        responsiveItems.addAll(background, hider, configuration.getPages());

        responsiveItems.setup(configuration);

        viewPager.addOnPageChangeListener(responsiveItems);
        viewPager.setCurrentItem(configuration.firstPageIndex());

        responsiveItems.onPageSelected(viewPager.getCurrentItem());
    }

    protected abstract void showOrToMain();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewPager != null) {
            viewPager.clearOnPageChangeListeners();
        }
    }


    private void addViewWrapper(WelcomeViewWrapper wrapper, View.OnClickListener onClickListener) {
        if (wrapper.getView() != null) {
            wrapper.setOnClickListener(onClickListener);
            responsiveItems.add(wrapper);
        }
    }

    boolean scrollToNextPage() {
        if (!canScrollToNextPage()) {
            return false;
        }
        viewPager.setCurrentItem(getNextPageIndex());
        return true;
    }

    boolean scrollToPreviousPage() {
        if (!canScrollToPreviousPage()) {
            return false;
        }
        viewPager.setCurrentItem(getPreviousPageIndex());
        return true;
    }

    protected int getNextPageIndex() {
        return viewPager.getCurrentItem() + (configuration.isRtl() ? -1 : 1);
    }

    protected int getPreviousPageIndex() {
        return viewPager.getCurrentItem() + (configuration.isRtl() ? 1 : -1);
    }

    protected boolean canScrollToNextPage() {
        return configuration.isRtl() ? getNextPageIndex() >= configuration.lastViewablePageIndex() : getNextPageIndex() <= configuration.lastViewablePageIndex();
    }

    protected boolean canScrollToPreviousPage() {
        return configuration.isRtl() ? getPreviousPageIndex() <= configuration.firstPageIndex() : getPreviousPageIndex() >= configuration.firstPageIndex();
    }

    /**
     * 当使用按钮栏底部的布局时，调用第一个按钮（在LTR布局方向左侧）按钮被调用
     */
    protected void onButtonBarFirstPressed() {

    }

    /**
     * 当使用按钮栏底部布局时，调用第二个（在LTR布局方向上的右）按钮
     */
    protected void onButtonBarSecondPressed() {

    }

    /**
     * 关闭活动并将其保存为已完成
     */
    protected void completeWelcomeScreen() {
        SharedPreferences sp = getSharedPreferences("HQUZkP", Context.MODE_PRIVATE);
        sp.edit().putString("isSplash", "true").apply();
        WelcomeSharedPreferencesHelper.storeWelcomeCompleted(this, getKey());
        setWelcomeScreenResult(RESULT_OK);
        startActivity(new Intent(this, MainActivity.class));
        finish();
        if (configuration.getExitAnimation() != WelcomeConfiguration.NO_ANIMATION_SET)
            overridePendingTransition(R.anim.wel_none, configuration.getExitAnimation());
    }

    /**
     * 关闭活动，不保存完成
     */
    protected void cancelWelcomeScreen() {
        setWelcomeScreenResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onBackPressed() {

        if (configuration.getBackButtonNavigatesPages() && scrollToPreviousPage()) {
            return;
        }

        if (configuration.getCanSkip() && configuration.getBackButtonSkips()) {
            completeWelcomeScreen();
        } else {
            cancelWelcomeScreen();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (configuration.getShowActionBarBackButton() && item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setWelcomeScreenResult(int resultCode) {
        Intent intent = this.getIntent();
        intent.putExtra(WELCOME_SCREEN_KEY, getKey());
        this.setResult(resultCode, intent);
    }

    private String getKey() {
        return WelcomeUtils.getKey(this.getClass());
    }

    protected abstract WelcomeConfiguration configuration();

    private class WelcomeFragmentPagerAdapter extends FragmentPagerAdapter {

        public WelcomeFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return configuration.createFragment(position);
        }

        @Override
        public int getCount() {
            return configuration.pageCount();
        }
    }
}