package com.zhoukp.signer.module.functions.ledgers.first;

import android.content.Context;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.zhoukp.signer.R;

import java.util.ArrayList;

/**
 * @author zhoukp
 * @time 2018/4/9 15:25
 * @email 275557625@qq.com
 * @function
 */
public class GroupedListAdapter extends GroupedRecyclerViewAdapter {

    private ArrayList<GroupEntity> mGroups;

    public GroupedListAdapter(Context context, ArrayList<GroupEntity> groups) {
        super(context);
        mGroups = groups;
    }

    @Override
    public int getGroupCount() {
        return mGroups == null ? 0 : mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<ChildEntity> children = mGroups.get(groupPosition).getChildren();
        return children == null ? 0 : children.size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return true;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.adapter_header;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return R.layout.adapter_footer;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.adapter_child;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
        GroupEntity entity = mGroups.get(groupPosition);
        holder.setText(R.id.tv_header, entity.getHeader());
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {
        GroupEntity entity = mGroups.get(groupPosition);
        holder.setText(R.id.tv_footer, entity.getFooter());
    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        ChildEntity entity = mGroups.get(groupPosition).getChildren().get(childPosition);
        holder.setText(R.id.tvContent, entity.getContent());
        holder.setText(R.id.tvContentLong, entity.getContentLong());
        switch (entity.getStatus()) {
            case 1:
                holder.setText(R.id.tvStatus, "准时");
                break;
            case 2:
                holder.setText(R.id.tvStatus, "迟到");
                break;
            case 3:
                holder.setText(R.id.tvStatus, "旷课");
                break;
        }
    }
}
