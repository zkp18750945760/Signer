package com.zhoukp.signer.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhoukp.signer.R;
import com.zhoukp.signer.utils.DensityUtil;
import com.zhoukp.signer.utils.WindowUtils;
import com.zhoukp.signer.view.bezier.BezierRoundView;
import com.zhoukp.signer.view.bezier.BezierViewPager;
import com.zhoukp.signer.view.bezier.CardPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhoukp
 * @time 2018/4/21 21:38
 * @email 275557625@qq.com
 * @function
 */
public class TestActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.viewPager)
    BezierViewPager viewPager;
    @BindView(R.id.bezRound)
    BezierRoundView bezRound;

    private ArrayList<Object> imgList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtils.setTransluteWindow(this);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        initImgList();

        int mWidth = getWindowManager().getDefaultDisplay().getWidth();
        float heightRatio = 0.565f;  //高是宽的 0.565 ,根据图片比例

        CardPagerAdapter cardAdapter = new CardPagerAdapter(getApplicationContext());
        cardAdapter.addImgUrlList(imgList);


        //设置阴影大小，即vPage  左右两个图片相距边框  maxFactor + 0.3*CornerRadius   *2
        //设置阴影大小，即vPage 上下图片相距边框  maxFactor*1.5f + 0.3*CornerRadius
        int maxFactor = mWidth / 25;
        cardAdapter.setMaxElevationFactor(maxFactor);

        int mWidthPading = mWidth / 8;

        //因为我们adapter里的cardView CornerRadius已经写死为10dp，所以0.3*CornerRadius=3
        //设置Elevation之后，控件宽度要减去 (maxFactor + dp2px(3)) * heightRatio
        //heightMore 设置Elevation之后，控件高度 比  控件宽度* heightRatio  多出的部分
        float heightMore = (1.5f * maxFactor +
                DensityUtil.dip2px(TestActivity.this, 3)) -
                (maxFactor + DensityUtil.dip2px(TestActivity.this, 3)) * heightRatio;

        int mHeightPading = (int) (mWidthPading * heightRatio - heightMore);

        viewPager.setLayoutParams(new LinearLayout.LayoutParams(mWidth, (int) (mWidth * heightRatio)));
        viewPager.setPadding(mWidthPading, mHeightPading, mWidthPading, mHeightPading);
        viewPager.setClipToPadding(false);
        viewPager.setAdapter(cardAdapter);
        viewPager.showTransformer(0.2f);
        bezRound.attach2ViewPage(viewPager);
    }

    public void initImgList() {
        imgList = new ArrayList<>();
        imgList.add(BitmapFactory.decodeResource(getResources(), R.drawable.pos0));
        imgList.add(BitmapFactory.decodeResource(getResources(), R.drawable.pos1));
        imgList.add(BitmapFactory.decodeResource(getResources(), R.drawable.pos2));
        imgList.add(BitmapFactory.decodeResource(getResources(), R.drawable.pos3));
    }
}
