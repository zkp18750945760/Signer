package com.zhoukp.signer.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhoukp.signer.R;
import com.zhoukp.signer.adapter.AllTestListViewAdapter;
import com.zhoukp.signer.bean.AllTestBean;
import com.zhoukp.signer.utils.AssetsHelper;
import com.zhoukp.signer.utils.WindowUtils;
import com.zhoukp.signer.viewpager.CommonViewPager;
import com.zhoukp.signer.viewpager.ViewPagerHolder;
import com.zhoukp.signer.viewpager.ViewPagerHolderCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoukp
 * @time 2018/2/2 20:07
 * @email 275557625@qq.com
 * @function 综测页面
 */

public class AllTestActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivBack;
    private CommonViewPager commonViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.getStatusBarHeight(this);
        WindowUtils.setTransluteWindow(this);

        setContentView(R.layout.activity_alltest);

        initViews();

        initVariates();

        initEvents();
    }

    private void initViews() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        commonViewPager = (CommonViewPager) findViewById(R.id.commonViewPager);
    }

    private void initVariates() {
        List<AllTestBean> datas = new ArrayList<>();
        AllTestBean bean = new Gson().fromJson(AssetsHelper.readAssetsTxt(this, "alltest_json"), AllTestBean.class);
        datas.add(bean);
        datas.add(bean);
        datas.add(bean);

        commonViewPager.setPages(datas, new ViewPagerHolderCreator<ViewImageHolder>() {
            @Override
            public ViewImageHolder createViewHolder() {
                // 返回ViewPagerHolder
                return new ViewImageHolder();
            }
        });
    }

    private void initEvents() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 提供ViewPager展示的ViewHolder
     * 用于提供布局和绑定数据
     */
    private static class ViewImageHolder implements ViewPagerHolder<AllTestBean> {

        TextView tvTheme, tvScore, tvRank, tvTime;
        ListView listView;

        @Override
        public View createView(Context context) {
            // 返回ViewPager 页面展示的布局
            View view = LayoutInflater.from(context).inflate(R.layout.view_pager_item, null);
            tvTheme = view.findViewById(R.id.tvTheme);
            tvScore = view.findViewById(R.id.tvScore);
            tvRank = view.findViewById(R.id.tvRank);
            tvTime = view.findViewById(R.id.tvTime);
            listView = view.findViewById(R.id.listView);
            return view;
        }

        @Override
        public void onBind(Context context, int position, AllTestBean bean) {
            // 数据绑定
            tvScore.setText(bean.getScore() + "");
            tvRank.setText(bean.getRank() + "/" + bean.getTotal());
            tvTime.setText(bean.getTime());

            AllTestListViewAdapter adapter = new AllTestListViewAdapter(context, bean);
            listView.setAdapter(adapter);

        }
    }
}
