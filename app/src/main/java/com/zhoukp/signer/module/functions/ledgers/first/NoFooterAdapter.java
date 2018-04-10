package com.zhoukp.signer.module.functions.ledgers.first;

import android.content.Context;

import com.donkingliang.groupedadapter.holder.BaseViewHolder;

import java.util.ArrayList;
/**
 * @author zhoukp
 * @time 2018/4/9 15:22
 * @email 275557625@qq.com
 * @function
 */
public class NoFooterAdapter extends GroupedListAdapter {

    public NoFooterAdapter(Context context, ArrayList<GroupEntity> groups) {
        super(context, groups);
    }

    /**
     * 返回false表示没有组尾
     *
     * @param groupPosition groupPosition
     * @return
     */
    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    /**
     * 当hasFooter返回false时，这个方法不会被调用。
     *
     * @return
     */
    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }

    /**
     * 当hasFooter返回false时，这个方法不会被调用。
     *
     * @param holder holder
     * @param groupPosition groupPosition
     */
    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

    }

}
