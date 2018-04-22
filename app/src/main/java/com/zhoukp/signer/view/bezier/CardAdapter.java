package com.zhoukp.signer.view.bezier;

import android.support.v7.widget.CardView;

/**
 * @author zhoukp
 * @time 2018/4/22 15:01
 * @email 275557625@qq.com
 * @function
 */
public interface CardAdapter {

    CardView getCardViewAt(int position);

    int getCount();

    int getMaxElevationFactor();

    void setMaxElevationFactor(int MaxElevationFactor);
}
